package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisViewer
{
    private Board board;
    private final static int UPDATE_INTERVAL = 1000;

    public TetrisViewer(Board board)
    {
	this.board = board;
    }

    public void show() {
	JFrame frame = new JFrame("Tetris Viewer");
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	TetrisComponent tetrisComponent = new TetrisComponent(board);

	frame.add(tetrisComponent, BorderLayout.CENTER);

	frame.pack();
	frame.setVisible(true);

	Timer timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		board.tick();
	    }
	});

	timer.setCoalesce(true);
	timer.start();

	JComponent pane = frame.getRootPane();

	// --- ACTION SETUP --//
	final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	in.put(KeyStroke.getKeyStroke("A"), "right");
	in.put(KeyStroke.getKeyStroke("D"), "left");
	in.put(KeyStroke.getKeyStroke("LEFT"), "right");
	in.put(KeyStroke.getKeyStroke("RIGHT"), "left");
	in.put(KeyStroke.getKeyStroke("ctrl Q"), "quit");

	final ActionMap act = pane.getActionMap();
	act.put("right", new MoveAction(Direction.RIGHT));
	act.put("left", new MoveAction(Direction.LEFT));
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