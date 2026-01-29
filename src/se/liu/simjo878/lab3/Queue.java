package se.liu.simjo878.lab3;

import se.liu.simjo878.lab1.Person;

public class Queue extends ListManipulator
{
    public void enqueue(Person person) {
	elements.add(person);
    }

    public Person dequeue() {
	// removeFirst better then remove in this case
	return elements.removeFirst();
    }
}
