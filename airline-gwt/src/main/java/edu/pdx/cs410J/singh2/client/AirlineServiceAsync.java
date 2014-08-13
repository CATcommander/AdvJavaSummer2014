package edu.pdx.cs410J.singh2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

/**
 * The client-side interface to the ping service
 */
public interface AirlineServiceAsync {

    void addFlight(AbstractAirline abstractAirline, AsyncCallback<ArrayList<AbstractAirline>> asyncCallback);
    void search(String airlineName, String src, String dest, AsyncCallback<Airline> async);
}
