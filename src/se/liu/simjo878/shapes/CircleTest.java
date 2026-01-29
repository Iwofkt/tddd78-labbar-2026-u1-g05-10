package se.liu.simjo878.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CircleTest
{
    public static void main(String[] args) {
	final List<Circle> circles = new ArrayList<>();
	circles.add(new Circle(5,3,1,Color.BLACK));
	circles.add(new Circle(10,7,2,Color.BLUE));
	circles.add(new Circle(5,15,8,Color.GREEN));

	for (Circle circle : circles) {
	    System.out.println("x: " + circle.getX() + " y: " + circle.getY());
	}
    }
}
