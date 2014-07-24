package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

/**
 * This class has information about a Flight which extends
 * from AbstractFlight
 * Airline's flights are sorted alphabetically by their source code. Flights
 * that depart from the SAME airport should be sorted chronologically by
 * their departure time
 *
 * For regular printing/writing - DateFormat.SHORT (6/17/94 9:47 PM)
 * For pretty printing/writing  - DateFormat.LONG  (June 17, 1994 9:47:45 PM PDT)
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {

    private int flightNumber;
    private String source;
    private String departTime;
    private String departDay;
    private String destination;
    private String arriveTime;
    private String arrivalDay;

    /**
     * @param flightNumber number of the flight
     * @param source       three-letter code of departure airport
     * @param departTime   departTime date and time in DateFormat.SHORT (24-time is no longer supported)
     * @param destination  three-letter code of arrival airport
     * @param arriveTime   arrival date and time in DateFormat.SHORT (24-time is no longer supported)
     */
    public Flight(int flightNumber, String source, String departTime, String departDay, String destination, String arriveTime, String arrivalDay) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.departTime = departTime;
        this.departDay = departDay;
        this.destination = destination;
        this.arriveTime = arriveTime;
        this.arrivalDay = arrivalDay;
    }

    /**
     * Just returns the flight number
     *
     * @return flightNumber
     * returns the flight number
     */
    public int getNumber() {
        return flightNumber;
    }

    /**
     * Returns the Date object with validated and formatted date
     *
     * @return formatted Date object
     */
    @Override
    public Date getDeparture() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY hh:mm a", Locale.US);
        simpleDateFormat.setLenient(false);

        Date date = null;

        try {
            date = simpleDateFormat.parse(departTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Returns Departure string that is formatted using DateFormat.SHORT
     *
     * @return departTime
     *         returns validated DateFormat Arrival string
     */
    public String getDepartureString() {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(this.getDeparture());
    }

    /**
     * Just returns the name of flight's departure airport
     *
     * @return source
     * returns the name of departure airport
     */
    public String getSource() {
        String airportName = null;

        airportName = AirportNames.getName(this.source.toUpperCase());
        if (airportName != null)
            return airportName;
        System.out.println(airportName);
        return source;
    }

    public String getSourceThreeLetterCode() {
        System.out.println(source);
        return source;
    }


    /**
     * Just returns the name of flight's destination airport
     *
     * @return destination
     * returns the name of destination airport
     */
    public String getDestination() {
        String airportName;

        airportName = AirportNames.getName(this.destination.toUpperCase());
        if (airportName != null)
            return airportName;
        System.out.println(airportName);
        return destination;
    }


    public String getDestThreeLetterCode() {
        System.out.println(destination);
        return destination;
    }
    /**
     * Returns the Date object with validated and formatted date
     *
     * @return formatted Date object
     */
    @Override
    public Date getArrival() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY hh:mm a", Locale.US);
        simpleDateFormat.setLenient(false);

        Date date = null;

        try {
            date = simpleDateFormat.parse(arriveTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public long getDuration() {
        long minutes;

        minutes = (this.getArrival().getTime() - this.getDeparture().getTime())/60000;
        System.out.println("duration: " + minutes);
        return minutes;
    }

    public String getDepartYearFourDigits() {
        return departTime;
    }

    public String getArrivalYearFourDigits() {
        return arriveTime;
    }
    /**
     * Returns Arrival string that is formatted using DateFormat.SHORT
     *
     * @return arriveTime
     *         returns validated DateFormat Arrival string
     */
    public String getArrivalString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this.getArrival());
    }

    public int compareTo(Flight flight) {

        // return 0 THIS object equal
        // return -1 THIS object is less than the flight
        // return 1 THIS object is greater than the flight

        //if (this.getSource().compareTo(flight.getSource()) != 0)
          //  return (this.getSource().compareTo(flight.getSource()));

        //return (this.getSource().compareTo(flight.getSource()) == 0) ? (this.getDepartureString().compareTo(flight.getDepartureString())) : 0;
        if(this.getSource().compareTo((flight).getSource()) != 0) {
            return (this.getSource().compareTo(flight.getSource()));
        }
        else
            return this.getDepartureString().compareTo(flight.getDepartureString());
    }


}

/*

public int compareTo(Flight o) {
        if(this.getSource().compareTo((o).getSource()) != 0) {
            return (this.getSource().compareTo(o.getSource()));
        }
        else
            return this.getDepartureString().compareTo(o.getDepartureString());
    }


int result = this.source1.compareTo(o.getSource());

        if (result == 0) {
            int result1 = this.departureDateTime.compareTo(o.getDeparture());
            if (result1 == 0) {
                return 0;
            } else {
                return result1;
            }

        } else {
            return result;
        }

 */