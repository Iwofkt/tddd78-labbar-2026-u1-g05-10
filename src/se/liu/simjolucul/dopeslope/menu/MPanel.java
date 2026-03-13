package se.liu.simjolucul.dopeslope.menu;

import se.liu.simjolucul.dopeslope.game.GModeType;
import se.liu.simjolucul.dopeslope.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MPanel extends JPanel implements ActionListener {
    private Main main;
    private Model menuModel;
    private MComponent menuMComponent;
    private Timer timer;
    public static final int FPS = 40;
    private boolean running = false;

    public MPanel(Main main, int width, int height) {
        this.main = main;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        menuModel = new Model(width, height);
        menuMComponent = new MComponent(menuModel);

        // Listen for menu actions (e.g., Start Game selected)
        menuMComponent.addActionListener(e -> {
            String command = e.getActionCommand();
            if ("Start Game".equals(command)) {
                main.startGame(GModeType.Endless);
            }
            else if ("Options".equals(command)) {
                // handle options
            }
        });

        add(menuMComponent, BorderLayout.CENTER);

        timer = new Timer(1000 / FPS, this);

        // In MenuPanel constructor, after creating menuComponent:
        menuMComponent.addActionListener(e -> {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "PLAY":
                    menuModel.setScreen(MScreens.MODE_SELECT);
                    break;
                case "MODE_ENDLESS":
                    main.startGame(GModeType.Endless);
                    break;
                case "MODE_COMBE":
                    main.startGame(GModeType.CombeDeCaron);
                    break;
                case "BACK":
                    menuModel.setScreen(MScreens.MAIN);
                    break;
                case "OPTIONS":
                    menuModel.setScreen(MScreens.OPTIONS);
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
                // ... handle options sub‑items later
            }
        });
    }

    public void startMenu() {
        if (!running) {
            running = true;
            timer.start();
            menuMComponent.requestFocusInWindow();
        }
    }

    public void stopMenu() {
        if (running) {
            running = false;
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        menuMComponent.repaint();
        menuModel.update();
    }
}