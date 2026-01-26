package se.liu.simjo878.tetris;

import java.util.Random;

public class Board
{
    private SquareType[][] squares;
    private int width;
    private int height;

    private final static Random RND = new Random();

    public Board(int width, int height) {
	this.width = width;
	this.height = height;
	squares = new SquareType[width][height];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		squares[x][y] = SquareType.EMPTY;
	    }
	}
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
