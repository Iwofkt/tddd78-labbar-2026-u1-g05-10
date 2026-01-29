package se.liu.simjo878.shapes;

import java.awt.*;

public class Text extends AbstractShape
{
    private int size;
    private String text;

    public Text(int x, int y, int size, Color color, String text) {
	super(x, y, color);
	if (size < 0) {
	    throw new IllegalArgumentException("Size cannot be negative");
	}
	this.size = size;
	this.text = text;
    }

    public int getSize() {
	return size;
    }

    public String getText() {
	return text;
    }

    @Override public String toString() {
	return x + " " + y + " " + size + " " + color;
    }

    @Override public void draw() {
	System.out.println("Ritar: " + this);
    }
}
