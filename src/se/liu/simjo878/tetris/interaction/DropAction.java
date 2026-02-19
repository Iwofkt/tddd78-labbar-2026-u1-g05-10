package se.liu.simjo878.tetris.interaction;

import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DropAction extends AbstractAction
{
    private final Board board;

    public DropAction(Board board) {
	this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	board.drop();
    }
}
