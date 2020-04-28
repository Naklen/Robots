package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;
import javax.swing.*;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private Settings settings = getMainFrameSettings();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        Saver.loadSettings(settings, desktopPane.getAllFrames());

        if(settings.isIconified())
            setExtendedState(ICONIFIED);
        else if (settings.isMaximized())
            setExtendedState(MAXIMIZED_BOTH);
        setSize(settings.getDimension());
        setLocation(settings.getLocation());

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                settings.setIconified(true);
                System.out.println("Iconified");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                settings.setIconified(false);
                if(settings.isMaximized())
                    setExtendedState(MAXIMIZED_BOTH);
                System.out.println("Deiconified");
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                settings.setDimension(e.getComponent().getSize());
                if (getExtendedState() == JFrame.MAXIMIZED_BOTH)
                    settings.setMaximized(true);
                else
                    settings.setMaximized(false);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                settings.setLocation(e.getComponent().getLocation());
            }
        });
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        
        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));
            testMenu.add(addLogMessageItem);
        }
        JMenuItem exitButton = new JMenuItem("Закрыть приложение");
        UIManager.put("OptionPane.yesButtonText" , "Да" );
        UIManager.put("OptionPane.noButtonText" , "Нет" );
        exitButton.addActionListener(e -> closeWindow());
        JMenu exitMenu = new JMenu("Закрыть приложение");
        exitMenu.add(exitButton);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }

    private void closeWindow() {
        int reply = JOptionPane.showConfirmDialog(null,
                "Вы действительно закрыть приложение?", "Закрыть", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            Saver.saveSettings(settings, desktopPane.getAllFrames());
            System.exit(0);
        }
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }

    private static Settings getMainFrameSettings() {
        return new Settings("Main", Toolkit.getDefaultToolkit().getScreenSize(), new Point(0,0), false, true);
    }
}
