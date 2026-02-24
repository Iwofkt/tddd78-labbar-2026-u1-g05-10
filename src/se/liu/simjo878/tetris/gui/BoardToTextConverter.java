package se.liu.simjo878.tetris.gui;

import se.liu.simjo878.tetris.Board;

public class BoardToTextConverter
{
    public String convertToText(Board board){

	StringBuilder builder = new StringBuilder();
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		switch (board.getVisibleSquareAt(x, y)) {
		    case EMPTY -> builder.append(" - ");
		    case I -> builder.append(" I ");
		    case J -> builder.append(" J ");
		    case Z -> builder.append(" Z ");
		    case L -> builder.append(" L ");
		    case O -> builder.append(" O ");
		    case S -> builder.append(" S ");
		    case T -> builder.append(" T ");
		    case OUTSIDE ->  builder.append("#");
		    default -> builder.append(" UNKNOWN ");
		}
	    }
	    builder.append("\n");
	}
	return (builder.toString());
    }
}
