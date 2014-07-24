package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.*;

/**
 * This class holds information about all the airlines
 * it consists of multiple flights.
 */
public class Airline extends AbstractAirline{

    private ArrayList<Flight> listOfFlights;
    private String AirlineName;

    /** creates new <code> Airline </code>
     *
     * @param AirlineName
     *        the name of the Airline
     *        for example: "CS410J Air Express"
     */
    public Airline(String AirlineName) {
        this.AirlineName = AirlineName;
        listOfFlights = new ArrayList<Flight>();
    }

    /**
     * Returns the name of the airline
     * @return Airline
     *         name of the airline
     */
    public String getName() {
        return AirlineName;
    }

    /**
     * Airline has list of flights. This function just return flight list
     * @return listOfFlight
     *         list of flights
     */
    public Collection<Flight> getFlights() {
        return listOfFlights;
    }

    /**
     * Add a flight to list of flights
     * @param flightToAdd
     *        new flight to add to the list
     */
    public void addFlight(AbstractFlight flightToAdd) {

        listOfFlights.add(((Flight) flightToAdd));
        Collections.sort(listOfFlights);
    }

}
