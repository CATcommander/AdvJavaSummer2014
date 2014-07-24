package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
            if (str.contains(":") || str.contains("/") || str.matches("\\p{Punct}"))
                throw new ParserException("Invalid Format");

            airline = new Airline(str);

            int flightNumber;
            String src, dest;
            String departDate, departTime, departDay;
            String arriveDate, arriveTime, arrivalDay;

            while((str = bufferedReader.readLine()) != null) {
                // read flight information
                // store into listOfFlights
                String []args;
                args = str.split(" ");

                if (args.length > 9 || args.length < 9)
                    throw new ParserException("File Read Error: Invalid command line Argument");

                src = args[1];
                departDate = args[2];
                departTime = args[3];
                departDay = args[4];
                dest = args[5];
                arriveDate = args[6];
                arriveTime = args[7];
                arrivalDay = args[8];

                try {
                    flightNumber = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    throw new ParserException("File Read Error: Invalid Flight Number");
                }

                // make a simpledateformat to check the format here, if valid pass to functions
             /*   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY hh:mm a");
                try {
                    Date date = simpleDateFormat.parse(args[2] + " " + args[3] + " " + args[4]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/
                checkCode(src);
                checkDate(departDate);
                checkTime(departTime);
                checkDay(departDay);

                checkCode(dest);
                checkDate(arriveDate);
                checkTime(arriveTime);
                checkDay(arrivalDay);

                String depart = departDate + " " + departTime + " " + departDay;
                String arrive = arriveDate + " " + arriveTime + " " + arrivalDay;

                flight = new Flight(flightNumber, src, depart, departDay, dest, arrive, arrivalDay);

                airline.addFlight(flight);
            }

            bufferedReader.close();

        } catch (ParserException ex) {
            if (ex.getMessage().contains("File Is Empty"))
                throw new ParserException("File Is Empty");
            else if (ex.getMessage().contains("File is malformatted"))
                throw new ParserException("File is malformatted");
            else if (ex.getMessage().contains("File Read Error: Invalid Flight Number"))
                throw new ParserException("File Read Error: Invalid Flight Number");
            else if (ex.getMessage().contains("File Read Error: Invalid Three-Letter source code"))
                throw new ParserException("File Read Error: Invalid Three-Letter source code");
            else if (ex.getMessage().contains("File Read Error: Invalid Three-Letter destination code"))
                throw new ParserException("File Read Error: Invalid Three-Letter destination code");
            else if (ex.getMessage().contains("File Read Error: Invalid Three-Letter code"))
                throw new ParserException("File Read Error: Invalid Three-Letter code");
            else if (ex.getMessage().contains("Invalid Format"))
                throw new ParserException("Invalid Format");
            else if (ex.getMessage().contains("File Read Error: Invalid Time"))
                throw new ParserException("File Read Error: Invalid Time");
            else if (ex.getMessage().contains("File Read Error: Invalid Hour"))
                throw new ParserException("File Read Error: Invalid Hour");
            else if (ex.getMessage().contains("File Read Error: Invalid Minute"))
                throw new ParserException("File Read Error: Invalid Minute");
            else if (ex.getMessage().contains("File Read Error: Invalid Date"))
                throw new ParserException("File Read Error: Invalid Date");
            else if (ex.getMessage().contains("File Read Error: Invalid Month"))
                throw new ParserException("File Read Error: Invalid Month");
            else if (ex.getMessage().contains("File Read Error: Invalid Day"))
                throw new ParserException("File Read Error: Invalid Day");
            else if (ex.getMessage().contains("File Read Error: Invalid am/pm"))
                throw new ParserException("File Read Error: Invalid am/pm");
            else if (ex.getMessage().contains("File Read Error: Invalid Year"))
                throw new ParserException("File Read Error: Invalid Year");
            else if (ex.getMessage().contains("File Read Error: Invalid command line Argument"))
                throw new ParserException("File Read Error: Invalid command line Argument");
            else if (ex.getMessage().contains("File Does Not Exist"))
                throw new ParserException("File Does Not Exist");
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

        for (char c: args.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                throw new ParserException("File Read Error: Invalid Time");
            }
        }

        int hour, minute;
        String []time = args.split(":");

        try {

            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);

        } catch (NumberFormatException ex) {
            throw new ParserException("File Read Error: Invalid Time");
        }
        if (hour > 12 || hour < 1) {
            throw new ParserException("File Read Error: Invalid Hour");
        }

        if (minute > 59 || minute < 0) {
            throw new ParserException("File Read Error: Invalid Minute");
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

        for (char c: args.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                throw new ParserException("File Read Error: Invalid Date");
            }
        }

        String date[] = args.split("/");
        int month, day, year;

        try {

            month = Integer.parseInt(date[0]);
            day = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);

        } catch (NumberFormatException ex) {
            throw new ParserException("File Read Error: Invalid Date");
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

    private static String checkDay(String args) throws ParserException {
        for (char c: args.toCharArray()) {
            if (args.length() != 2 || Character.isDigit(c)) {
                throw new ParserException("File Read Error: Invalid am/pm");
            }
        }
        return args.toUpperCase();
    }

    private static String checkCode(String arg) throws ParserException {
        for (char c: arg.toCharArray()) {
            if (arg.length() != 3 || Character.isDigit(c)) {
                throw new ParserException("File Read Error: Invalid Three Letter destination code");
            }
        }
        String validCode = AirportNames.getName(arg.toUpperCase());

        if (validCode == null)
            throw new ParserException("File Read Error: Invalid Three Letter destination code");
        return arg;
    }

}
