package se.liu.simjo878.tetris.interaction;

import se.liu.simjo878.tetris.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewGameAction extends AbstractAction {
    private final Board board;

    public NewGameAction(Board board) {
	this.board = board;
    }

    @Override public void actionPerformed(ActionEvent actionEvent) {
	board.setGameOver(true);
	board.setNewGame(true);
    }
}
