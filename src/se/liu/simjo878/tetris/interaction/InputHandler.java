package se.liu.simjo878.tetris.interaction;


import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.Direction;

import javax.swing.*;

public class InputHandler {

    public InputHandler(JComponent pane, Board board) {
	final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	in.put(KeyStroke.getKeyStroke("A"), "right");
	in.put(KeyStroke.getKeyStroke("D"), "left");
	in.put(KeyStroke.getKeyStroke("W"), "rotateright");
	in.put(KeyStroke.getKeyStroke("S"), "rotateleft");
	in.put(KeyStroke.getKeyStroke("SPACE"), "dropdown");

	in.put(KeyStroke.getKeyStroke("LEFT"), "right");
	in.put(KeyStroke.getKeyStroke("RIGHT"), "left");
	in.put(KeyStroke.getKeyStroke("UP"), "rotateright");
	in.put(KeyStroke.getKeyStroke("DOWN"), "rotateleft");

	in.put(KeyStroke.getKeyStroke("ctrl Q"), "quit");

	final ActionMap act = pane.getActionMap();
	act.put("right", new MoveAction(Direction.RIGHT, board));
	act.put("left", new MoveAction(Direction.LEFT, board));
	act.put("rotateright", new RotateAction(Direction.RIGHT, board));
	act.put("rotateleft", new RotateAction(Direction.LEFT, board));
	act.put("dropdown", new DropAction(board));
	act.put("quit", new QuitAction());
    }
}
