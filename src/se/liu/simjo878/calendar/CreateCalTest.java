package se.liu.simjo878.calendar;
import java.util.ArrayList;

public class CreateCalTest
{
    public static void main(String[] args) {
        Cal cal = new Cal(new ArrayList<>());

        cal.book(2025, "january", 10, 9, 0, 10, 0, "app 1");
        cal.book(2025, "january", 10, 10, 30, 11, 30, "app 2");
        cal.book(2025, "january", 11, 13, 0, 14, 0, "app 3");
        cal.book(2025, "february", 1, 8, 15, 9, 0, "app 4");
        cal.book(2025, "february", 3, 15, 0, 17, 0, "app 5");
        cal.book(2025, "march", 20, 18, 0, 20, 0, "app 6");
        cal.book(2025, "april", 5, 12, 0, 13, 0, "app 7");

        cal.show();
    }
}
