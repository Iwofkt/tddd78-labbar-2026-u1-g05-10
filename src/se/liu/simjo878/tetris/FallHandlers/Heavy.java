package se.liu.simjo878.tetris.FallHandlers;

import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.Poly;
import se.liu.simjo878.tetris.SquareType;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Heavy implements FallHandler {
    private final FallHandler defaultHandler = new DefaultHandler();

    @Override
    public boolean hasCollision(final Board board, Point oldPos) {
	Poly falling = board.getFalling();
	if (falling == null) {
	    return false; // Ingen kollisionshantering om inget block faller
	}

	boolean onlyMovedDown = false;

	Point fallingPos = board.getFallingPos();
	if (oldPos != null){
	    onlyMovedDown = fallingPos.x == oldPos.x;
	} else {
	    onlyMovedDown = true;
	}

	if (!onlyMovedDown) {
	    // Om den fallande brickan inte rör sig nedåt, använd den vanliga kollisionshanteraren
	    return defaultHandler.hasCollision(board, fallingPos);
	}

	List<Point> overlapping = new ArrayList<>();

	// Hitta alla överlappande block
	for (int x = 0; x < falling.getSize(); x++) {
	    for (int y = 0; y < falling.getSize(); y++) {
		SquareType blockSquare = falling.getSquareType(x, y);

		if (blockSquare == SquareType.EMPTY) {
		    continue; // Om det är ett tomt block, hoppa över det
		}

		int boardX = fallingPos.x + x;
		int boardY = fallingPos.y + y;

		SquareType boardSquare = board.getSquareType(boardX, boardY);

		if (boardSquare == SquareType.OUTSIDE) {
		    return true; // kollision med OUTSIDE
		}
		if (boardSquare != SquareType.EMPTY) {
		    overlapping.add(new Point(boardX, boardY)); // Överlappar ett fast block
		}
	    }
	}

	for (Point p : overlapping) {
	    int x = p.x;
	    int y = p.y;
	    if (!validMove(board, x, y)) {
		return true;
	    }
	}

	for (Point p : overlapping) {
	    int x = p.x;
	    int y = p.y;
	    moveDown(board, x, y);
	    }
	return false;
    }

    private boolean moveDown(Board board, int x, int y) {

	while (true) {
	    if (board.getSquareType(x, y+1) == SquareType.OUTSIDE) {
		return false;
	    }
	    else if (board.getSquareType(x, y + 1) == SquareType.EMPTY) {
		board.setSquareType(x, y + 1, board.getSquareType(x, y));
		board.setSquareType(x, y, SquareType.EMPTY);
		return true;
	    }
	    if (moveDown(board, x, y + 1)) {
		continue;
	    }
	    return false;
	}
    }

    private boolean validMove(Board board, int x, int y) {
	while (true) {
	    if (board.getSquareType(x, y) == SquareType.OUTSIDE) {
		return false;
	    } else if (board.getSquareType(x, y) == SquareType.EMPTY) {
		return true;
	    }
	    y += 1;
	}
    }
}
