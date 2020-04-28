package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashSet;

public class Saver {

    private static File file = new File(System.getProperty("user.home") + "/robotsWindowSettings.txt");

    public static void saveSettings(Settings mainFrameSettings, JInternalFrame[] internalFrames) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            HashSet<Settings> framesSettings = new HashSet<>();
            stream.writeObject(mainFrameSettings);
            for (JInternalFrame f : internalFrames) {
                Settings frameSettings = new Settings(f.getTitle(), f.getSize(), f.getLocation(), f.isIcon(), f.isMaximum());
                framesSettings.add(frameSettings);
            }
            stream.writeObject(framesSettings);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings(Settings mainFrameSettings, JInternalFrame[] internalFrames) {
        if (file.exists()) {
            try (ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                mainFrameSettings = (Settings) stream.readObject();
                HashSet<Settings> framesSettings = (HashSet<Settings>) stream.readObject();
                for (Settings fs : framesSettings)
                    for (JInternalFrame f : internalFrames) {
                        if (f.getTitle().equals(fs.getTitle())){
                            f.setIcon(fs.isIconified());
                            f.setMaximum(fs.isMaximized());
                            f.setLocation(fs.getLocation());
                            f.setSize(fs.getDimension());
                        }
                    }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
