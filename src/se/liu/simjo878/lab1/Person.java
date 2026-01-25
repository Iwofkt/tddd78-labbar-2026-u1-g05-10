package se.liu.simjo878.lab1;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String name;
    private LocalDate birthDay;

    public Person(String name, LocalDate birthDay) {
	this.name = name;
	this.birthDay = birthDay;
    }

    public int getAge() {
	return Period.between(birthDay, LocalDate.now()).getYears();
    }

    @Override public String toString() {
	return (name + " " + getAge());
    }

    public static void main(String[] args) {
	Person p1 = new Person("Peter", LocalDate.of(1990, 1, 1));
	Person p2 = new Person("John", LocalDate.of(2000, 10, 13));
	Person p3 = new Person("Atle", LocalDate.of(1900, 8, 25));
	System.out.println(p1.getAge());
	System.out.println(p1);
	System.out.println(p2);
	System.out.println(p3);
    }
}
