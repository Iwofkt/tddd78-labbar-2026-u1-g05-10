package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TetrisLauncher
{
    private static final int SPLASH_TIME = 2000;

    public static void main(String[] args)
    {
	SplashScreen splash = new SplashScreen();
	splash.show();

	Timer timer = new Timer(SPLASH_TIME, e -> {

	    splash.stopShow();

	    // -- GAME -- //
	    HighscoreList highscoreList = loadHighscores();

	    Board board = new Board(12, 25);
	    TetrisViewer viewer = new TetrisViewer(board,  highscoreList);
	    viewer.show();

	});
	timer.setRepeats(false);
	timer.start();
    }

    private static HighscoreList loadHighscores()
    {
	boolean loaded = false;

	// fallback
	HighscoreList highscoreList = new HighscoreList();

	while (!loaded) {
	    try {
		highscoreList = HighscoreList.load();
		loaded = true;

	    } catch (IOException ex) {
		// if fail tell user
		int result = JOptionPane.showOptionDialog(
			null,
			"Ignorera detta medelande ifall du vill skapa en ny highscore fil.\n" +
			"\nEtt fel uppstod när highscore skulle läsas eller så exiterar inte filen:\n" +
			ex.getMessage() +
			"\nVill du försöka igen?",
			"Fel vid highscore",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			new Object[] { "Ja", "Nej" },
			"Nej"
		);

		// if error is ignoerd create new empty list
		if (result == JOptionPane.NO_OPTION) {
		    loaded = true;
		    highscoreList = new HighscoreList();
		}
	    }
	}
	return highscoreList;
    }
}
