package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.Collection;

/**
 * PrettyPrinter class that implements the AirlineDumper interface
 * creates a nicely-formatted textual presentation of an airline's
 * flights. It uses DateFormat to make the dates look nice. Takes
 * advantage of AirportNames class. Also, pretty printer includes
 * the duration of each flight in minutes
 */
public class PrettyPrinter implements AirlineDumper {
    private String fileName;

    /**
     * name of the file
     * @param fileName
     * name of the file
     */
    public PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * dumps the airline object to text file
     * @param airline
     * airline object
     * @throws IOException
     * throw IOException
     */
    public void dump(AbstractAirline airline) throws IOException {
        try {
            Collection<AbstractFlight> flightList;
            flightList = airline.getFlights();

            Writer writer;

            if (flightList == null)
                throw new AssertionError("Empty Airline List");

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF-8"));

            writer.write(airline.toString());
            writer.write("\n");

            // print out the headers
            writer.write("Flight #\tDeparture\t\t\tDeparture Date & Time\t\t\tDestination\t\t\tArrival Date & Time\t\tDuration\n");
            writer.write("--------\t---------\t\t\t---------------------\t\t\t-----------\t\t\t-------------------\t\t--------\n");

            // 432 Portland OR, Sat Dec 27, 2010 1:22 PST LAX etc
            for (AbstractFlight flight: flightList) {
                writer.write(flight.getNumber() + "\t\t\t");
                writer.write(((Flight) flight).getSrcCode() + "\t");
                writer.write(flight.getDepartureString() + "\t\t") ;
                writer.write(((Flight) flight).getDestCode() + "\t");
                writer.write(flight.getArrival() + "\t" + ((Flight) flight).getDuration() + "\n");
            }

            writer.flush();
            writer.close();

        } catch(IOException ex) {
            System.err.println("File Write Error");
        }
    }
}