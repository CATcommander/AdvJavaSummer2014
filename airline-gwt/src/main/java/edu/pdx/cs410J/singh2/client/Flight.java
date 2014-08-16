package edu.pdx.cs410J.singh2.client;

import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;


/**
 * This class has information about a Flight which extends
 * from AbstractFlight
 * Airline's flights are sorted alphabetically by their source code. Flights
 * that depart from the SAME airport should be sorted chronologically by
 * their departure time
 *
 * For regular printing/writing - DateFormat.SHORT (6/17/94 9:47 PM)
 * For pretty printing/writing - DateFormat.LONG (June 17, 1994 9:47:45 PM PDT)
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {

    private int flightNumber;
    private String source;
    private String departTime;
    private String destination;
    private String arriveTime;

    /** Flight constructor
     * @param flightNumber number of the flight
     * @param source three-letter code of departure airport
     * @param departTime departTime date and time in DateFormat.SHORT (24-time is no longer supported)
     * @param destination three-letter code of arrival airport
     * @param arriveTime arrival date and time in DateFormat.SHORT (24-time is no longer supported)
     */
    public Flight(int flightNumber, String source, String departTime, String destination, String arriveTime) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.departTime = departTime;
        this.destination = destination;
        this.arriveTime = arriveTime;
    }

    public Flight() {
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
        DateTimeFormat dateTimeFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a", new DefaultDateTimeFormatInfo()){};

        Date date = dateTimeFormat.parse(departTime);

        return date;
    }
    /**
     * Returns Departure string that is formatted using DateFormat.SHORT
     *
     * @return departTime
     * returns validated DateFormat Arrival string
     */
    public String getDepartureString() {
        //DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");
        DateTimeFormat dateTimeFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a", new DefaultDateTimeFormatInfo()){};

        return dateTimeFormat.format(this.getDeparture());
    }
    /**
     * Just returns the name of flight's departure airport
     *
     * @return source
     * returns the name of departure airport
     */
    public String getSource() {
        return source.toUpperCase();
    }
/**
 * returns the year in four digits
 * @return returns the year in four digits
 */
    /**
     * returns full three letter code
     * Example: POR -> Portland, OR
     * @return returns three letter code
     */
    public String getSrcCode() {
        String airportName = AirportNames.getName(this.source.toUpperCase());
        if (airportName != null)
            return airportName;
        return airportName;
    }
    /**
     * Just returns the name of flight's destination airport
     *
     * @return destination
     * returns the name of destination airport
     */
    public String getDestination() {
        return destination.toUpperCase();
    }
    /**
     * returns the Date with LONG format into a String
     * @return Date Format LONG String
     */
    public String getDepartNice() {
       // DateTimeFormat format = DateTimeFormat.getFormat("EEE, MMM d, yyyy h:mm a");
        DateTimeFormat dateTimeFormat = new DateTimeFormat("EEE, MMM d, yyyy hh:mm a", new DefaultDateTimeFormatInfo()){};

        return dateTimeFormat.format(this.getDeparture());
    }
    /**
     * returns full airport code
     * @return returns full airport code
     */
    public String getDestCode() {
        String airportName = AirportNames.getName(this.destination.toUpperCase());
        if (airportName != null)
            return airportName;
        return airportName;
    }
    /**
     * Returns the Date object with validated and formatted date
     * @return formatted Date object
     */
    @Override
    public Date getArrival() {
       // DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        DateTimeFormat dateTimeFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a", new DefaultDateTimeFormatInfo()){};

        Date date = dateTimeFormat.parse(arriveTime);

        return date;
    }
    /**
     * Returns Arrival string that is formatted using DateFormat.SHORT
     *
     * @return arriveTime
     * returns validated DateFormat Arrival string
     */
    public String getArrivalString() {
       // DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        DateTimeFormat dateTimeFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a", new DefaultDateTimeFormatInfo()){};

        return dateTimeFormat.format(this.getArrival());
    }
    /**
     * returns Date Format LONG String
     * @return Date Format LONG String
     */
    public String getArrivalNice() {
        DateTimeFormat dateTimeFormat = new DateTimeFormat("EEE, MMM d, yyyy hh:mm a", new DefaultDateTimeFormatInfo()){};

        return dateTimeFormat.format(this.getArrival());
    }
    /**
     * get duration in minutes
     * @return returns flight duration in minutes
     */
    public long getDuration() {
        long minutes;
        minutes = (this.getArrival().getTime() - this.getDeparture().getTime())/60000;
        return minutes;
    }
    /**
     * compares a flight object with itself to check it its the same
     * @param flight
     * flight object
     * @return
     * return 0 THIS object equal
     * return -1 THIS object is less than the flight
     * return 1 THIS object is greater than the flight
     */
    public int compareTo(Flight flight) {
// return 0 THIS object is equal to flight
// return -1 THIS object is less than the flight
// return 1 THIS object is greater than the flight
        int SMALLER = -1;
        int SAME = 0;
        int GREATER = 1;

        if (this.source.compareTo(flight.getSource()) == 1) {
            return GREATER;
        }
        else if (this.source.compareTo(flight.getSource()) == -1) {
            return SMALLER;
        }
        else {
            if (this.getDeparture().before(flight.getDeparture())){
                return SMALLER;
            }
            else if (this.getDeparture().after(flight.getDeparture())) {
                return GREATER;
            }
            else {
                return SAME;
            }
        }
    }
}