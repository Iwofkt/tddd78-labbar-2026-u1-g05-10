package se.liu.simjo878.shapes;

import java.awt.*;
import java.util.Objects;

public abstract class AbstractShape implements Shape
{
    protected int x;
    protected int y;
    protected Color color;


    protected AbstractShape(int x, int y, Color color) {
	this.x = x;
	this.y = y;
	this.color = color;
    }

    @Override public int getX() {
	return x;
    }

    @Override public int getY() {
	return y;
    }

    @Override public Color getColor() {
	return color;
    }

    @Override public boolean equals(final Object o) {
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	final AbstractShape that = (AbstractShape) o;
	return x == that.x && y == that.y && Objects.equals(color, that.color);
    }

    @Override public int hashCode() {
	return Objects.hash(x, y, color);
    }
}
