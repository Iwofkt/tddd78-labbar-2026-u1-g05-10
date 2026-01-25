package se.liu.simjo878.calendar;

public class TimePoint
{
    private int hour;
    private int minute;

    public TimePoint(int hour, int minute) {
	this.hour = hour;
	this.minute = minute;
    }

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }

    @Override public String toString() {
	return String.format("%02d:%02d", hour, minute);
    }

    public int compareTo(TimePoint other) {
	int thisTime  = hour * 60 + minute;
	int otherTime = other.hour * 60 + other.minute;
	return Integer.compare(thisTime, otherTime);
    }
}
