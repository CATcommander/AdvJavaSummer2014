package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;


/**
 reads the contents of a text file and from it creates an airline
 with its associated flights
 */
public class TextParser implements AirlineParser {

    public String fileName;

    /**
     * default constructor initializes fileName
     * @param fileName
     *        name of the file
     */
    public TextParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parses the file given, if invalid date is read, throw
     * an error
     * @return
     *        airline (including flights if any exist)
     * @throws ParserException
     *         throws ParserException if File Is Empty
     *         throws ParserException if File Does Not Exist
     *         throws ParserException if File is malformatted
     */
    public AbstractAirline parse() throws ParserException {

        File inFile;
        FileReader fileReader;
        BufferedReader bufferedReader;

        Airline airline = null;
        Flight flight;
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

            if (str == null)
                throw new ParserException("File Is Empty");

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
                    throw new ParserException("File Read Error: Invalid Flight Number");
                }

                for (char c: src.toCharArray()) {
                    if (src.length() != 3 || Character.isDigit(c)) {
                        throw new ParserException("File Read Error: Invalid Three-Letter source code");
                    }
                }
                src.toUpperCase();

                checkDate(departDate);
                checkTime(departTime);

                for (char c: dest.toCharArray()) {
                    if (dest.length() != 3 || Character.isDigit(c)) {
                        throw new ParserException("File Read Error: Invalid Three-Letter destination code");
                    }
                }
                dest.toUpperCase();

                checkDate(arriveDate);
                checkTime(arriveTime);

                flight = new Flight(flightNumber, src, departDate + " " + departTime, dest, arriveDate + " " + arriveTime);

                airline.addFlight(flight);
            }

            bufferedReader.close();

        } catch (ParserException ex) {
            if (ex.getMessage().contains("File Is Empty"))
                throw new ParserException("File Is Empty");
            else if (ex.getMessage().contains("File is malformatted"))
                throw new ParserException("File is malformatted");
        } catch (IOException e) {
            throw new ParserException("File Does Not Exist");
        }

        return airline;
    }

    /**
     * validates the time is valid and in correct format, otherwise
     * throw a ParserException
     * @param args
     *        flight time from file
     * @return
     *        valid time
     * @throws ParserException
     *         throw ParserException if Invalid Time Format
     *         throw ParserException if Invalid hour
     *         throw ParserException if Invalid minute
     */
    private static String checkTime(String args) throws ParserException{

        int hour, minute;
        String []time = args.split(":");

        try {

            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);

        } catch (NumberFormatException ex) {
            throw new ParserException("File Read Error: Invalid Time Format");
        }
        if (hour > 23 || hour < 00) {
            throw new ParserException("File Read Error: Invalid hour");
        }

        if (minute > 59 || minute < 00) {
            throw new ParserException("File Read Error: Invalid minute");
        }

        return hour + ":" + minute;
    }

    /**
     * validate if date is valid and in correct format
     * @param args
     *        flight date from file
     * @return
     *        valid date
     * @throws ParserException
     *         throw ParserException if Invalid Date
     *         throw ParserException if Invalid Month
     *         throw ParserException if Invalid Day
     *         throw ParserException if Invalid Year
     */
    private static String checkDate(String args) throws ParserException{
        String date[] = args.split("/");
        int month, day, year;

        try {

            month = Integer.parseInt(date[0]);
            day = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);

        } catch (NumberFormatException ex) {
            throw new AssertionError("File Read Error: Invalid Date");
        }

        if (month < 1 || month > 12) {
            throw new ParserException("File Read Error: Invalid Month");
        }

        if (day < 1 || day > 31) {
            throw new ParserException("File Read Error: Invalid Day");
        }

        /* year is 9999 assuming humanity or earth survives that long */
        if (year < 1000 || year > 9999) {
            throw new ParserException("File Read Error: Invalid Year");
        }

        // if the date is valid, then return the original valid date */
        return date[0] + "/" + date[1] + "/" + date[2];
    }

}
