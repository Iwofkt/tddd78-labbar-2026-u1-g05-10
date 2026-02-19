package se.liu.simjo878.tetris.interaction;

import se.liu.simjo878.tetris.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameOverAction extends AbstractAction
{
    private final Board board;

    public GameOverAction(int exitCode, Board board) {
	this.board = board;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	board.setGameOver(true);
    }
}
