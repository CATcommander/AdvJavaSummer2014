package edu.pdx.cs410J.singh2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

/**
 * A GWT remote service that returns a dummy airline
 */
@RemoteServiceRelativePath("airlineServlet")
public interface AirlineService extends RemoteService {

    /**
     * return a list of airlines to display to user
     * @return list of airlines
     */
    public ArrayList<Airline> displayAll();

    /**
     * search for flight given the airlineName, src and dest
     * @param airlineName name of the airline
     * @param src   source airport
     * @param dest  destination airport
     * @return returns the matched airline with flights
     */
    public String search(String airlineName, String src, String dest);

    /**
     * add a flight with airline name
     * @param airlineName name of the airline
     * @param flight  flight to add
     * @return list of airline with flights
     */
    public ArrayList<Airline> addFlight(String airlineName, Flight flight);
}
