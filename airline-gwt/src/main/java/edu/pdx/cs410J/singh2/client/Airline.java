package edu.pdx.cs410J.singh2.client;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class holds information about all the airlines
 * it consists of multiple flights.
 */
public class Airline extends AbstractAirline {

    private ArrayList<Flight> listOfFlights;
    private String AirlineName;

    /** creates new <code> Airline </code>
     *
     * @param AirlineName
     * the name of the Airline
     * for example: "CS410J Air Express"
     */
    public Airline(String AirlineName) {
        this.AirlineName = AirlineName;
        listOfFlights = new ArrayList<Flight>();
    }

    public Airline() {
    }

    /**
     * Returns the name of the airline
     * @return Airline
     * name of the airline
     */
    public String getName() {
        return AirlineName;
    }
    /**
     * Airline has list of flights. This function just return flight list
     * @return listOfFlight
     * list of flights
     */
    public ArrayList<Flight> getFlights() {
        return listOfFlights;
    }
    /**
     * Add a flight to list of flights
     * @param flightToAdd
     * new flight to add to the list
     */
    public void addFlight(AbstractFlight flightToAdd) {
        listOfFlights.add(((Flight) flightToAdd));
        Collections.sort(listOfFlights);
    }
}