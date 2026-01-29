package se.liu.simjo878.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CircleTest
{
    public static void main(String[] args) {
	final List<Circle> circles = new ArrayList<>();
	circles.add(new Circle(Color.BLACK));
	circles.add(new Circle(Color.BLUE));
	circles.add(new Circle(Color.GREEN));

	for (Circle circle : circles) {
	    //System.out.println(circle.);
	}
    }
}
