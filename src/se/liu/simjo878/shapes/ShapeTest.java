package se.liu.simjo878.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeTest
{
    public static void main(String[] args) {
	final List<Shape> shapes = new ArrayList<>();
	shapes.add(new Circle(5,3,1,Color.BLACK));
	shapes.add(new Circle(10,7,2,Color.BLUE));
	shapes.add(new Circle(5,15,8,Color.GREEN));
	shapes.add(new Rectangle(20,10,2,3,Color.RED));
	shapes.add(new Rectangle(10,10,4,2,Color.PINK));
	shapes.add(new Text(30,40,12, Color.BLACK, "Hello"));
	shapes.add(new Text(40,40,12, Color.BLUE, "Hello there"));

	for (Shape shape : shapes) {
	    System.out.println(shape.getX() + " " + shape.getY());
	    System.out.println(shape);
	}
    }
}
