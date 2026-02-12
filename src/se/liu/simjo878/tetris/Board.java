package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();
    private final static int MARGIN = 10;
    private final static int DOUBLE_MARGIN = 2*MARGIN;


    private SquareType[][] squares;
    private int width;
    private int height;

    private Poly falling = null;
    private Point fallingPos = null;

    private List<BoardListener> listeners;
    private final TetrominoMaker tetrominoMaker;

    // -- CONSTURCTOR -- //

    public Board(int width, int height) {
	this.width = width;
	this.height = height;

	squares = new SquareType[width + DOUBLE_MARGIN][height + DOUBLE_MARGIN];

	int totalWidth = width + DOUBLE_MARGIN;
	int totalHeight = height + DOUBLE_MARGIN;

	squares = new SquareType[totalWidth][totalHeight];

	//fill all with OUTSIDE
	for (SquareType[] column : squares) {
	    Arrays.fill(column, SquareType.OUTSIDE);
	}

	// then fill the "middle" with EMPTY
	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		squares[col + MARGIN][row + MARGIN] = SquareType.EMPTY;
	    }
	}
	this.listeners = new ArrayList<>();
	this.tetrominoMaker = new TetrominoMaker();
    }

    // -- GETTERS -- //

    public int getWidth() {
	return width;  // antal kolumner
    }

    public int getHeight() {
	return height;  // antal rader
    }

    public SquareType getSquareType(int col, int row) {
	// col = x-koordinat (kolumn), row = y-koordinat (rad)
	return squares[col+MARGIN][row+MARGIN];
    }

    public Poly getFalling() {
	return falling;
    }

    public Point getFallingPos() {
	return fallingPos;
    }

    public SquareType getVisibleSquareAt(int col, int row) {
	if (falling == null) {
	    return squares[col+MARGIN][row+MARGIN];
	}

	Rectangle fallingBounds = new Rectangle(
		fallingPos.x,
		fallingPos.y,
		falling.getWidth(),
		falling.getHeight()
	);

	if (fallingBounds.contains(col, row)) {
	    SquareType polySquare = falling.getSquareType(
		    col - fallingPos.x,
		    row - fallingPos.y
	    );

	    if (polySquare != SquareType.EMPTY) {
		return polySquare;
	    }
	}
	return squares[col+MARGIN][row+MARGIN];
    }

    // -- setters -- //
    private void setFalling(Poly falling) {
	this.falling = falling;
    }

    private void setFallingPos(Point fallingPos) {
	this.fallingPos = fallingPos;
    }


    // -- BOARD OPERATIONS -- //
    public void randomBoard() {
	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		squares[col][row] = SquareType.values()[RND.nextInt(SquareType.values().length)];
	    }
	}
	notifyListeners();
    }


    public void tick(){
	if (getFalling() != null && getFallingPos().y != (height-getFalling().getHeight())) {
	    moveFalling();
	}
	else {
	    setFalling();
	}
	notifyListeners();
    }


    private void moveFalling(){
	Point newPos = getFallingPos();
	newPos.y += 1;
	setFallingPos(newPos);
    }


    private void setFalling(){
	this.falling = tetrominoMaker.getPoly(
		RND.nextInt(SquareType.values().length-2)
	);
	this.fallingPos = new Point((width/2) - 1, 0);
    }


    public void move(Direction direction) {
	Point newPos = getFallingPos();
	newPos.x += (direction == Direction.LEFT ? 1 : -1);
	setFallingPos(newPos);
	notifyListeners();
    }

    private boolean hasCollision() {

    }

    // -- UPDATE LISTENERS -- //

    public void addBoardListener(BoardListener bl){
	listeners.add(bl);
    }

    private void notifyListeners(){
	for (BoardListener bl : listeners){
	    bl.boardChanged();
	}
    }
}