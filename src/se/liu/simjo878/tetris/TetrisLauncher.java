package se.liu.simjo878.tetris;

import javax.swing.*;
import java.io.IOException;

public class TetrisLauncher
{
    private static final int SPLASH_TIME = 2000; // 2 seconds

    public static void main(String[] args)
    {
	SwingUtilities.invokeLater(() -> {

	    // --- Splash screen ---
	    JFrame splashFrame = new JFrame();
	    splashscreen splash = new splashscreen();

	    splashFrame.add(splash);
	    splashFrame.pack();
	    splashFrame.setLocationRelativeTo(null);
	    splashFrame.setVisible(true);

	    Timer timer = new Timer(SPLASH_TIME, e -> {

		splashFrame.dispose();

		HighscoreList highscoreList = new HighscoreList(); // fallback om load misslyckas

		boolean loaded = false;

		while (!loaded) {
		    try {
			highscoreList = HighscoreList.load();
			loaded = true; // lyckades läsa
		    } catch (IOException ex) {
			int result = javax.swing.JOptionPane.showConfirmDialog(
				null,
				"Ignorera detta medelande ifall du vill skapa en ny highscore fil.\n" +
				"\nEtt fel uppstod när highscore skulle läsas eller så exiterar inte filen:\n" +
				ex.getMessage() +
				"\nVill du försöka igen?",
				"Fel vid highscore",
				javax.swing.JOptionPane.YES_NO_OPTION,
				javax.swing.JOptionPane.ERROR_MESSAGE
			);

			if (result != javax.swing.JOptionPane.YES_OPTION) {
			    loaded = true;
			    highscoreList = new HighscoreList();
			}
		    }
		}



		Board board = new Board(12, 25);
		TetrisViewer viewer = new TetrisViewer(board,  highscoreList);
		viewer.show();

	    });

	    timer.setRepeats(false);
	    timer.start();

	});
    }
}
