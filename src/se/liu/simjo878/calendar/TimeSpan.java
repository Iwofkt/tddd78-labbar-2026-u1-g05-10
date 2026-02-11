package se.liu.simjo878.calendar;

public class TimeSpan {
    private final TimePoint start;
    private final TimePoint end;

    public TimeSpan(TimePoint start, TimePoint end) {
	this.start = start;
	this.end = end;
    }

    public TimePoint getStart() {
	return start;
    }

    public TimePoint getEnd() {
	return end;
    }

    @Override public String toString() {
	return (start + " - " + end);
    }
}
