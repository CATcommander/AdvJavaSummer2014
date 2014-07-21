package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractFlight;
import java.util.Date;

/**
 * This class has information about a Flight which extends
 * from AbstractFlight
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {

    private int flightNumber;
    private String source;
    private String departTime;
    private String destination;
    private String arriveTime;

    /**
     *
     * @param flightNumber
     *        number of the flight
     * @param source
     *        three-letter code of departure airport
     * @param departTime
     *        departTime date and time (24-time) format mm/dd/yyyy hh:mm
     * @param destination
     *        three-letter code of arrival airport
     * @param arriveTime
     *        arrival date and time (24-time) format mm/dd/yyyy hh:mm
     */
    public Flight(int flightNumber, String source, String departTime, String destination, String arriveTime) {
        this.flightNumber = flightNumber;
        this.departTime = departTime;
        this.source = source;
        this.destination = destination;
        this.arriveTime = arriveTime;
    }

    /**
     * Just returns the flight number
     * @return flightNumber
     *         returns the flight number
     */
    public int getNumber(){
        return flightNumber;
    }

    public Date getDeparture() {

        return null;
    }
    /**
     * Just returns the flight's departure time (Date and Time)
     * @return departTime
     *         flight's departure time
     */
    public String getDepartureString() {
        return departTime;
    }

    /**
     * Just returns the name of flight's departure airport
     * @return source
     *         returns the name of departure airport
     */
    public String getSource() {
        return source;
    }

    /**
     * Just returns the name of flight's destination airport
     * @return destination
     *         returns the name of destination airport
     */
    public String getDestination() {
        return destination;
    }

    public Date getArrival() {
        return null;
    }
    /**
     * Just returns the flight's arrival time (Date and Time)
     * @return arriveTime
     *         returns flight's arrival time
     */
    public String getArrivalString() {
        return arriveTime;
    }

    public int compareTo(Flight flight){

        return 0;
    }
}
