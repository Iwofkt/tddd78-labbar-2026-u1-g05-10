package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;

public class OldTetrisViewer
{
    private Board board;
    private BoardToTextConverter converter = new BoardToTextConverter();

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
    }
}
