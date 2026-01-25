package se.liu.simjo878.calendar;

public class SimpleDate {
    private int year;
    private Month month;
    private int day;

    public SimpleDate(int year, Month month, int day) {
	this.year = year;
	this.month = month;
	this.day = day;
    }

    @Override public String toString() {
	return 	String.format("%04d-%02d-%02d", year, month.getNumber(), day);
    }

    public int getYear() {
	return year;
    }

    public Month getMonth() {
	return month;
    }

    public int getDay() {
	return day;
    }
}
