package se.liu.simjo878.tetris;

public class BoardToTextConverter
{
    public String convertToText(Board board){

	StringBuilder builder = new StringBuilder();

	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		switch (board.getSquareType(x, y)){
		    case EMPTY -> builder.append(" - ");
		    case I -> builder.append(" % ");
		    case J -> builder.append(" # ");
		    case Z -> builder.append(" & ");
		    case L -> builder.append(" @ ");
		    case O -> builder.append(" £ ");
		    case S -> builder.append(" $ ");
		    case T -> builder.append(" ¥ ");
		    default -> builder.append(" UNKNOWN ");
		}
	    }
	    builder.append("\n");
	}
	return (builder.toString());
    }
}
