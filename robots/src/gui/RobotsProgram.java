package gui;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram
{
    public static void main(String[] args) {
      try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.put("OptionPane.yesButtonText" , "Да" );
        UIManager.put("OptionPane.noButtonText" , "Нет" );
      } catch (Exception e) {
        e.printStackTrace();
      }
      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame();
        frame.setVisible(true);
      });
    }}
