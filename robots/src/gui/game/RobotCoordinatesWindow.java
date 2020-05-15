package gui.game;

import gui.settingsSaver.SaveableJInternalFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class RobotCoordinatesWindow extends SaveableJInternalFrame  implements Observer {

    private final RobotModel robotModel;
    private TextArea coordinates;

    public RobotCoordinatesWindow(RobotModel model) {
        super("Координаты робота", true, true, true, true);
        setSize(200, 100);
        this.robotModel = model;
        robotModel.addObserver(this);
        JPanel panel = new JPanel();
        setContentPane(panel);
        //GridLayout layout = new GridLayout(2 , 2);
        BorderLayout layout = new BorderLayout();
        String text = "x: " + RobotModel.round(robotModel.getRobotPositionX()) +
                        "\ny: " + RobotModel.round(robotModel.getRobotPositionY());
        coordinates = new TextArea(text, 0, 0, TextArea.SCROLLBARS_NONE);
        coordinates.setSize(this.getWidth() / 2 , this.getHeight());
        panel.setLayout(layout);
        panel.add(coordinates);
    }

    @Override
    public void update(Observable o, Object arg) {
        coordinates.setText("x: " + RobotModel.round(robotModel.getRobotPositionX()) +
                                "\ny: " + RobotModel.round(robotModel.getRobotPositionY()));
    }
}
