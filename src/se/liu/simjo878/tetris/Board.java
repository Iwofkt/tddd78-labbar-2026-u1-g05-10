package se.liu.simjo878.tetris;

import java.awt.*;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();
    private SquareType[][] squares;
    private int width;
    private int height;

    private Poly falling;
    private Point fallingPos;

    public Board(int width, int height) {
	squares = new SquareType[width][height];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		squares[x][y] = SquareType.EMPTY;
	    }
	}
	this.width = width;
	this.height = height;

	final TrenominoMaker trenominoMaker = new TrenominoMaker();
	this.falling = trenominoMaker.getPoly(5);
	this.fallingPos = new Point((width - 1) / 2, (height - 1) / 2);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public SquareType getSquareType(int x, int y){
	return squares[x][y];
    }

    public Poly getFalling() {
	return falling;
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public SquareType getVisibleSquareAt(int x, int y) {
	if (falling == null) {
	    return squares[x][y];
	}

	Rectangle fallingBounds = new Rectangle(
		fallingPos.x,
		fallingPos.y,
		falling.getWidth(),
		falling.getHeight()
	);

	if (fallingBounds.contains(x, y)) {
	    SquareType polySquare =
		    falling.getSquareType(x - fallingPos.x, y - fallingPos.y);

	    if (polySquare != SquareType.EMPTY) {
		return polySquare;
	    }
	}
	return squares[x][y];
    }


    public void randomBoard(){
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		squares[x][y] = SquareType.values()[RND.nextInt(SquareType.values().length)];
	    }
	}
    }

    public static void main(String[] args) {
	Board board = new Board(10, 10);
    }
}
