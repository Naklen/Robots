package gui.settingsSaver;

public interface ISaveable {
    void load(Settings settings);
    Settings getSettings();
    String getTitle();
}
