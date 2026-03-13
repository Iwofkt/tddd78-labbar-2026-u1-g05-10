package se.liu.simjo878.tetris.gui;

import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.highscore.Highscore;
import se.liu.simjo878.tetris.highscore.HighscoreList;
import se.liu.simjo878.tetris.interaction.GameOverAction;
import se.liu.simjo878.tetris.interaction.InputHandler;
import se.liu.simjo878.tetris.interaction.NewGameAction;
import se.liu.simjo878.tetris.interaction.PauseAction;
import se.liu.simjo878.tetris.interaction.QuitAction;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TetrisViewer
{
    private Board board;
    private final HighscoreList highscoreList;
    private boolean highscoreSaved = false;

    private long startTime;

    private static final int LEVEL_UP_TIME = 30;
    private static final int START_DELAY = 700;
    private static final int DELAY_DECREASE_PER_LEVEL = 50;
    private static final int MIN_DELAY = 100;

    public TetrisViewer(Board board, HighscoreList highscoreList)
    {
	this.board = board;
	this.highscoreList = highscoreList;
    }


    public void show() {

	// -- FRAME INIT -- //

	JFrame frame = new JFrame("Tetris Viewer");
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// -- ADD GAME -- //

	TetrisComponent tetrisComponent = new TetrisComponent(board, highscoreList);

	frame.add(tetrisComponent, BorderLayout.CENTER);

	// -- MENU BAR -- //

	final JMenuBar gameTopBar = new JMenuBar();

	final JMenu file = new JMenu("Game");
	final JMenuItem quitApp = new JMenuItem("Avsluta application", 'Q');
	final JMenuItem newGame = new JMenuItem("Starta ny omgång", 'P');
	final JMenuItem quitRound = new JMenuItem("Avbryt omgång", 'O');
	final JMenuItem pauseGame = new JMenuItem("Pausa spelet", 'P');

	file.add(quitApp);
	file.add(newGame);
	file.add(quitRound);
	file.add(pauseGame);

	quitApp.addActionListener(new QuitAction());
	newGame.addActionListener(new NewGameAction(board));
	quitRound.addActionListener(new GameOverAction(0, board));
	pauseGame.addActionListener(new PauseAction(board));

	gameTopBar.add(file);
	frame.setJMenuBar(gameTopBar);

	frame.pack();
	frame.setVisible(true);

	// -- TIMER-- //

	startTime = System.currentTimeMillis();

	Timer timer = new Timer(START_DELAY, e -> {

	    if (!board.getGameOver()) {
		updateSpeed((Timer)e.getSource());
	    }

	    board.tick();

	    if (board.getGameOver()) {
		if (!highscoreSaved) {
		    saveHighscore();
		    highscoreSaved = true;
		}
		if (board.getNewGame()) {
		    board.resetBoard();
		    resetTimer((Timer)e.getSource());
		    highscoreSaved = false;
		}
	    }
	});

	timer.setCoalesce(true);
	timer.start();

	// --- ACTION SETUP --//
	InputHandler inputHandler = new InputHandler(frame.getRootPane(), board);
    }

    // -- TIMER HELPER FUNCTIONS -- //

    private void updateSpeed(Timer timer) {
	long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;

	int newLevel = (int)(elapsedSeconds / LEVEL_UP_TIME);

	if (newLevel > board.getLevel()) {

	    board.setLevel(newLevel);

	    int newDelay = Math.max(
		    MIN_DELAY,
		    START_DELAY - (newLevel * DELAY_DECREASE_PER_LEVEL)
	    );
	    timer.setDelay(newDelay);
	}
    }

    private void resetTimer(Timer timer){
	timer.stop();
	startTime = System.currentTimeMillis();
	timer.setDelay(START_DELAY);
	timer.restart();
    }

    private void saveHighscore() {
	boolean saved = false;
	String username = null;
	while (username == null) {
	    // Ask for the username input
	    username = JOptionPane.showInputDialog(
		    null,
		    "Vänligen skriv in ditt användarnamn:\n",
		    "Ange Användarnamn",
		    JOptionPane.QUESTION_MESSAGE
	    );
	}
	username = username.trim();
	while (!saved) {
	    try {
		highscoreList.addScore(new Highscore(username, board.getPoints()));
		saved = true; // lyckades spara
	    } catch (IOException ex) {
		ex.printStackTrace();

		// Visa popup med fråga om användaren vill försöka igen
		int result = JOptionPane.showOptionDialog(
			null,
			"Ett fel uppstod när highscore skulle sparas:\n" + ex.getMessage() +
			"\nVill du försöka igen?",
			"Fel vid sparning",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			new Object[] { "Ja", "Nej" },
			"Nej"
		);

		if (result != JOptionPane.YES_OPTION) {
		    saved = true;
		}
	    }
	}
    }
}