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


/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
    // have a hashmap of airlines here to store all the airlines
    ArrayList<AbstractAirline> airlineDatabase = new ArrayList<>();

    public AbstractAirline airline()
    {
        Airline airline = new Airline();
        airline.addFlight( new Flight() );
        return airline;
    }

    @Override
    public ArrayList<AbstractAirline> displayAll() {
        if (!airlineDatabase.isEmpty())
            return airlineDatabase;
        return airlineDatabase;
    }

    @Override
    public ArrayList<AbstractAirline> addFlight(AbstractAirline airline) {

        boolean airlineExists = false;

        // if airline exists
        if (airline != null) {
            if (!airlineDatabase.isEmpty()) {
                for (AbstractAirline abstractAirline: airlineDatabase) {
                    if (abstractAirline.getName().contains(airline.getName())) {
                        abstractAirline.addFlight(((Airline) airline).getFlights().get(0));
                        airlineExists = true;
                    }
                }
            }
        }
        else {
            return airlineDatabase;
        }

        if (!airlineExists)
            airlineDatabase.add(airline);

        return airlineDatabase;
    }

    @Override
    public Airline search(String airlineName, String src, String dest) {
        AbstractAirline airline = new Airline(airlineName);

        airline = airlineDatabase.get(0);

        for (Object f: airline.getFlights()) {
            if (((Flight) f).getSource().equalsIgnoreCase(src) &&
                    ((Flight) f).getDestination().equalsIgnoreCase(dest)) {
                airline.addFlight(((Flight) f));
                return ((Airline) airline);
            }
        }
/*
        for (AbstractAirline air: airlineDatabase) {

            if (air.getName().equalsIgnoreCase(airline.getName())) {
                for (Object f: air.getFlights()) {
                    if (((Flight) f).getSource().equalsIgnoreCase(src) &&
                            ((Flight) f).getDestination().equalsIgnoreCase(dest)) {
                        airline.addFlight(((Flight) f));
                        return ((Airline) airline);
                    }
                }
            }
        }*/


        return ((Airline) airline);

        /*if (airlineDatabase.get(airlineName) != null) {
            // if airline exists
            if (airlineDatabase.contains(airlineName)) {
                // for each airline in airlineDatabase
                for (AbstractAirline abstractAirline: airlineDatabase)
                {
                    if (abstractAirline.getName().equalsIgnoreCase(airlineName))
                    {
                        for (Object flight: abstractAirline.getFlights())
                        {
                            if( ((AbstractFlight) flight).getSource().equalsIgnoreCase(src) &&
                                    ((AbstractFlight) flight).getDestination().equalsIgnoreCase(dest))
                            {
                                Window.alert("in search" + src + dest);
                                airline.addFlight(((AbstractFlight) flight));
                                Window.alert(prettySearch(((Airline) airline), src, dest));
                            }
                        }
                    }
                }
            }
            else
                return null;
        }*/
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
                sb.append(" at " + flight.getDepartLONG());
                sb.append(" Arrives " + flight.getDestCode());
                sb.append(" at " + flight.getArrivalLONG() + ". Duration: " + flight.getDuration() + " minutes\n");
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
