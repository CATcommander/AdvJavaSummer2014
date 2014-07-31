package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.web.HttpRequestHelper;

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
    //private final Map<String, String> airlineMap = new HashMap<String, String>();
    private final Map<String, Airline> airlineMap = new HashMap<String, Airline>();


    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String name = getParameter( "name", request );
        String src = getParameter("src", request);
        String dest = getParameter("dest", request);

        if (name != null && src == null && dest == null) {
            writeValue(name, response);

        } else {
            writeAllMappings(response);
        }

        // search flag is given
    //    if (name != null && src != null && dest != null) {
      //      Airline airline;
        //    airline = airlineMap.get(name);
          //  for (map : airlineMap) {

//            }
  //      }
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String key = getParameter( "name", request );
        if (key == null) {
            missingRequiredParameter( response, key );
            return;
        }
// do get parameter for all the other variables such as flight number and such
        String flightNum = getParameter( "flightNumber", request );
        if ( flightNum == null) {
            missingRequiredParameter( response, flightNum );
            return;
        }
        int flightNumber = 0;
        if (flightNum != null) {
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
        Airline airline;
        Flight flight;

        airline = new Airline(key); // getParameter. name of the airline
        flight = new Flight(flightNumber, src, departTime, dest, arriveTime);

        airline.addFlight(flight);
        this.airlineMap.put(key, airline);

        PrintWriter pw = response.getWriter();
       // pw.println(Messages.mappedKeyValue(key, airline));
        pw.println();
        prettyPrint(airline, response);
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    private void missingRequiredParameter( HttpServletResponse response, String key )
        throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    private void writeValue( String key, HttpServletResponse response ) throws IOException
    {
        Airline value = this.airlineMap.get(key);

        PrintWriter pw = response.getWriter();

        //pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        //pw.println(Messages.formatKeyValuePair( key, value.getName() ));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( airlineMap.size() ));

        for (Map.Entry<String, Airline> entry : this.airlineMap.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue().getFlights().toString()));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    private void prettyPrint(AbstractAirline airline, HttpServletResponse response) {
        try {
            Collection<AbstractFlight> flightList;
            flightList = airline.getFlights();

            PrintWriter writer = response.getWriter();

            if (flightList == null)
                throw new AssertionError("Empty Airline List");

            writer.write("Airline: " + airline.toString());
            writer.write("\n");

            // 432 Portland OR, Sat Dec 27, 2010 1:22 PST LAX etc
            for (AbstractFlight flight : flightList) {
                writer.write("Flight " + flight.getNumber());
                writer.write(" Departs " + ((Flight) flight).getSrcCode());
                writer.write(" at " + flight.getDepartureString());
                writer.write(" Arrives " + ((Flight) flight).getDestCode());
                writer.write(" at " + flight.getArrival() + " Duration " + ((Flight) flight).getDuration() + "\n");
            }

            writer.flush();


        } catch (IOException ex) {
            System.err.println("File Write Error");

        }
    }

}
