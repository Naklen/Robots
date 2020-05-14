package gui;

public interface ISaveable {
    void load(Settings settings);
    Settings getSettings();
    String getTitle();
}
