package gui.game;

import gui.settingsSaver.SaveableJInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GameWindow extends SaveableJInternalFrame
{
    private final GameVisualizer m_visualizer;
    private final RobotModel robotModel;

    public GameWindow(RobotModel robotModel)
    {
        super("Игровое поле", true, true, true, true);
        this.robotModel = robotModel;
        m_visualizer = new GameVisualizer(robotModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
