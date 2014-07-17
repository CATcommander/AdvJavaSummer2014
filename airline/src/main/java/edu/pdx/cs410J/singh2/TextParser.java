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
    http://www.mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/
    * */
  //  private static final String DATE_FORMAT = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\\\d\\\\d)";
  //  private static final String TIME_FORMAT = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
  //  private static final String SRC_FORMAT = "[A-Z][a-z]{3}";

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


            if (!inFile.exists())
                throw new ParserException("File Read Error: Empty File");

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

                System.out.println("src " + src);
                System.out.println(departDate);
                System.out.println(departTime);
                System.out.println(dest);
                System.out.println(arriveDate);
                System.out.println(arriveTime);

                try {
                    flightNumber = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    throw new ParserException("File Read Error: Invalid Flight Number");
                }

                for (char c: src.toCharArray()) {
                    if (src.length() != 3 || Character.isDigit(c)) {
                        throw new ParserException("File Read Error: Invalid Three-letter source code");
                    }
                }


                checkDate(departDate);
                checkTime(departTime);

                for (char c: dest.toCharArray()) {
                    if (dest.length() != 3 || Character.isDigit(c)) {
                        throw new ParserException("File Read Error: Invalid Three-letter destination code");
                    }
                }

                checkDate(arriveDate);
                checkTime(arriveTime);

                flight = new Flight(flightNumber, src, departDate + " " + departTime, dest, arriveDate + " " + arriveTime);

                airline.addFlight(flight);
            }
            bufferedReader.close();

        } catch (IOException ex) {
            System.err.println("File Read Error1");
        }// catch (ParserException ex) {
        ////    throw new ParserException("File Read Error2");
        //  }

        return airline;
    }

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

    private static String checkDate(String args) throws ParserException{
        String date[] = args.split("/");
        int month, day, year;

        try {

            month = Integer.parseInt(date[0]);
            day = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);

        } catch (NumberFormatException ex) {
            throw new AssertionError("File Read Error: Invalid Time");
        }

        if (month < 1 || month > 12) {
            throw new ParserException("File Read Error: Invalid Month");
        }

        if (day < 1 || day > 31) {
            throw new ParserException("File Read Error: Invalid Day");
        }

        /* year is 9999 assuming humanity or earth survives that long */
        if (year < 1800 || year > 9999) {
            throw new ParserException("File Read Error: Invalid Year");
        }

        // if the date is valid, then return the original valid date */
        return date[0] + "/" + date[1] + "/" + date[2];
    }

}
