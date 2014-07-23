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

    public PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    public void dump(AbstractAirline airline) throws IOException {
        try {
            Collection<AbstractFlight> flightList;
            flightList = airline.getFlights();


            Writer writer;

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF-8"));

            writer.write("Airline: " + airline.getName());
            writer.write("\n");


            // convert time duration into minutes
            for (AbstractFlight flight: flightList) {
                writer.write("Flight Number: " + flight.getNumber() + "\n");
                writer.write("From: " + flight.getSource());
                writer.write(flight.getDepartureString() + " " + flight.getDeparture() + "\n");
                writer.write("To: " + flight.getDestination() + " ");
                writer.write(flight.getArrivalString() + " " + flight.getArrival());
                writer.write("\n");
            }

            writer.flush();
            writer.close();

        } catch(IOException ex) {
            System.err.println("File Write Error");
        }
    }
}
