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

                flightNumber = Project2.validateFlightNumber(args[0]);
                src = Project2.validateThreeLetterCode(args[1]);
                departDate = Project2.validateDate(args[2]);
                departTime = Project2.validateTime(args[3]);
                dest = Project2.validateThreeLetterCode(args[4]);
                arriveDate = Project2.validateDate(args[5]);
                arriveTime = Project2.validateTime(args[6]);

                System.out.println(flight.toString());
                flight = new Flight(flightNumber, src, departDate + " " + departTime, dest, arriveDate + " " + arriveTime);

                airline.addFlight(flight);
            }
            bufferedReader.close();

        } catch(IOException ex) {
            System.err.println("File Read Error");
        }

        return airline;
    }

}
