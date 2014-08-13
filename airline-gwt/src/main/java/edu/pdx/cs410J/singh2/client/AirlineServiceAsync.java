package edu.pdx.cs410J.singh2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAirline;

/**
 * The client-side interface to the ping service
 */
public interface AirlineServiceAsync {

  /**
   * Return the current date/time on the server
   */
  void airline(AsyncCallback<AbstractAirline> async);
}
