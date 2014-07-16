package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.FileReader;
import java.text.ParseException;

/**
 reads the contents of a text file and from it creates an airline
 with its associated flights
 */
public class TextParser implements AirlineParser {
    public AbstractAirline parse() throws ParserException {

        FileReader reader = null;

        Airline airline = new Airline("name");
        Flight flight = new Flight(flightNumber, src, departDate + " " + departTime, dest, arriveDate + " " + arriveTime);
        airline.addFlight(flight);

        return flight;
    }
}
