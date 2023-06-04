package com.soeguet;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.soeguet.gui.GuiImpl;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    FlatIntelliJLaf.setup();
    SwingUtilities.invokeLater(GuiImpl::new);
  }
}
