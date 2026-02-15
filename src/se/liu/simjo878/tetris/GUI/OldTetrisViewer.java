package se.liu.simjo878.tetris.GUI;

import se.liu.simjo878.tetris.Board;

import javax.swing.*;
import java.awt.*;

public class OldTetrisViewer
{
    private Board board;
    private BoardToTextConverter converter = new BoardToTextConverter();
    private final static int UPDATE_INTERVAL = 1000;

    public OldTetrisViewer(Board board)
    {
	this.board = board;
    }
    public void show(){
	JFrame frame = new JFrame("Tetris Viewer");
	frame.setLayout(new BorderLayout());

	JTextArea textArea = new JTextArea(board.getHeight(), board.getWidth());
	textArea.setText(converter.convertToText(board));
	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));

	frame.add(textArea, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);

	Timer timer = new Timer(UPDATE_INTERVAL, e -> {
	    board.randomBoard();
	    textArea.setText(converter.convertToText(board));
	});


	timer.setCoalesce(true);
	timer.start();
    }
}
