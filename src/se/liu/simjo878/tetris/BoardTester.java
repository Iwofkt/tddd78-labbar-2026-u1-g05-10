package se.liu.simjo878.tetris;

public class BoardTester
{
    public static void main(String[] args) {
	Board board = new Board(12, 20);

	TetrisViewer viewer = new TetrisViewer(board);
	viewer.show();

    }
}