package com.soeguet.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import net.miginfocom.swing.*;

public abstract class Gui extends JFrame {
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner non-commercial license
	protected JScrollPane form_scrollPane2;
	protected JTable form_dataTable;
	protected JPanel form_panel1;
	protected JTextPane form_deliveryCodeTextPane;
	protected JButton form_openPhotoButton;
	protected JButton form_takePhotoButton;
	public Gui() {
		initComponents();
	}

	protected abstract void deliveryCodeTextPaneKeyPressed(KeyEvent e);

	protected abstract void takePhotoButtonMouseClicked(MouseEvent e);

	protected abstract void deliveryCodeTextPaneMouseClicked(MouseEvent e);

	protected abstract void deliveryCodeTextPaneKeyReleased(KeyEvent e);

	protected abstract void openPhotoButtonMouseClicked(MouseEvent e);

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner non-commercial license
		this.form_scrollPane2 = new JScrollPane();
		this.form_dataTable = new JTable();
		this.form_panel1 = new JPanel();
		this.form_deliveryCodeTextPane = new JTextPane();
		this.form_openPhotoButton = new JButton();
		this.form_takePhotoButton = new JButton();

		//======== this ========
		setTitle("retourograf");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		Container contentPane = getContentPane();
		contentPane.setLayout(new MigLayout(
			"fill,insets 5,align center center,gap 5 5",
			// columns
			"[fill]" +
			"[grow,fill]" +
			"[fill]",
			// rows
			"[]" +
			"[fill]" +
			"[grow,fill]" +
			"[]" +
			"[]" +
			"[]" +
			"[]"));

		//======== form_scrollPane2 ========
		{

			//---- form_dataTable ----
			this.form_dataTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"file", "creation date"
				}
			) {
				final Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class
				};
				final boolean[] columnEditable = new boolean[] {
					false, false
				};
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return this.columnTypes[columnIndex];
				}
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return this.columnEditable[columnIndex];
				}
			});
			this.form_dataTable.setRequestFocusEnabled(false);
			this.form_dataTable.setFont(this.form_dataTable.getFont().deriveFont(this.form_dataTable.getFont().getSize() + 8f));
			this.form_dataTable.setRowHeight(30);
			this.form_dataTable.setAutoCreateRowSorter(true);
			this.form_dataTable.setShowHorizontalLines(true);
			this.form_dataTable.setShowVerticalLines(true);
			this.form_dataTable.setSurrendersFocusOnKeystroke(true);
			this.form_dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.form_scrollPane2.setViewportView(this.form_dataTable);
		}
		contentPane.add(this.form_scrollPane2, "cell 1 1 1 2");

		//======== form_panel1 ========
		{
			this.form_panel1.setBorder(new TitledBorder(null, "delivery code", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
				new Font("Segoe UI", Font.PLAIN, 20), Color.gray));
			this.form_panel1.setLayout(new MigLayout(
				"fill,insets 5,align center center,gap 5 5",
				// columns
				"[grow,fill]",
				// rows
				"[]"));

			//---- form_deliveryCodeTextPane ----
			this.form_deliveryCodeTextPane.setMinimumSize(new Dimension(150, 30));
			this.form_deliveryCodeTextPane.setPreferredSize(new Dimension(150, 30));
			this.form_deliveryCodeTextPane.setFont(this.form_deliveryCodeTextPane.getFont().deriveFont(this.form_deliveryCodeTextPane.getFont().getSize() + 8f));
			this.form_deliveryCodeTextPane.setBorder(null);
			this.form_deliveryCodeTextPane.setMargin(new Insets(2, 16, 2, -16));
			this.form_deliveryCodeTextPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					deliveryCodeTextPaneKeyPressed(e);
				}
				@Override
				public void keyReleased(KeyEvent e) {
					deliveryCodeTextPaneKeyReleased(e);
				}
			});
			this.form_deliveryCodeTextPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					deliveryCodeTextPaneMouseClicked(e);
				}
			});
			this.form_panel1.add(this.form_deliveryCodeTextPane, "pad 0 10 0 -10,cell 0 0,aligny center,growy 0");
		}
		contentPane.add(this.form_panel1, "cell 1 4");

		//---- form_openPhotoButton ----
		this.form_openPhotoButton.setText("open photo");
		this.form_openPhotoButton.setOpaque(true);
		this.form_openPhotoButton.setBackground(new Color(0xccccff));
		this.form_openPhotoButton.setFont(this.form_openPhotoButton.getFont().deriveFont(this.form_openPhotoButton.getFont().getSize() + 8f));
		this.form_openPhotoButton.setForeground(new Color(0x333333));
		this.form_openPhotoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openPhotoButtonMouseClicked(e);
			}
		});
		contentPane.add(this.form_openPhotoButton, "cell 1 5,alignx center,growx 0");

		//---- form_takePhotoButton ----
		this.form_takePhotoButton.setText("take photo");
		this.form_takePhotoButton.setOpaque(true);
		this.form_takePhotoButton.setBackground(new Color(0x3f009932, true));
		this.form_takePhotoButton.setFont(this.form_takePhotoButton.getFont().deriveFont(this.form_takePhotoButton.getFont().getSize() + 8f));
		this.form_takePhotoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takePhotoButtonMouseClicked(e);
			}
		});
		contentPane.add(this.form_takePhotoButton, "cell 1 5");
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
