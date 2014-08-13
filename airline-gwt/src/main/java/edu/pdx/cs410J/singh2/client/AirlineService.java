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
@RemoteServiceRelativePath("airline")
public interface AirlineService extends RemoteService {

    public ArrayList<AbstractAirline> addFlight(AbstractAirline airline);
    public Airline search(String airlineName, String src, String dest);
}
