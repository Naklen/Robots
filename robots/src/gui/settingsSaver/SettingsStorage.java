package gui.settingsSaver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsStorage {

    private static File file = new File(System.getProperty("user.home") + "/robotsWindowSettings");
    private static List<ISaveable> saveableComponents = new ArrayList<>();

    public static void saveSettings() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            HashMap<String, Settings> componentsSettings = new HashMap<>();
            for (ISaveable c : saveableComponents)
                componentsSettings.put(c.getTitle(), c.getSettings());
            stream.writeObject(componentsSettings);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        if (file.exists()) {
            try (ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                HashMap<String, Settings> componentsSettings = (HashMap<String, Settings>) stream.readObject();
                for (ISaveable c : saveableComponents)
                    c.load(componentsSettings.get(c.getTitle()));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void addComponentInStorage(ISaveable component) {
        saveableComponents.add(component);
    }
}
