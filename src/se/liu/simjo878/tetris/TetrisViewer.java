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
		board.randomBoard();
		tetrisComponent.paintComponent(frame.getGraphics());
	    }
	});

	timer.setCoalesce(true);
	timer.start();
    }
}