package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String destination;
    private String arriveTime;


    /**
     * @param flightNumber number of the flight
     * @param source       three-letter code of departure airport
     * @param departTime   departTime date and time in DateFormat.SHORT (24-time is no longer supported)
     * @param destination  three-letter code of arrival airport
     * @param arriveTime   arrival date and time in DateFormat.SHORT (24-time is no longer supported)
     */
    public Flight(int flightNumber, String source, String departTime, String destination, String arriveTime) {
        this.flightNumber = flightNumber;

        this.source = source;
        this.departTime = departTime;
        this.destination = destination;
        this.arriveTime = arriveTime;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
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
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this.getDeparture());
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
    public String getDepartYearFourDigits() {
        return departTime;
    }

    /**
     * returns full three letter code
     * Example: POR -> Portland, OR
     * @return returns three letter code
     */
    public String getSrcCode() {
        String airportName;
        airportName = AirportNames.getName(this.source.toUpperCase());
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
     * returns three letter code
     * @return returns three letter code
     */
    public String getDestCode() {
        String airportName;
        airportName = AirportNames.getName(this.destination.toUpperCase());
        if (airportName != null)
            return airportName;
        return airportName;
    }
    /**
     * Returns the Date object with validated and formatted date
     *
     * @return formatted Date object
     */
    @Override
    public Date getArrival() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        simpleDateFormat.setLenient(false);

        Date date = null;

        try {
            date = simpleDateFormat.parse(arriveTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * returns the year in four digits
     * @return returns the year in four digits
     */
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
     *        flight object
     * @return
     *        return 0 THIS object equal
     *        return -1 THIS object is less than the flight
     *        return 1 THIS object is greater than the flight
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
