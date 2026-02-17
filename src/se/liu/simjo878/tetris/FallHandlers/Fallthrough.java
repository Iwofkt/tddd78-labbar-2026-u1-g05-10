package se.liu.simjo878.tetris.FallHandlers;

import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.Poly;
import se.liu.simjo878.tetris.SquareType;

import java.awt.*;

public class Fallthrough implements FallHandler
{
    @Override public boolean hasCollision(final Board board, final Point oldPos) {

	Poly falling = board.getFalling();

	if (falling == null) return false;

	Point fallingPos = board.getFallingPos();

	for (int x = 0; x < falling.getSize(); x++) {
	    for (int y = 0; y < falling.getSize(); y++) {
		SquareType blockSquare = falling.getSquareType(x, y);
		if (blockSquare == SquareType.EMPTY) continue;

		int boardX = fallingPos.x + x;
		int boardY = fallingPos.y + y;

		// Kolla om koordinaten ligger inom spelplanen
		if (boardX < 0 || boardX >= board.getWidth() || boardY < 0 || boardY >= board.getHeight()) {
		    return true;
		}
	    }
	}
	return false;
    }
}
