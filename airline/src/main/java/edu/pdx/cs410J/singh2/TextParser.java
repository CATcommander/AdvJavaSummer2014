package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;

/**
 reads the contents of a text file and from it creates an airline
 with its associated flights
 */
public class TextParser implements AirlineParser {

    String fileName;

    /* source: http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-with-regular-expression/
    * */
    private static final String DATE_FORMAT = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\\\d\\\\d)";
    private static final String TIME_FORMAT = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private static final String SRC_FORMAT = "[A-Z][a-z]{3}";

    public TextParser(String fileName) {
        this.fileName = fileName;
        // abstractAirline = new Airline(fileName);
    }

    public AbstractAirline parse() throws ParserException {

        File inFile;
        FileReader fileReader;
        BufferedReader bufferedReader;
        Airline airline = null;
        Flight flight = null;
        String str;


        /* checks if the file exists
        *  if it exists, then check the airline, if airline matches
        *  read airline and flights and add it to local flight list
        *  if airline doesn't match throw a user-friendly error */
        try {

            inFile = new File(fileName);
            fileReader = new FileReader(inFile);
            bufferedReader = new BufferedReader(fileReader);

            str = bufferedReader.readLine();
            airline = new Airline(str);

            int flightNumber;
            String src, dest;
            String departDate, departTime;
            String arriveDate, arriveTime;

            while((str = bufferedReader.readLine()) != null) {
                // read flight information
                // store into listOfFlights
                String []args;
                args = str.split(" ");

                src = args[1];
                departDate = args[2];
                departTime = args[3];
                dest = args[4];
                arriveDate = args[5];
                arriveTime = args[6];

                try {
                    flightNumber = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    throw new ParserException("Invalid Flight number");
                }

                if (!src.matches(SRC_FORMAT)) {
                    throw new ParserException("Invalid Three-letter source code");
                }

                if (!departDate.matches(DATE_FORMAT)) {
                    throw new ParserException("Invalid Date format");
                }

                if (!departTime.matches(TIME_FORMAT)) {
                    throw new ParserException("Invalid Time format");
                }

                if (!dest.matches(SRC_FORMAT)) {
                    throw new ParserException("Invalid Three-letter destination code");
                }

                if (!arriveDate.matches(DATE_FORMAT)) {
                    throw new ParserException("Invalid Date format");
                }

                if (!arriveTime.matches(TIME_FORMAT)) {
                    throw new ParserException("Invalid Time format");
                }

                flight = new Flight(flightNumber, src, departDate + " " + departTime, dest, arriveDate + " " + arriveTime);

                airline.addFlight(flight);
            }
            bufferedReader.close();

        } catch (IOException ex) {
            System.err.println("File Read Error");
        } catch (ParserException ex) {
            throw new ParserException("File Read Error");
        }

        return airline;
    }

}
