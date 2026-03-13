package se.liu.simjolucul.dopeSlope;

import se.liu.simjolucul.dopeSlope.Game.GameModeType;
import se.liu.simjolucul.dopeSlope.Game.GamePanel;
import se.liu.simjolucul.dopeSlope.highscore.HighscoreList;
import se.liu.simjolucul.dopeSlope.menu.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class Main extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;

    private final Map<GameModeType, HighscoreList> highscoreLists = new EnumMap<>(GameModeType.class);

    public static final int VIRTUAL_WIDTH = 800;
    public static final int VIRTUAL_HEIGHT = 1000;

    public Main() {
        setTitle("DopeSlope");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        loadHighscores();

        // Create CardLayout and its container
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create panels
        gamePanel = new GamePanel(this, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, highscoreLists);
        menuPanel = new MenuPanel(this, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Add panels as cards
        cardPanel.add(menuPanel, "menu");
        cardPanel.add(gamePanel, "game");

        // Set the card panel as the content pane
        setContentPane(cardPanel);

        // Show the menu initially
        showMenu();

        pack();
        setLocationRelativeTo(null);
    }

    public void showMenu() {
        menuPanel.startMenu();
        cardLayout.show(cardPanel, "menu");
        gamePanel.stopGame();
    }

    public void startGame(GameModeType gameModeType) {
        menuPanel.stopMenu();
        cardLayout.show(cardPanel, "game");
        gamePanel.startGame(gameModeType);
    }

    public Map<GameModeType, HighscoreList> getHighscoreLists() {
        return highscoreLists;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    private void loadHighscores() {
        highscoreLists.put(GameModeType.Endless,
                loadSingleHighscoreList("highscores_endless.json", false));

        highscoreLists.put(GameModeType.CombeDeCaron,
                loadSingleHighscoreList("highscores_combedecaron.json", true));
    }

    private static HighscoreList loadSingleHighscoreList(String filename, boolean lowerIsBetter) {
        boolean loaded = false;
        HighscoreList highscoreList = new HighscoreList(filename, lowerIsBetter);

        while (!loaded) {
            try {
                highscoreList = HighscoreList.load(filename, lowerIsBetter);
                loaded = true;

            } catch (Exception ex) {
                int result = JOptionPane.showOptionDialog(
                        null,
                        "Ignorera detta meddelande ifall du vill skapa en ny highscore-fil.\n" +
                                "\nEtt fel uppstod när highscore skulle läsas eller så existerar inte filen:\n" +
                                ex.getMessage() +
                                "\nVill du försöka igen?",
                        "Fel vid highscore",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Ja", "Nej"},
                        "Nej"
                );

                if (result == JOptionPane.NO_OPTION) {
                    loaded = true;
                    highscoreList = new HighscoreList(filename, lowerIsBetter);
                }
            }
        }
        return highscoreList;
    }
}