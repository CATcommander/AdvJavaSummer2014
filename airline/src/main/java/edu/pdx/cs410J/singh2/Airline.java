package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

/**
 * This class holds information about all the airlines
 * it consists of multiple flights.
 */
public class Airline extends AbstractAirline{

    private static ArrayList<AbstractFlight> listOfFlights;
    private static String AirlineName;

    /** creates new <code> Airline </code>
     *
     * @param AirlineName
     *        the name of the Airline
     *        for example: "CS410J Air Express"
     */
    public Airline(String AirlineName) {
        this.AirlineName = AirlineName;
        listOfFlights = new ArrayList<AbstractFlight>();
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
    public ArrayList<AbstractFlight> getFlights() {
        return listOfFlights;
    }

    /**
     * Add a flight to list of flights
     * @param flightToAdd
     *        new flight to add to the list
     */
    public void addFlight(AbstractFlight flightToAdd) {
        listOfFlights.add(flightToAdd);
    }

}
