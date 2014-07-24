package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;

import java.util.Collection;

/**
  takes the contents of an airline and writes it out to a text file
 */
public class TextDumper implements AirlineDumper {
    private String fileName;

    /**
     * name of the file is parse into constructor
     * @param fileName
     *        file name
     */
    public TextDumper(String fileName) {
        this.fileName = fileName;
    }

    /**
     * check if the file exists, if it does, add the listOfFlights to file
     * if it doesn't exist, create it automatically, copy the
     * flight list into text file
     * @param airline
     *        dumps the airline
     * @throws IOException
     *         throws an exception if text file malformed
     */
    public void dump(AbstractAirline airline) throws IOException{

        try {
            Collection<Flight> flightList;
            flightList = airline.getFlights();

            Writer writer;

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF-8"));

            writer.write(airline.getName());
            writer.write("\n");

            for (AbstractFlight flight: flightList) {
                writer.write(flight.getNumber() + " ");
                writer.write(((Flight) flight).getSource() + " ");
                writer.write(((Flight) flight).getDepartYearFourDigits() + " ");
                writer.write(((Flight) flight).getDestination() + " ");
                writer.write(((Flight) flight).getArrivalYearFourDigits());
                writer.write("\n");
            }

            writer.flush();
            writer.close();

        } catch(IOException ex) {
            System.err.println("File Write Error");
        }
    }
}
