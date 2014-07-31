package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all keys and values from the server
     */
    public Response getAllKeysAndValues() throws IOException
    {
        return get(this.url);
    }

    /**
     * Returns all values for the given key
     */
   /* public Response getValues( String key ) throws IOException
    {
        return get(this.url, "key", key);
    }*/

    // returns all flights for the given airline
    public Response getFlights(String name) throws IOException
    {
        return get(this.url, "name", name);
    }

    public Response addKeyValuePair( String key, String value ) throws IOException
    {
        return post( this.url, "key", key, "value", value );
    }

    // post (this.url, "airline", airline, "flight number", flight number, "src", src)

    public Response addNewFlight(String name, String fn, String s, String dt, String de, String at) throws IOException {
        String airlineName = name;
        String flightNumber = fn;
        String src = s;
        String departTime = dt;
        String dest = de;
        String arriveTime = at;

        return post(this.url, "name", airlineName, "flightNumber", flightNumber, "src", src, "departTime", departTime, "dest", dest, "arriveTime", arriveTime);
    }

}
