package se.liu.simjo878.shapes;

import java.awt.*;

public class Circle extends AbstractShape
{
    private int radius;

    public Circle(int x, int y, int radius, Color color) {
	super(x, y, color);
	if (radius < 0) {
	    throw new IllegalArgumentException("Negativ radie!");
	}
	this.radius = radius;
    }

    public int getRadius() {
	return radius;
    }

    @Override public String toString() {
	return getX() + " " + getY() + " " + radius + " " + getColor();
    }

    @Override public void draw(final Graphics g) {
	//System.out.println("Ritar: " + this);
	g.setColor(color);
	g.fillOval(x, y, radius, radius);  // fyll
	g.drawOval(x, y, radius, radius); // calc. from radius!

    }

}
