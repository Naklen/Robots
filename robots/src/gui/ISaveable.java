package gui;

public interface ISaveable {
    void load(Settings settings);
    Settings save();
    String getTitle();
}
