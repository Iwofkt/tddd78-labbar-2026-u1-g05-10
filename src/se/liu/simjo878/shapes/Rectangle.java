package se.liu.simjo878.shapes;

import java.awt.*;

public class Rectangle extends AbstractShape
{
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height, Color color) {
	super(x, y, color);
	if (width < 0 || height < 0) {
	    throw new IllegalArgumentException("Negative values for size are not allowed");
	}
	this.width = width;
	this.height = height;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    @Override public String toString() {
	return x + " " + y + " " + width + " " + height + " " + color;
    }

    @Override public void draw(final Graphics g) {
	//System.out.println("Ritar: " + this);
	g.setColor(color);
	g.fillRect(x, y, width, height);
	g.drawRect(x, y, width, height);
    }
}
