package se.liu.simjo878.tetris.interaction;

import se.liu.simjo878.tetris.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PauseAction extends AbstractAction
{
    private final Board board;

    public PauseAction(Board board) {
	this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
	board.setGamePaused(!board.getGamePaused());
    }
}