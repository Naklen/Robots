package gui;

import javax.swing.*;
import java.beans.PropertyVetoException;

public abstract class SaveableJInternalFrame extends JInternalFrame implements ISaveable {

    public SaveableJInternalFrame(String title, boolean resizable, boolean closable,
                                  boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
    }

    public Settings getSettings() {
        return new Settings(getTitle(), getSize(), getLocation(), isIcon(), isMaximum());
    }

    public void load(Settings settings) {
        try {
            setIcon(settings.isIconified());
            setMaximum(settings.isMaximized());
        }
        catch (PropertyVetoException pve) {
            System.out.println(pve.getMessage());
        }
        setLocation(settings.getLocation());
        setSize(settings.getDimension());
    }
}
