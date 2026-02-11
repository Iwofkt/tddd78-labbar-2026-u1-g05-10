package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;

    // Grafiska konstanter

    private final static int SQUARE_SIZE = 40;  // pixelstorlek per ruta
    private final static int MARGIN = 5;        // mellanrum mellan rutor

    // Färgkarta för SquareTypes
    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();

    private static EnumMap<SquareType, Color> createColorMap() {
	EnumMap<SquareType, Color> colorMap = new EnumMap<>(SquareType.class);
	colorMap.put(SquareType.EMPTY, Color.BLACK);
	colorMap.put(SquareType.I, Color.CYAN);
	colorMap.put(SquareType.O, Color.YELLOW);
	colorMap.put(SquareType.T, Color.MAGENTA);
	colorMap.put(SquareType.S, Color.GREEN);
	colorMap.put(SquareType.Z, Color.RED);
	colorMap.put(SquareType.J, Color.BLUE);
	colorMap.put(SquareType.L, Color.ORANGE);
	return colorMap;
    }

    public TetrisComponent(Board board) {
	this.board = board;
	board.addBoardListener(this);
    }


    @Override public Dimension getPreferredSize() {
	// Beräkna storlek baserat på antal kolumner och rader
	int widthInPixels = board.getWidth() * (SQUARE_SIZE + MARGIN) + MARGIN;
	int heightInPixels = board.getHeight() * (SQUARE_SIZE + MARGIN) + MARGIN;
	return new Dimension(widthInPixels, heightInPixels);
    }

    @Override
    public void boardChanged() {
	repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	// Rita bakgrund
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, getWidth(), getHeight());

	// Rita fasta block
	drawBoard(g2d);

	// Rita fallande block
	drawFallingPoly(g2d);
    }

    private void drawBoard(Graphics2D g2d) {
	// Loopa igenom alla positioner på brädet
	for (int row = 0; row < board.getHeight(); row++) {        // Rad för rad
	    for (int col = 0; col < board.getWidth(); col++) {     // Kolumn för kolumn
		SquareType squareType = board.getSquareType(col, row);
		drawSquare(g2d, col, row, squareType);
	    }
	}
    }

    private void drawSquare(Graphics2D g2d, int col, int row, SquareType squareType) {
	// Konvertera brädekoordinater (col, row) till pixelkoordinater (x, y)
	int pixelX = MARGIN + col * (SQUARE_SIZE + MARGIN);
	int pixelY = MARGIN + row * (SQUARE_SIZE + MARGIN);

	// Hämta färg från mappningen
	Color color = SQUARE_COLORS.get(squareType);
	if (color == null) {
	    color = Color.BLACK;
	}

	// Rita fylld kvadrat
	g2d.setColor(color);
	g2d.fillRect(pixelX, pixelY, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawFallingPoly(Graphics2D g2d) {
	Poly falling = board.getFalling();
	if (falling == null) {
	    return;
	}

	Point fallingPos = board.getFallingPos();
	int startCol = fallingPos.x;  // startkolumn
	int startRow = fallingPos.y;  // startrad

	// Loopa igenom poly-blockets interna struktur
	for (int polyRow = 0; polyRow < falling.getHeight(); polyRow++) {
	    for (int polyCol = 0; polyCol < falling.getWidth(); polyCol++) {
		SquareType squareType = falling.getSquareType(polyCol, polyRow);

		if (squareType != SquareType.EMPTY) {
		    // Beräkna position på huvudbrädet
		    int boardCol = startCol + polyCol;
		    int boardRow = startRow + polyRow;

		    // Kontrollera att positionen ä // <-- viktigtr inom brädet
		    if (boardCol >= 0 && boardCol < board.getWidth() && boardRow >= 0 && boardRow < board.getHeight()) {
			drawSquare(g2d, boardCol, boardRow, squareType);
		    }
		}
	    }
	}
    }
}