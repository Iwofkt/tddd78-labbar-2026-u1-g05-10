package se.liu.simjo878.tetris;

import se.liu.simjo878.tetris.FallHandlers.DefaultHandler;
import se.liu.simjo878.tetris.FallHandlers.FallHandler;
import se.liu.simjo878.tetris.FallHandlers.Fallthrough;
import se.liu.simjo878.tetris.FallHandlers.Heavy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board
{
    private final static Random RND = new Random();
    private final static int NONE_SQUARES_MARGIN = 2;
    private final static int MARGIN = 10;
    private final static int DOUBLE_MARGIN = 2*MARGIN;

    private boolean gameOver = false;
    private boolean gamePaused = false;
    private int level = 0;

    private SquareType[][] squares;
    private int width;
    private int height;

    private Poly falling = null;
    private Point fallingPos = null;
    private FallHandler fallHandler;
    private final Heavy heavy = new Heavy();
    private final FallHandler defaultHandler = new DefaultHandler();
    private final Fallthrough fallthrough = new Fallthrough();
    private PowerUps powerUp = PowerUps.NORMAL;

    private List<BoardListener> listeners;
    private final TetrominoMaker tetrominoMaker;

    private static final Map<Integer,Integer> POINT_MAP = Map.of(0, 0,1, 40, 2, 100, 3, 300, 4, 1200);
    private int points = 0;

    // -- CONSTURCTOR -- //

    public Board(int width, int height) {
	this.width = width;
	this.height = height;
	this.fallHandler = defaultHandler;

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

    public int getMargin() {
	return MARGIN;
    }

    public SquareType getSquareType(int col, int row) {
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
		falling.getSize(),
		falling.getSize()
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

    public int getPoints() {
	return points;
    }

    public boolean getGameOver() {
	return gameOver;
    }

    public boolean getGamePaused() {
	return gamePaused;
    }

    public int getLevel() {
	return level;
    }

    public PowerUps getPowerUp() {
	return powerUp;
    }

    // -- SETTERS -- //

    private void setFallingPos(Point fallingPos) {
	this.fallingPos = fallingPos;
    }

    private void setPoints(int points) {
	this.points = points;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    // -- BOARD OPERATIONS -- //

    public void setGameOver(boolean gameOver) {
	this.gameOver = gameOver;
	notifyListeners();
    }

    public void setGamePaused(boolean gamePaused) {
	this.gamePaused = gamePaused;
	notifyListeners();
    }

    public void randomBoard() {
	for (int col = 0; col < width; col++) {
	    for (int row = 0; row < height; row++) {
		squares[col][row] = SquareType.values()[RND.nextInt(SquareType.values().length)];
	    }
	}
	notifyListeners();
    }


    public void tick(){

	if (gameOver || gamePaused){
	    return;
	}

	if (getFalling() != null) {
	    Point oldPos = new Point(fallingPos);
	    moveFalling(1);

	    if (fallHandler.hasCollision(this, oldPos)){
		moveFalling(-1);
		addFallingToBoard();
		removeFullRows();
		falling = null;
	    }
	}

	else {
	    newFalling();
	    if (fallHandler.hasCollision(this, fallingPos)){
		setGameOver(true);
	    }
	}
	notifyListeners();
    }

    //  -- tick helper functions

    private void moveFalling(int value){
	if (gameOver){
	    return;
	}
	Point newPos = getFallingPos();
	newPos.y += value;
	setFallingPos(newPos);
    }


    private void newFalling(){
	this.falling = tetrominoMaker.getPoly(
		RND.nextInt(SquareType.values().length-NONE_SQUARES_MARGIN)
	);
	this.fallingPos = new Point((width/2) - 1, 0);


	switch (RND.nextInt(0, 5)) {
	    case 0:
		fallHandler = heavy;
		powerUp = PowerUps.HEAVY;
		break;
	    case 4:
		fallHandler = fallthrough;
		powerUp = PowerUps.FALLTHROUGH;
		break;
	    default:
		fallHandler = defaultHandler;
		powerUp = PowerUps.NORMAL;
	}

    }


    private void addFallingToBoard() {
	for (int y = 0; y < falling.getSize(); y++) {
	    for (int x = 0; x < falling.getSize(); x++) {

		if (falling.getSquareType(x, y) != SquareType.EMPTY) {

		    int boardX = fallingPos.x + x + MARGIN;
		    int boardY = fallingPos.y + y + MARGIN;

		    squares[boardX][boardY] = falling.getSquareType(x, y);
		}
	    }
	}
    }

    private void removeFullRows() {
	// Gå igenom varje rad i den "synliga" spelplanen

	int rowAmount = 0;
	for (int row = 0; row < height; row++) {
	    boolean full = true;

	    for (int col = 0; col < width; col++) {
		if (squares[col + MARGIN][row + MARGIN] == SquareType.EMPTY) {
		    full = false;
		    break;
		}
	    }

	    if (full) {
		// Flytta ner alla rader ovanför
		for (int r = row; r > 0; r--) {
		    for (int c = 0; c < width; c++) {
			squares[c + MARGIN][r + MARGIN] = squares[c + MARGIN][r - 1 + MARGIN];
		    }
		}

		// Sätt översta raden till EMPTY
		for (int c = 0; c < width; c++) {
		    squares[c + MARGIN][MARGIN] = SquareType.EMPTY;
		}

		// Efter att ha tagit bort en rad, kolla samma rad igen
		row--;
		rowAmount++;
	    }
	}
	setPoints(getPoints() + POINT_MAP.get(rowAmount) * (getLevel() + 1));
    }

    //-- game interactions

    public void move(Direction dir) {

	if (fallingPos == null) {
	    return;
	}

	Point oldPos = new Point(fallingPos);

	fallingPos.x += (dir == Direction.LEFT ? 1 : -1);

	if (fallHandler.hasCollision(this, oldPos)) {
	    fallingPos = oldPos;
	}

	notifyListeners();
    }


    public void rotate(Direction dir) {

	if (falling == null || gameOver) {
	    return;
	}

	Point oldPos = new Point(fallingPos);

	Poly oldPoly = falling;
	falling = falling.rotate(dir == Direction.RIGHT);

	if (fallHandler.hasCollision(this, oldPos)) {
	    falling = oldPoly;
	}
	notifyListeners();
    }


    public void drop() {

	if (falling == null || gameOver) {
	    return;
	}

	while (true) {

	    Point oldPos = new Point(fallingPos);

	    fallingPos.y += 1;

	    if (fallHandler.hasCollision(this, oldPos)) {
		fallingPos = oldPos;
		break;
	    }
	}

	notifyListeners();
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

    public void setSquareType(int x,int y,SquareType type) {
	squares[x+MARGIN][y+MARGIN] = type;
    }
}