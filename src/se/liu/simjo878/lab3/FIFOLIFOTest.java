package se.liu.simjo878.lab3;

import se.liu.simjo878.lab1.Person;

import java.time.LocalDate;
import java.util.Date;

public class FIFOLIFOTest
{
    public static void main(String[] args) {
	Person p1 = new Person("Peter", LocalDate.of(1990, 1, 1));
	Person p2 = new Person("John", LocalDate.of(2000, 10, 13));
	Person p3 = new Person("Atle", LocalDate.of(1900, 8, 25));
	Stack stack = new Stack();
	Queue queue = new Queue();

	// asserts does not work!!!!

	// Stack tests
	stack.push(p1);
	System.out.printf("Stack Size: %d\n", stack.size());
	System.out.printf("Stack Size: %b\n", stack.contains(p1));
	System.out.printf("Stack Size: %b\n", stack.contains(p2));
	stack.push(p2);
	stack.push(p3);
	stack.pop();
	assert stack.contains(p1);
	assert stack.contains(p2);
	assert !stack.contains(p3);
	System.out.println("passed all stack tests.");

	// Stack tests
	queue.enqueue(p1);
	System.out.printf("Stack Size: %d\n", queue.size());
	System.out.printf("Stack Size: %b\n", queue.contains(p1));
	System.out.printf("Stack Size: %b\n", queue.contains(p2));
	queue.enqueue(p2);
	queue.enqueue(p3);
	queue.dequeue();
	assert !stack.contains(p1);
	assert stack.contains(p2);
	assert stack.contains(p3);
	System.out.println("passed all queue tests.");
    }
}
