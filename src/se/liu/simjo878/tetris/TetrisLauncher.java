package se.liu.simjo878.tetris;

import javax.swing.*;

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

		Board board = new Board(12, 25);
		TetrisViewer viewer = new TetrisViewer(board);
		viewer.show();

	    });

	    timer.setRepeats(false);
	    timer.start();

	});
    }
}
