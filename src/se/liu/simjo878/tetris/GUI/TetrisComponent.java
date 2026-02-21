package se.liu.simjo878.tetris.GUI;

import se.liu.simjo878.tetris.Board;
import se.liu.simjo878.tetris.BoardListener;
import se.liu.simjo878.tetris.Highscore.Highscore;
import se.liu.simjo878.tetris.Highscore.HighscoreList;
import se.liu.simjo878.tetris.Poly;
import se.liu.simjo878.tetris.PowerUps;
import se.liu.simjo878.tetris.SquareType;

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
    private final static float GHOST_TRANSPARENCY = 0.2F;
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
	drawOverlay(g2d, 1, Color.BLACK);

	drawBoard(g2d);

	drawFallingPoly(g2d);

	// Rita poäng (uppe till höger)
	drawStats(g2d, "Points: " + board.getPoints(), 1);

	drawStats(g2d, "Level: " + board.getLevel(), 3);

	if (board.getGameOver()){
	    drawOverlay(g2d, MENU_TRANSPARENCY, new Color(70, 0, 0));
	    drawTitle(g2d, "GAME OVER", Color.RED);
	    drawGameStats(g2d);
	}

	else if (board.getGamePaused() && !board.getGameOver()){
	    drawOverlay(g2d, 0.3F, new Color(0, 0, 70));
	    drawTitle(g2d, "GAME PAUSED", Color.BLUE);
	    drawGameStats(g2d);
	}
    }

    // -- PAINT HELPER FUNCTIONS -- //
    private void drawOverlay(Graphics2D g2d, float transperency, Color color) {
	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperency));
	g2d.setColor(color);
	g2d.fillRect(0, 0, getWidth(), getHeight());
	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    private void drawBoard(Graphics2D g2d) {
	// Loopa igenom alla positioner på brädet

	for (int row = 0; row < board.getHeight(); row++) {
	    for (int col = 0; col < board.getWidth(); col++) {
		SquareType squareType = board.getSquareType(col, row);
		drawSquare(g2d, col, row, squareType, SQUARE_COLORS.get(squareType));
	    }
	}
    }


    private Color lighten(Color color, double factor) {
	int r = (int)Math.min(255, color.getRed() + 255 * factor);
	int g = (int)Math.min(255, color.getGreen() + 255 * factor);
	int b = (int)Math.min(255, color.getBlue() + 255 * factor);
	return new Color(r, g, b);
    }


    private void drawSquare(Graphics2D g2d, int col, int row, SquareType squareType, Color color) {
	int pixelX = MARGIN + col * (SQUARE_SIZE + MARGIN);
	int pixelY = MARGIN + row * (SQUARE_SIZE + MARGIN);


	// Rita fyllning
	g2d.setColor(color);
	g2d.fillRect(pixelX + SQUARE_BORDER/2, pixelY + SQUARE_BORDER/2, SQUARE_SIZE - SQUARE_BORDER, SQUARE_SIZE - SQUARE_BORDER);

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

		    // Kontrollera att positionen
		    if (boardCol >= 0 && boardCol < board.getWidth() && boardRow >= 0 && boardRow < board.getHeight()) {

			Color color = SQUARE_COLORS.get(squareType);

			if (color == null)color = Color.BLACK;
			else if (board.getPowerUp() == PowerUps.FALLTHROUGH) {
			    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, GHOST_TRANSPARENCY));
			}
			else if(board.getPowerUp() == PowerUps.HEAVY) {
			    color = Color.DARK_GRAY;
			}
			drawSquare(g2d, boardCol, boardRow, squareType, color);

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		    }
		}
	    }
	}
    }

    private void drawStats(Graphics2D g2d, String stat, int infoLevel) {

	// Set font (adjust size if needed)
	Font font = new Font("Arial", Font.BOLD, 20);
	g2d.setFont(font);

	// Get text width to align right
	FontMetrics metrics = g2d.getFontMetrics(font);
	int textWidth = metrics.stringWidth(stat);
	int textHeight = metrics.getAscent();

	int x = getWidth() - textWidth - TEXT_MARGIN;
	int y = textHeight + TEXT_MARGIN*infoLevel;

	g2d.setColor(Color.WHITE);
	g2d.drawString(stat, x, y);
    }

    private void drawGameStats(Graphics2D g2d) {
	// transparent overlay

	String scoreText = "Points: " + board.getPoints();

	Font scoreFont = new Font("Arial", Font.BOLD, 40);
	g2d.setFont(scoreFont);

	FontMetrics metrics = g2d.getFontMetrics(scoreFont);
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

    private void drawTitle(Graphics2D g2d, String title, Color color) {
	Font scorefont = new Font("Arial", Font.BOLD, 45);
	g2d.setFont(scorefont);

	FontMetrics metrics = g2d.getFontMetrics(scorefont);
	int textWidth = metrics.stringWidth(title);
	int textHeight = metrics.getAscent();

	int x = getWidth()/2 - textWidth/2;
	int y = getHeight()/2 - TEXT_MARGIN*5;

	g2d.setColor(color);
	g2d.drawString(title, x, y);
    }
}