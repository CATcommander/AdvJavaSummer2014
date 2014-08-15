package edu.pdx.cs410J.singh2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.singh2.client.Airline;
import edu.pdx.cs410J.singh2.client.AirlineService;
import edu.pdx.cs410J.singh2.client.Flight;

import java.util.ArrayList;


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
        AbstractAirline airline = new Airline();

        if (airlineName != null) {
            // if airline exists
            if (airlineDatabase.contains(airlineName)) {
                // for each airline in airlineDatabase
                for (AbstractAirline abstractAirline: airlineDatabase) {
                    if (abstractAirline.getName().equalsIgnoreCase(airlineName)) {
                        for (Object flight: abstractAirline.getFlights()){
                            if( ((AbstractFlight) flight).getSource().equals(src) &&
                                    ((AbstractFlight) flight).getDestination().equals(dest)) {
                                    airline.addFlight(((AbstractFlight) flight));
                            }
                        }
                    }
                }
            }
        }

        return ((Airline) airline);
    }

    @Override
    protected void doUnexpectedFailure(Throwable throwable) {
        throwable.printStackTrace(System.err);
        super.doUnexpectedFailure(throwable);
    }
}
