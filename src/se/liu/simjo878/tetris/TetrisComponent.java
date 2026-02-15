package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener
{
    private final Board board;

    // Grafiska konstanter

    private final static int SQUARE_SIZE = 30;  // pixelstorlek per ruta
    private final static int SQUARE_BORDER = 4;
    private final static int MARGIN = 3;        // mellanrum mellan rutor
    private final static int TEXT_MARGIN = 10;
    private final static float MENU_TRANSPARENCY = 0.4F;
    private HighscoreList myHighscoreList;


    // Färgkarta för SquareTypes

    private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();

    private static EnumMap<SquareType, Color> createColorMap() {
	EnumMap<SquareType, Color> colorMap = new EnumMap<>(SquareType.class);
	colorMap.put(SquareType.EMPTY, new Color(0, 0, 50)); // very dark blue
	colorMap.put(SquareType.I, Color.CYAN);
	colorMap.put(SquareType.O, Color.YELLOW);
	colorMap.put(SquareType.T, Color.MAGENTA);
	colorMap.put(SquareType.S, Color.GREEN);
	colorMap.put(SquareType.Z, Color.RED);
	colorMap.put(SquareType.J, Color.BLUE);
	colorMap.put(SquareType.L, Color.ORANGE);
	return colorMap;
    }

    // -- CONSTRUCTOR  -- //

    public TetrisComponent(Board board,  HighscoreList highscoreList) {
	this.board = board;
	this.myHighscoreList = highscoreList;
	board.addBoardListener(this);
    }

    // -- RENDERING FUNCTIONS -- //

    @Override public Dimension getPreferredSize() {
	// Beräkna storlek baserat på antal kolumner och rader
	int widthInPixels = board.getWidth() * (SQUARE_SIZE + MARGIN) + MARGIN*2;
	int heightInPixels = board.getHeight() * (SQUARE_SIZE + MARGIN) + MARGIN*2;
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

	drawBoard(g2d);

	// Rita fallande block
	drawFallingPoly(g2d);

	// Rita poäng (uppe till höger)
	drawCurrentScore(g2d);

	if (board.getGameOver()){
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, MENU_TRANSPARENCY));
	    g2d.setColor(Color.BLACK);
	    g2d.fillRect(0, 0, getWidth(), getHeight());
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	    drawGameOver(g2d);
	}
    }

    // -- PAINT HELPER FUNCTIONS -- //

    private void drawBoard(Graphics2D g2d) {
	// Loopa igenom alla positioner på brädet

	for (int row = 0; row < board.getHeight(); row++) {        // Rad för rad
	    for (int col = 0; col < board.getWidth(); col++) {     // Kolumn för kolumn
		SquareType squareType = board.getSquareType(col, row);
		drawSquare(g2d, col, row, squareType);
	    }
	}
    }


    private Color lighten(Color color, double factor) {
	int r = (int)Math.min(255, color.getRed() + 255 * factor);
	int g = (int)Math.min(255, color.getGreen() + 255 * factor);
	int b = (int)Math.min(255, color.getBlue() + 255 * factor);
	return new Color(r, g, b);
    }


    private void drawSquare(Graphics2D g2d, int col, int row, SquareType squareType) {
	int pixelX = MARGIN + col * (SQUARE_SIZE + MARGIN);
	int pixelY = MARGIN + row * (SQUARE_SIZE + MARGIN);

	Color color = SQUARE_COLORS.get(squareType);
	if (color == null) color = Color.BLACK;

	// Rita fyllning
	g2d.setColor(color);
	g2d.fillRect(pixelX + 2, pixelY + 2, SQUARE_SIZE - SQUARE_BORDER, SQUARE_SIZE - SQUARE_BORDER);

	// Ljuskant if det är ett block
	if (squareType != SquareType.EMPTY) {
	    Color borderColor = lighten(color, 0.5);
	    g2d.setColor(borderColor);
	    g2d.setStroke(new BasicStroke(4));
	    g2d.drawRect(pixelX + 1, pixelY + 1, SQUARE_SIZE - SQUARE_BORDER, SQUARE_SIZE - SQUARE_BORDER);
	}
	else{
	    g2d.setStroke(new BasicStroke(MARGIN-1));
	    g2d.drawRect(pixelX + 1, pixelY + 1, SQUARE_SIZE - SQUARE_BORDER/2, SQUARE_SIZE - SQUARE_BORDER/2);
	}
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
	for (int polyRow = 0; polyRow < falling.getSize(); polyRow++) {
	    for (int polyCol = 0; polyCol < falling.getSize(); polyCol++) {
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

    private void drawCurrentScore(Graphics2D g2d) {
	String scoreText = "Points: " + board.getPoints();

	// Set font (adjust size if needed)
	Font font = new Font("Arial", Font.BOLD, 20);
	g2d.setFont(font);

	// Get text width to align right
	FontMetrics metrics = g2d.getFontMetrics(font);
	int textWidth = metrics.stringWidth(scoreText);
	int textHeight = metrics.getAscent();

	int x = getWidth() - textWidth - TEXT_MARGIN;
	int y = textHeight + TEXT_MARGIN;

	g2d.setColor(Color.WHITE);
	g2d.drawString(scoreText, x, y);
    }

    private void drawGameOver(Graphics2D g2d) {
	String scoreText = "Points: " + board.getPoints();

	Font scorefont = new Font("Arial", Font.BOLD, 40);
	g2d.setFont(scorefont);

	FontMetrics metrics = g2d.getFontMetrics(scorefont);
	int textWidth = metrics.stringWidth(scoreText);
	int textHeight = metrics.getAscent();

	int x = getWidth()/2 - textWidth/2;
	int y = getHeight()/2 + TEXT_MARGIN;

	g2d.setColor(Color.WHITE);
	g2d.drawString(scoreText, x, y);

	// Rita alla highscores under poängen
	int yOffset = y + textHeight * 2; // starta lite under poängen

	Font highscoreFont = new Font("Arial", Font.PLAIN, 20);
	g2d.setFont(highscoreFont);

	FontMetrics scoreMetrics = g2d.getFontMetrics(highscoreFont);

	for (Highscore hs : myHighscoreList.getHighscores()) {

	    String text = hs.getName() + " - " + hs.getPoints();

	    int scoreTextWidth = scoreMetrics.stringWidth(text);
	    int scoreX = getWidth() / 2 - scoreTextWidth / 2;

	    g2d.drawString(text, scoreX, yOffset);

	    yOffset += scoreMetrics.getHeight(); // flytta ner för nästa rad
	}
    }
}