package se.liu.simjo878.lab3;

import se.liu.simjo878.lab1.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stack extends ListManipulator
{

    public void push(Person person) {
	elements.add(person);
    }

    public Person pop() {
	// removeLast better then remove in this case
	return elements.removeLast();
    }
}
