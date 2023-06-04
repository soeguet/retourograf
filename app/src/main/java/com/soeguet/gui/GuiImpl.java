package com.soeguet.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class GuiImpl extends Gui {

  // TODO please provide your own path for photos
  private final String photosPath = "";

  @SuppressWarnings("rawtypes")
  Vector originalTableModel;

  private JDialog updateDialog;

  public GuiImpl() {
    customInit();
  }

  private void customInit() {

    loadLogo();
    setSize(1200, 800);
    setLocationRelativeTo(null);
    form_deliveryCodeTextPane.requestFocus();

    updateTable();
    textFieldBackgroundColor();
  }

  private void loadLogo() {
    // initial logo load up
    ClassLoader classLoader = getClass().getClassLoader();
    if (Objects.requireNonNull(classLoader.getResource("logo.png")).toString().contains("jar")) {

      setIconImage(Toolkit.getDefaultToolkit().getImage(classLoader.getResource("logo.png")));
    } else {
      setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/logo.png"));
    }
  }

  @Override
  protected void deliveryCodeTextPaneKeyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

      if (form_dataTable.getSelectedRow() > -1) {
        openSelectedPhoto();
        return;
      }
      takePhoto();
      e.consume();
    }
  }

  @Override
  protected void takePhotoButtonMouseClicked(MouseEvent e) {
    takePhoto();
  }

  @Override
  protected void deliveryCodeTextPaneMouseClicked(MouseEvent e) {
    form_dataTable.clearSelection();
  }

  @Override
  protected void deliveryCodeTextPaneKeyReleased(KeyEvent e) {
    textFieldBackgroundColor();
  }

  @Override
  protected void openPhotoButtonMouseClicked(MouseEvent e) {

    if (form_dataTable.getSelectedRow() < 0) {
      JOptionPane.showConfirmDialog(
          this, "select a photo first!", "no photo selected", JOptionPane.DEFAULT_OPTION);

      return;
    }
    openSelectedPhoto();
  }

  private void openSelectedPhoto() {
    Path valueAt = (Path) form_dataTable.getValueAt(form_dataTable.getSelectedRow(), 0);
    System.out.println("valueAt = " + valueAt);

    File f = new File(photosPath + valueAt);
    Desktop dt = Desktop.getDesktop();
    try {
      dt.open(f);
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(this, "can't open photo, did you delete or move the file?");
    }
  }

  private void takePhoto() {
    if (form_deliveryCodeTextPane.getText().trim().length() < 5) {

      if (form_deliveryCodeTextPane.getText().trim().isEmpty()) {
        JOptionPane.showConfirmDialog(
            this,
            "please provide a delivery code!",
            "delivery code missing",
            JOptionPane.DEFAULT_OPTION);
      } else {
        JOptionPane.showConfirmDialog(
            this,
            "please provide a valid delivery code!",
            "delivery code too short",
            JOptionPane.DEFAULT_OPTION);
      }

      SwingUtilities.invokeLater(
          () -> {
            form_deliveryCodeTextPane.setText("");
            textFieldBackgroundColor();

            form_deliveryCodeTextPane.requestFocus();
          });
      return;
    }

    SwingUtilities.invokeLater(
        () -> {
          if (form_takePhotoButton.isEnabled()) {
            toggleAllComponents(false);
            createPhoto();
          }
        });
  }

  private void textFieldBackgroundColor() {

    searchTableContent();

    if (form_deliveryCodeTextPane.getText().trim().length() < 5) {
      SwingUtilities.invokeLater(
          () -> form_deliveryCodeTextPane.setBackground(new Color(0xffcccc)));
    } else {
      SwingUtilities.invokeLater(
          () -> form_deliveryCodeTextPane.setBackground(new Color(0xccffcc)));
    }
  }

  private void toggleAllComponents(boolean b) {

    if (!b) {

      SwingUtilities.invokeLater(
          () -> {
            updateDialog = new JDialog(this, true);
            updateDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            updateDialog.setLayout(new MigLayout("gapy 0"));

            updateDialog.add(new JLabel("creating photo, please wait"), "alignx center,wrap");
            updateDialog.setSize(300, 300);
            updateDialog.setLocationRelativeTo(this);
            updateDialog.setVisible(true);
          });
    } else {
      SwingUtilities.invokeLater(() -> updateDialog.dispose());
    }

    form_takePhotoButton.setEnabled(b);
    form_openPhotoButton.setEnabled(b);
    form_deliveryCodeTextPane.setEnabled(b);
    form_dataTable.setEnabled(b);
  }

  private void createPhoto() {

    new Thread(
            () -> {
              form_takePhotoButton.setEnabled(false);

              String arg = form_deliveryCodeTextPane.getText().trim();
              arg = arg.replace(" ", "-");
              String argument =
                  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"))
                      + "_"
                      + arg;

              try {

                ClassLoader classLoader = getClass().getClassLoader();

                Process p;

                if (Objects.requireNonNull(classLoader.getResource("scripts/main.py"))
                    .toString()
                    .contains("jar")) {

                  p =
                      new ProcessBuilder(
                              "python",
                              Objects.requireNonNull(classLoader.getResource("scripts/main.py"))
                                  .getPath(),
                              argument)
                          .redirectErrorStream(true)
                          .start();
                } else {
                  p =
                      new ProcessBuilder("python", "src/main/resources/scripts/main.py", argument)
                          .redirectErrorStream(true)
                          .start();
                }

                p.getInputStream().transferTo(System.out);
                p.waitFor();

              } catch (IOException | InterruptedException e) {
                JOptionPane.showMessageDialog(this, "python script invalid path");
              }

              updateTable();
              toggleAllComponents(true);

              form_deliveryCodeTextPane.setText("");
            })
        .start();
  }

  @SuppressWarnings("rawtypes")
  private void searchTableContent() {
    SwingUtilities.invokeLater(
        () -> {
          DefaultTableModel dtm = (DefaultTableModel) form_dataTable.getModel();
          dtm.setRowCount(0);
          // To search for contents from original table content
          for (Object rows : originalTableModel) {
            Vector rowVector = (Vector) rows;
            for (Object column : rowVector) {
              if (column.toString().contains(form_deliveryCodeTextPane.getText().trim())) {
                // content found so adding to table
                dtm.addRow(rowVector);
                break;
              }
            }
          }

          if (dtm.getRowCount() == 1) {
            form_dataTable.setRowSelectionInterval(0, 0);
          }
        });
  }

  private void updateTable() {
    try (Stream<Path> stream = Files.list(Paths.get(photosPath))) {

      DefaultTableModel dtm = (DefaultTableModel) form_dataTable.getModel();
      dtm.setRowCount(0);

      AtomicReference<LocalDateTime> localDateTime = new AtomicReference<>();

      AtomicReference<BasicFileAttributes> basicFileAttributes = new AtomicReference<>();

      stream.forEach(
          a -> {
            File file = new File(a.toUri());
            try {
              basicFileAttributes.set(
                  Files.readAttributes(file.toPath(), BasicFileAttributes.class));
            } catch (IOException e) {
              JOptionPane.showMessageDialog(
                  this, "photo in photos folder is damaged: " + file.getPath());
            }

            localDateTime.set(
                LocalDateTime.ofInstant(
                    basicFileAttributes.get().creationTime().toInstant(), ZoneId.of("UTC+2")));
            dtm.addRow(
                new Object[] {
                  a.getFileName(),
                  localDateTime.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                });
          });

      sortTable();
      textFieldBackgroundColor();

      //noinspection rawtypes
      originalTableModel =
          (Vector) ((DefaultTableModel) form_dataTable.getModel()).getDataVector().clone();

    } catch (IOException e) {
      JOptionPane.showMessageDialog(this, "photos path in source code invalid");
    }
  }

  private void sortTable() {
    form_dataTable.getRowSorter().toggleSortOrder(1);
    form_dataTable.getRowSorter().toggleSortOrder(1);
  }
}
