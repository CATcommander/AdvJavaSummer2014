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
import java.util.Map;


/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
    // have a hashmap of airlines here to store all the airlines
    //ArrayList<AbstractAirline> airlineDatabase = new ArrayList<>();
    HashMap<String, AbstractAirline> airlineDatabase = new HashMap<>();

    public AbstractAirline airline()
    {
        Airline airline = new Airline();
        airline.addFlight( new Flight() );
        return airline;
    }

    @Override
    public ArrayList<AbstractAirline> displayAll() {
        ArrayList<AbstractAirline> abstractAirlines = new ArrayList<>();

        if (!airlineDatabase.isEmpty()) {
            for (Map.Entry<String, AbstractAirline> airlineEntry: airlineDatabase.entrySet()) {
                abstractAirlines.add(airlineEntry.getValue());
            }
        }
        return abstractAirlines;
    }

    @Override
    public ArrayList<AbstractAirline> addFlight(AbstractAirline airline, AbstractFlight flight) {

        boolean airlineExists = false;

        ArrayList<AbstractAirline> abstractAirlines = new ArrayList<>();

        if (airlineDatabase.containsValue(airline)) {
            for (Map.Entry<String, AbstractAirline> airlineEntry: airlineDatabase.entrySet()) {
                abstractAirlines.add(airlineEntry.getValue());
            }
            for (AbstractAirline abstractAirline: abstractAirlines) {
                if (abstractAirline.getName().equalsIgnoreCase(airline.getName())) {
                    abstractAirline.addFlight(flight);
                    return abstractAirlines;
                }
            }
        }

        airline.addFlight(flight);
        airlineDatabase.put(airline.getName(), airline);
        for (Map.Entry<String, AbstractAirline> airlineEntry: airlineDatabase.entrySet()) {
            abstractAirlines.add(airlineEntry.getValue());
        }

        return abstractAirlines;
/*
        if (airlineDatabase.contains(airline)) {
            for (AbstractAirline abstractAirline: airlineDatabase) {
                if (abstractAirline.getName().equalsIgnoreCase(airline.getName())) {

                    airline.addFlight(flight);
                    airlineDatabase.add(airline);
                }
            }
        }
        else {
            airline.addFlight(flight);
            airlineDatabase.add(airline);
        }

        return airlineDatabase;*/
    }

    @Override
    public Airline search(String airlineName, String src, String dest) {
        AbstractAirline airline = new Airline();

        if (airlineDatabase.containsKey(airlineName)) {
            airline = airlineDatabase.get(airlineName);
            Collection<AbstractFlight> abstractFlights = airline.getFlights();
            for (AbstractFlight flight: abstractFlights) {
                if (flight.getSource().equalsIgnoreCase(src) && flight.getDestination().equalsIgnoreCase(dest)){
                    return ((Airline) airline);
                }

            }
        }

       /* if (!airlineDatabase.isEmpty()) {
            if (airlineDatabase.contains(airline)) {
                for (AbstractAirline abc : airlineDatabase) {
                    if (abc.getName().equalsIgnoreCase(airlineName)) {
                        return ((Airline) abc);
                    }*/
                    // sb.append(abc.getFlights().toString());
                /*if (abc.getName().equalsIgnoreCase(airlineName)) {
                    for (Object f : abc.getFlights()) {

                        if (((Flight) f).getSource().equalsIgnoreCase(src) &&
                                ((Flight) f).getDestination().equalsIgnoreCase(dest)) {
                            //airline.addFlight(((Flight) f));
                            return ((Flight) f).getNumber() + ((Flight) f).getSource() + ((Flight) f).getDepartureString();
                        }
                    }
                }*/

        return ((Airline) airline);
    }

    private String prettySearch(Airline airline, String s, String d)
    {
        Collection<Flight> Flights;
        Flights = airline.getFlights();
        StringBuilder sb = new StringBuilder();

        boolean found = false;
        if (Flights.isEmpty()) {
            Window.alert("Flight List is Empty");
            return null;
        }

        sb.append("Airline " + airline.toString() + "\n");
        for (Flight flight: Flights) {
            if (flight.getSource().compareToIgnoreCase(s) == 0 && flight.getDestination().compareToIgnoreCase(d) == 0) {
                sb.append("Flight " + flight.getNumber());
                sb.append(" Departs " + flight.getSrcCode());
                sb.append(" at " + flight.getDepartNice());
                sb.append(" Arrives " + flight.getDestCode());
                sb.append(" at " + flight.getArrivalNice() + ". Duration: " + flight.getDuration() + " minutes\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("There are no flights that originate at \'" + s + "\' airport and terminate at \'" + d + "\'");
        }
        return sb.toString();
    }

    @Override
    protected void doUnexpectedFailure(Throwable throwable) {
        throwable.printStackTrace(System.err);
        super.doUnexpectedFailure(throwable);
    }
}
