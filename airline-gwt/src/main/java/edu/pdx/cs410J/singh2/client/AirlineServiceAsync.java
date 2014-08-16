package edu.pdx.cs410J.singh2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

/**
 * The client-side interface to the ping service
 */
public interface AirlineServiceAsync {

    /**
     * add a flight with airline
     * @param airlineName name of the airline
     * @param flight  flight to add
     * @param async  async callback to talk to server
     */
    void addFlight(String airlineName, Flight flight, AsyncCallback<ArrayList<Airline>> async);

    /**
     * perform a search on the server side
     * @param airlineName name of the airline
     * @param src  source airport
     * @param dest destination airport
     * @param async  async callback to talk to server
     */
    void search(String airlineName, String src, String dest, AsyncCallback<String> async);

    /**
     * function to display all the flights
     * @param async async callback to talk to server
     */
    void displayAll(AsyncCallback<ArrayList<Airline>> async);
}
