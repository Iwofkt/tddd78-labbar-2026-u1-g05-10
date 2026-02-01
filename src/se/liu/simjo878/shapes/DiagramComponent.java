package se.liu.simjo878.shapes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramComponent extends JComponent
{
    private List<Shape> shapes;

    public DiagramComponent() {
	this.shapes = new ArrayList<>();
    }

    public void addShape(Shape s)
    {
	shapes.add(s);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);

	for (Shape s : shapes) {
	    s.draw(g);
	}
    }
}