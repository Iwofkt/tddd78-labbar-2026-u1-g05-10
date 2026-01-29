package se.liu.simjo878.tetris;

public class BoardTester
{
    public static void main(String[] args) {
	Board board = new Board(10, 8);
	TetrisViewer viewer = new TetrisViewer(board);
	BoardToTextConverter converter = new BoardToTextConverter();
	System.out.println(converter.convertToText(board));

	viewer.show();
    }
}
