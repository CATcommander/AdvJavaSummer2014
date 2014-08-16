package edu.pdx.cs410J.singh2.server;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.singh2.client.Airline;
import edu.pdx.cs410J.singh2.client.AirlineService;
import edu.pdx.cs410J.singh2.client.Flight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
    static final HashMap<String, Airline> airlineDatabase = new HashMap<>();

    /**
     * display all the flights to the user
     * @return return a list of airline with flights added
     */
    @Override
    public ArrayList<Airline> displayAll() {
        ArrayList<Airline> abstractAirlines = new ArrayList<>();
        abstractAirlines.addAll(airlineDatabase.values());

        return abstractAirlines;
    }

    /**
     * add a flight with airline name and flight
     * @param airlineName name of the airline
     * @param flight  flight to add
     * @return returns the list of airline and flights
     */
    @Override
    public ArrayList<Airline> addFlight(String airlineName, Flight flight) {
        System.out.println("add Flight");

        Airline airline = airlineDatabase.get(airlineName);

        // if airline does not exist
        if (airline == null) {
            System.out.println("airline is null");
            airline = new Airline(airlineName);
        }

        System.out.println("adding a new flight");
        airline.addFlight(flight);
        airlineDatabase.put(airlineName, airline);

        System.out.println("flight added?");

        // airline does exist
        ArrayList<Airline> airlineList = new ArrayList<>();
        airlineList.addAll(airlineDatabase.values());

        System.out.println("airlineList made with values");

        System.out.println(airlineList.toString());

        return airlineList;
    }

    /**
     * perform a search for find the matched flights
     * @param airlineName name of the airline
     * @param src   source airport
     * @param dest  destination airport
     * @return returns the airline with matched flights
     */
    @Override
    public String search(String airlineName, String src, String dest) {
        Airline airline = null;

        if (airlineDatabase.containsKey(airlineName)) {
            airline = airlineDatabase.get(airlineName);
            return prettySearch(airline, src, dest);
        }

        return airlineName + " does not exist";
    }

    /**
     * pretty print the search results
     * @param airline name of the airline
     * @param s  name of the source airport
     * @param d  name of the destination airport
     * @return returns the airline with flights
     */
    private String prettySearch(Airline airline, String s, String d)
    {
        Collection<Flight> Flights;
        Flights = airline.getFlights();
        String match = "";
        String howMany = "";
        int count = 0;

        boolean found = false;
        if (Flights.isEmpty()) {
            Window.alert("Flight List is Empty");
            return null;
        }


        for (Flight flight: Flights) {
            if (flight.getSource().compareToIgnoreCase(s) == 0 && flight.getDestination().compareToIgnoreCase(d) == 0) {
                match += ("Flight " + flight.getNumber());
                match += (" Departs " + flight.getSrcCode());
                match += (" at " + flight.getDepartNice());
                match += (" Arrives " + flight.getDestCode());
                match += (" at "  + flight.getArrivalNice() + ". Duration: " + flight.getDuration() + " minutes\n");
                found = true;
                count++;
            }
        }
        if (!found) {
            match += ("There are no flights that originate at " + s + " and terminate at " + d);
        }
        else {
            howMany += "\nFound " + count + " flight(s) that Originate at " + s + " and terminate at " + d;

        }
        howMany += "\n" + match;
        System.out.println("in pretty search " + match);
        return howMany;
    }

    /**
     * throw an exception if something goes wrong at server side
     * @param throwable to throw
     */

    @Override
    protected void doUnexpectedFailure(Throwable throwable) {
        throwable.printStackTrace(System.err);
        super.doUnexpectedFailure(throwable);
    }
}
