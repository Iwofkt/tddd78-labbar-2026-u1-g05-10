package se.liu.simjo878.tetris;

public class Board
{
    private SquareType[][] squares;
    private int width;
    private int height;


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

    public static void main(String[] args) {
	Board board = new Board(10, 10);

	for (int y = 0; y < board.height; y++) {
	    for (int x = 0; x < board.width; x++) {
		System.out.print(board.squares[x][y] + " ");
	    }
	    System.out.println();
	}

    }
}
