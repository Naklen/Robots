package gui;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class JInternalFrameSaveLoader {
    public static Settings save(JInternalFrame frame) {
        return new Settings(frame.getTitle(), frame.getSize(), frame.getLocation(), frame.isIcon(), frame.isMaximum());
    }

    public static void load(JInternalFrame frame, Settings settings) {
        try {
            frame.setIcon(settings.isIconified());
            frame.setMaximum(settings.isMaximized());
        }
        catch (PropertyVetoException pve) {
            System.out.println(pve.getMessage());
        }
        frame.setLocation(settings.getLocation());
        frame.setSize(settings.getDimension());
    }
}
