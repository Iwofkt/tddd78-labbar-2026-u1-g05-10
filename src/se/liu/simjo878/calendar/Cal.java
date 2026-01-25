package se.liu.simjo878.calendar;

import java.util.ArrayList;
import java.util.List;

public class Cal {

    private List<Appointment> appointments = null;

    public Cal(List<Appointment> appointments)
    {
	this.appointments = appointments;
    }


    public void show()
    {
	for (Appointment appointment : appointments){
	    System.out.println(appointment + " " + appointment.getSubject());
	}
    }

    public void book(int year, String month, int day,
		     int startHour, int startMinute, int endHour,
		     int endMinute, String subject)
    {
	// inparameter control

	if (year <= 1970){
	    throw new IllegalArgumentException("year must be greater than 1970");
	}

	if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23){
	    throw new IllegalArgumentException("Hours must be between 0 and 23");
	}

	if(startMinute < 0 || startMinute > 59 || endMinute < 0 || endMinute > 59){
	    throw new IllegalArgumentException("Minutes must be between 0 and 59");
	}

	if (Month.getMonthNumber(month) == -1){
	    throw new IllegalArgumentException("unknown month: " + month);
	}

	if (day < 1 || day > Month.getMonthLength(month)){
	    System.out.println(Month.getMonthNumber(month));
	    throw new IllegalArgumentException("Invalid day: " + day + " in month: " + month);
	}

	// creation of an appointment
	Month validMonth = new Month(month, Month.getMonthNumber(month), Month.getMonthLength(month));

	SimpleDate simpleDate = new SimpleDate(year, validMonth, day);

	TimePoint startPoint = new TimePoint(startHour, startMinute);
	TimePoint endPoint = new TimePoint(endHour, endMinute);

	TimeSpan appointmentSpan = new TimeSpan(startPoint, endPoint);

	Appointment appointment = new Appointment(subject, simpleDate, appointmentSpan);

	//add appointmenmt to list
	appointments.add(appointment);

    }
}
