package se.liu.simjo878.tetris.interaction;

import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RotateAction extends AbstractAction
{
    private final Direction direction;
    private final Board board;

    public RotateAction(Direction direction, Board board) {
	this.direction = direction;
	this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	board.rotate(direction);
    }
}
