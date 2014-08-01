package edu.pdx.cs410J.singh2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AirlineServlet extends HttpServlet
{
    private final Map<String, Airline> airlineMap = new HashMap<String, Airline>();

    /**
     * handles GET request from client
     * @param request request URL from the client
     * @param response server's response to client's request
     * @throws ServletException server side error
     * @throws IOException
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter( "name", request );
        String src = getParameter("src", request);
        String dest = getParameter("dest", request);

        PrintWriter wr = response.getWriter();

        // search flag
        if (name != null && src != null && dest != null) {
            Airline airline;
            airline = airlineMap.get(name);

            if (airline == null) {
                wr.println("Error: Airline \'" + name + "\' does not exist");
            }
            else {
                prettySearch(airline, src, dest, response);
            }
        }
        else if (name != null) {
            Airline airline = airlineMap.get(name);

            if (airline == null) {
                wr.println("Error: Airline \'" + name + "\' does not exist");
            }
            else {
                prettyPrint(airline, response);
            }
            wr.flush();
        }
        else {

            if (!airlineMap.isEmpty()) {
                wr.write("Server contains " + airlineMap.size() + " airlines.\n");
                for (Map.Entry<String, Airline> entry : airlineMap.entrySet()) {
                    prettyPrint(entry.getValue(), response);
                }
            }
            else
                wr.write("\nServer contains 0 airline(s)");

            wr.flush();
        }
    }

    /**
     * handles POST method
     * @param request request URL from the client
     * @param response server's response to client's request
     * @throws ServletException server side error
     * @throws IOException
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String key = getParameter( "name", request );
        if (key == null) {
            missingRequiredParameter( response, key );
            return;
        }

        String flightNum = getParameter( "flightNumber", request );
        if ( flightNum == null) {
            missingRequiredParameter( response, flightNum );
            return;
        }

        int flightNumber = 0;

        if (!flightNum.isEmpty()) {
            try {
                flightNumber = Integer.parseInt(flightNum);
            } catch (NumberFormatException n) {
                throw new IOException("Invalid Flight Number");
            }
        }

        String src = getParameter( "src", request );
        if ( src == null) {
            missingRequiredParameter( response, src );
            return;
        }

        String departTime = getParameter( "departTime", request );
        if ( departTime == null) {
            missingRequiredParameter( response, departTime );
            return;
        }

        String dest = getParameter( "dest", request );
        if ( dest == null) {
            missingRequiredParameter( response, dest );
            return;
        }

        String arriveTime = getParameter( "arriveTime", request );
        if ( arriveTime == null) {
            missingRequiredParameter( response, arriveTime );
            return;
        }

        Airline airline = airlineMap.get(key);
        Flight flight;

        // if airline does not exist, create a new one
        if (airline == null) {
            airline = new Airline(key); // getParameter. name of the airline
        }

        flight = new Flight(flightNumber, src, departTime, dest, arriveTime);
        airline.addFlight(flight);

        this.airlineMap.put(key, airline);

        PrintWriter pw = response.getWriter();
        prettyPrint(airline, response);
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     *
     * @param response server's response to client's request
     * @param key name of the key
     * @throws IOException
     */
    private void missingRequiredParameter( HttpServletResponse response, String key )
            throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * get the parameter from server's response
     * @param name name of the key
     * @param request server's response
     * @return return the value if found, else returns null
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;
        }
        else {
            return value;
        }
    }

    /**
     * A separate pretty print function for handle search
     * @param airline airline object
     * @param s       source
     * @param d       destination
     * @param response server's response
     * @throws IOException
     */
    private void prettySearch(Airline airline, String s, String d, HttpServletResponse response) throws IOException
    {
        Collection<Flight> Flights;
        Flights = airline.getFlights();

        PrintWriter printWriter = response.getWriter();
        boolean found = false;

        if (Flights.isEmpty())
            throw new IOException("Flight List is Empty");

        printWriter.write("Airline " + airline.toString() + "\n");

        for (Flight flight: Flights) {
            if (flight.getSource().compareToIgnoreCase(s) == 0 && flight.getDestination().compareToIgnoreCase(d) == 0) {
                printWriter.write("Flight " + flight.getNumber());
                printWriter.write(" Departs " + flight.getSrcCode());
                printWriter.write(" at " + flight.getDepartLONG());
                printWriter.write(" Arrives " + flight.getDestCode());
                printWriter.write(" at " + flight.getArrivalLONG() + ". Duration: " + flight.getDuration() + " minutes\n");
                found = true;
            }
        }
        if (!found) {
            printWriter.write("There are no flights that originate at \'" + s + "\' airport and terminate at \'" + d + "\'");
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Pretty print when printing to web
     * @param airline airline object
     * @param response server's response to client's request
     */
    private void prettyPrint(Airline airline, HttpServletResponse response) {
        try {
            Collection<Flight> flightList;
            flightList = airline.getFlights();

            PrintWriter writer = response.getWriter();

            if (flightList == null)
                throw new AssertionError("Empty Airline List");

            writer.write("\nAirline " + airline.toString() + "\n");

            // 432 Portland OR, Sat Dec 27, 2010 1:22 PST LAX etc
            for (Flight flight : flightList) {
                writer.write("Flight " + flight.getNumber());
                writer.write(" Departs " + flight.getSrcCode());
                writer.write(" at " + flight.getDepartLONG());
                writer.write(" Arrives " + flight.getDestCode());
                writer.write(" at " + flight.getArrivalLONG() + ". Duration: " + flight.getDuration() + " minutes\n");
            }
            writer.flush();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (IOException ex) {
            System.err.println("ERROR: Invalid Response");

        }
    }
}
