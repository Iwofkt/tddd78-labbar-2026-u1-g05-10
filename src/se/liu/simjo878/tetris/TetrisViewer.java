package se.liu.simjo878.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisViewer
{
    private Board board;
    private final HighscoreList highscoreList;
    private boolean highscoreSaved = false;
    private final static int UPDATE_INTERVAL = 200;

    public TetrisViewer(Board board, HighscoreList highscoreList)
    {
	this.board = board;
	this.highscoreList = highscoreList;
    }

    // Quiting the program with ctrl Q
    private void attemptExit(JFrame frame) {
	int choice = JOptionPane.showOptionDialog(
		frame,
		"Är du säker på att du vill avsluta applikationen?",
		"Avsluta",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		new Object[] { "Ja", "Nej" },
		"Nej");

	if (choice == JOptionPane.YES_OPTION) {
	    System.exit(0); // Avsluta programmet
	}
    }

    public void show() {

	// -- FRAME INIT -- //

	JFrame frame = new JFrame("Tetris Viewer");
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// -- ADD GAME -- //

	TetrisComponent tetrisComponent = new TetrisComponent(board,  highscoreList);

	frame.add(tetrisComponent, BorderLayout.CENTER);

	// -- MENU BAR -- //

	final JMenuBar bar = new JMenuBar();

	final JMenu file = new JMenu("Game");
	final JMenuItem quitApp = new JMenuItem("Avsluta application", 'Q');
	final JMenuItem quitRound = new JMenuItem("Avbryt omgång", 'O');
	file.add(quitApp);
	file.add(quitRound);
	quitRound.addActionListener(new GameOverAction(0));
	quitApp.addActionListener(e -> attemptExit(frame));
	bar.add(file);

	frame.setJMenuBar(bar);

	frame.pack();
	frame.setVisible(true);

	// -- TIMER-- //

	Timer timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		board.tick();

		// save highscore once
		if (board.getGameOver() && !highscoreSaved) {
		    highscoreList.addScore(new Highscore("Player", board.getPoints()));
		    highscoreSaved = true;
		}
	    }
	});

	timer.setCoalesce(true);
	timer.start();


	// --- ACTION SETUP --//


	JComponent pane = frame.getRootPane();

	final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	in.put(KeyStroke.getKeyStroke("A"), "right");
	in.put(KeyStroke.getKeyStroke("D"), "left");
	in.put(KeyStroke.getKeyStroke("W"), "rotateright");
	in.put(KeyStroke.getKeyStroke("S"), "rotateleft");

	in.put(KeyStroke.getKeyStroke("LEFT"), "right");
	in.put(KeyStroke.getKeyStroke("RIGHT"), "left");
	in.put(KeyStroke.getKeyStroke("UP"), "rotateright");
	in.put(KeyStroke.getKeyStroke("DOWN"), "rotateleft");

	in.put(KeyStroke.getKeyStroke("ctrl Q"), "quit");

	final ActionMap act = pane.getActionMap();
	act.put("right", new MoveAction(Direction.RIGHT));
	act.put("left", new MoveAction(Direction.LEFT));
	act.put("rotateright", new RotateAction(Direction.RIGHT));
	act.put("rotateleft", new RotateAction(Direction.LEFT));
	act.put("quit", new QuitAction(0));
    }

    // -- ACTIONS -- //

    //Move action for the falling tetromino
    private class MoveAction extends AbstractAction {
	private final Direction direction;

	private MoveAction(Direction direction) {
	    this.direction = direction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    board.move(direction);
	}
    }

    //Move action for the falling tetromino
    private class RotateAction extends AbstractAction {
	private final Direction direction;

	private RotateAction(Direction direction) {
	    this.direction = direction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    board.rotate(direction);
	}
    }


    private class GameOverAction extends AbstractAction
    {
	private final int exitCode;

	private GameOverAction(int exitCode) {
	    this.exitCode = exitCode;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    board.setGameOver(true);
	}
    }

    // Quiting the program with ctrl Q
    private class QuitAction extends AbstractAction
    {
	private final int exitCode;

	private QuitAction(int exitCode) {
	    this.exitCode = exitCode;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    System.exit(exitCode);
	}
    }
}