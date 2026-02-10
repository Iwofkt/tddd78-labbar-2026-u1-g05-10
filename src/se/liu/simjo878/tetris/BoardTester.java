package se.liu.simjo878.tetris;

public class BoardTester
{
    public static void main(String[] args) {
	Board board = new Board(12, 20);
	board.randomBoard();

	TetrisViewer viewer = new TetrisViewer(board);
	BoardToTextConverter converter = new BoardToTextConverter();
	viewer.show();
    }
}