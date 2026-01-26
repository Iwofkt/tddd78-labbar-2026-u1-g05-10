package se.liu.simjo878.tetris;

public class BoardTester
{
    public static void main(String[] args) {
	Board board = new Board(10, 10);
	BoardToTextConverter converter = new BoardToTextConverter();
	System.out.println(converter.convertToText(board));

	for (int i = 0; i < 10; i++) {
	    board.randomBoard();
	    System.out.println(converter.convertToText(board));
	}
    }
}
