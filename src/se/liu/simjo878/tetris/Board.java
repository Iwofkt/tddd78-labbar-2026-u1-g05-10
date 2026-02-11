package se.liu.simjo878.tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();
    private static final int DEFAULT_POLY_INDEX = 6;


    private SquareType[][] squares;
    private int width;
    private int height;

    private Poly falling;
    private Point fallingPos;

    private List<BoardListener> listeners;



    public Board(int width, int height) {
	this.width = width;
	this.height = height;
	squares = new SquareType[width][height];

	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		squares[col][row] = SquareType.EMPTY;
	    }
	}

	final TetrominoMaker tetrominoMaker = new TetrominoMaker();
	this.falling = tetrominoMaker.getPoly(DEFAULT_POLY_INDEX);
	this.fallingPos = new Point((width/2) - 1, (height/2) - 1);
	this.listeners = new ArrayList<>();
    }

    public int getWidth() {
	return width;  // antal kolumner
    }

    public int getHeight() {
	return height;  // antal rader
    }

    public SquareType getSquareType(int col, int row) {
	// col = x-koordinat (kolumn), row = y-koordinat (rad)
	return squares[col][row];
    }

    public Poly getFalling() {
	return falling;
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public SquareType getVisibleSquareAt(int col, int row) {
	if (falling == null) {
	    return squares[col][row];
	}

	Rectangle fallingBounds = new Rectangle(
		fallingPos.x,
		fallingPos.y,
		falling.getWidth(),
		falling.getHeight()
	);

	if (fallingBounds.contains(col, row)) {
	    SquareType polySquare = falling.getSquareType(
		    col - fallingPos.x,
		    row - fallingPos.y
	    );

	    if (polySquare != SquareType.EMPTY) {
		return polySquare;
	    }
	}
	return squares[col][row];
    }

    public void randomBoard() {
	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		squares[col][row] = SquareType.values()[RND.nextInt(SquareType.values().length)];
	    }
	}
	notifyListeners();
    }

    public void addBoardListener(BoardListener bl){
	listeners.add(bl);
    }

    private void notifyListeners(){
	for (BoardListener bl : listeners){
	    bl.boardChanged();
	}
    }
    public static void main(String[] args) {
	Board board = new Board(10, 20);
	board.randomBoard();
    }
}