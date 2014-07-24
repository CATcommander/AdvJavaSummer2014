package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * The main class for the CS410J airline Project
 */
public class Project3 {

    public static final String USAGE="usage: java edu.pdx.cs410J.singh2.Project3 [options] <args>" +
            "\nargs are (in this order):" +
            "\nname             The name of the airline" +
            "\nflightNumber     The flight number" +
            "\nsrc              Three-letter code of departure airport" +
            "\ndepartTime       Departure date and time (12-hour time)" +
            "\ndest             Three-letter code of arrival airport" +
            "\narriveTime       Arrival date and time (12-hour time)" +
            "\noptions are (options may appear in any order):" +
            "\npretty file      Pretty print the airline's flights to a " +
            "\n                 to a text file or standard out (file -)" +
            "\n-textFile file   Where to read/write the airline info" +
            "\n-print           Prints a description of the new flight" +
            "\n-README          Prints a README for this project and exits";

    public static final String INVALID_FN = "ERROR: Invalid Flight Number";
    public static final String INVALID_CODE = "ERROR: Invalid Three-letter Code";
    public static final String INVALID_DATE = "ERROR: Invalid Date Format. Must be in mm/dd/yyyy";
    public static final String INVALID_TIME = "ERROR: Invalid Time Format. Must be in 12-hour time";
    public static final String INVALID_DAY = "ERROR: Invalid Day Format. Must be am/pm";

    private static void handleREADME() {
        System.out.println("\nAuthor\n-------\nHarmanpreet Singh\nProject1\nCS410J\n7/9/2014\n");
        System.out.println("What is it?\n-----------");
        System.out.println("A simple basic program that keeps track of Flights. Flight can be created" +
                "\nand added to Airline database. An Airline has a name that consists of" +
                "\nmultiples flights. Each Flight departs from a source and leaves at a" +
                "\ngiven departure time and arrives at the destination at given arrival time.");

        System.out.println("\nHow to run?\n----------\nBefore running the application, read the USAGE section below. " +
                "To run this program \nenter the following on a terminal or command prompt:");
        System.out.println("\tjava edu.pdx.cs410J.singh2.Project1 [options] <args>\n" +
                "\tjava edu.pdx.cs410J.singh2.Project1 -print \"Air Express\" 432 PDX 01/12/2014 03:34 LAX 01/12/2014 05:40");

        System.out.println("\nUSAGE\n-------\nNote: Arguments must be in order. Options order does not matter and they are optional.\n\n" + USAGE);

        System.exit(0);
    }

    /**
     *  Validates the Date, time and am/pm marker
     * @param args
     *        date and time string
     * @return
     *        validated date and time
     * @throws ParserException
     *         Throws ParserException on Invalid Date or Time
     */
    private static String validateDateAndTime(String args) throws ParserException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        try {
            sdf.parse(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 11/12/2014 12:00 pm
        String dateAndTime[] = args.split(" ");
        String date, time, marker;

        date = validateDate(dateAndTime[0]);
        time = validateTime(dateAndTime[1]);
        marker = validateDay(dateAndTime[2]);

        return date + " " + time + " " + marker;
    }

    /**
     * This function validates if flightNumber is valid integer. If flight number is not a valid integer,
     * the program exits with a user-friendly error message.
     *
     * @param arg
     *        string to parse
     * @return flightNumber
     *         parsed value of integer
     */

    private static int validateFlightNumber(String arg) {
        int flightNumber;

        try {

            flightNumber = Integer.parseInt(arg);

        } catch (NumberFormatException ex) {
            printUsageAndExit(INVALID_FN);
            throw new AssertionError("Unreachable statement");
        }

        if (flightNumber < 0) {
            printUsageAndExit(INVALID_FN);
            throw new AssertionError("Unreachable statement");
        }

        return flightNumber;
    }

    /**
     * Validate the date so the date format is mm/dd/yyyy
     * if <code>args</code> is invalid, the program exits
     * @param args
     *        the date of departure/arrival
     * @return date
     *         returns the valid date in correct format (mm/dd/yyyy)
     */

    private static String validateDate(String args) {

        for (char c: args.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                printUsageAndExit(INVALID_DATE);
                throw new AssertionError("Unreachable statement");
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        simpleDateFormat.setLenient(false);


        try {
            simpleDateFormat.parse(args);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String date[] = args.split("/");
        int month, day, year;

        try {

            month = Integer.parseInt(date[0]);
            day = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);

        } catch (NumberFormatException ex) {
            printUsageAndExit(INVALID_DATE);
            throw new AssertionError("Unreachable statement");
        }

        if (month < 1 || month > 12) {
            printUsageAndExit(INVALID_DATE);
            throw new AssertionError("Unreachable statement");
        }

        if (day < 1 || day > 31) {
            printUsageAndExit(INVALID_DATE);
            throw new AssertionError("Unreachable statement");
        }

        /* year is 9999 assuming humanity or earth survives that long */
        if (year < 1800 || year > 9999) {
            printUsageAndExit(INVALID_DATE);
            throw new AssertionError("Unreachable statement");
        }

        /* date[0] = month
           date[1] = day
           date[2] = year

           if the date is valid, then return the original valid date */
        return date[0] + "/" + date[1] + "/" + date[2];
    }

    /**
     * Validates the time format. if <code>args</code> is not
     * valid time, program exits
     * @param args
     *        the time in 24-hour format (string to parse)
     * @return
     *        returns the validated time in correct format (hh:mm)
     */
    private static String validateTime(String args) {

        for (char c: args.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                throw new AssertionError(INVALID_TIME);
            }
        }

        String time[] = args.split(":");
        int hour, minute;

        try {

            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);

        } catch (NumberFormatException ex) {
            printUsageAndExit(INVALID_TIME);
            throw new AssertionError("Unreachable statement");
        }

        /* check if time is in 24-hour format */
        if (hour > 12 || hour < 1) {
            printUsageAndExit(INVALID_TIME);
            throw new AssertionError("Unreachable statement");
        }

        if (minute > 59 || minute < 0) {
            printUsageAndExit(INVALID_TIME);
            throw new AssertionError("Unreachable statement");
        }

        return time[0] + ":" + time[1];
    }


    private static String validateDay(String arg) {
        for (char c: arg.toCharArray()) {
            if (arg.length() != 2 || Character.isDigit(c)) {
                printUsageAndExit(INVALID_DAY);
                throw new AssertionError("Unreachable statement");
            }
        }

        return arg.toUpperCase();
    }

    /**
     * validates three letter code is characters only. If it contains
     * anything except characters, the program will exit
     * @param arg
     *        the string to parse with three letter code
     * @return
     *        validated parsed three letter code in upper case
     */
    private static String validateThreeLetterCode(String arg) {

        for (char c: arg.toCharArray()) {
            if (arg.length() != 3 || Character.isDigit(c)) {
                printUsageAndExit(INVALID_CODE);
                throw new AssertionError("Unreachable statement");
            }
        }

        String validCode = AirportNames.getName(arg.toUpperCase());

        if (validCode == null)
            printUsageAndExit("ERROR: Airport code \'" +  arg + "\' does not exist");

        return arg;
    }

    /**
     * Print the error messaged parsed in and exits with system error code 1
     * @param errorMessage
     *        the error message to print
     */
    private static void printUsageAndExit(String errorMessage) {

        System.err.println(errorMessage);
        System.err.println();
        System.err.println(USAGE);

        System.exit(1);
    }

    private static void checkErrosInCommandLine(boolean hasNoFlag, boolean hasPrintFlag, boolean prettyFlag, boolean textFileFlag, String... args) {

        /* if no flag detected and argument length > 10, throw an error */
        if (hasNoFlag && args.length > 10) {
            printUsageAndExit("Unknown command line argument");
        }

        /* only print flag is given */
        if (hasPrintFlag && !textFileFlag && !prettyFlag && (args.length > 11 || args.length < 11)) {
            printUsageAndExit("Unknown command line argument");
        }

        /* only textFile is given */
        if (textFileFlag && !hasPrintFlag && !prettyFlag && (args.length > 12 || args.length < 12))
            printUsageAndExit("Unknown command line argument");

        /* only pretty flag is given */
        if (prettyFlag && !textFileFlag && !hasPrintFlag && (args.length > 12 || args.length < 12)) {
            printUsageAndExit("Unknown command line argument");
        }

        /* only textFile and print flags are given */
        if (textFileFlag && hasPrintFlag && !prettyFlag && (args.length > 13 || args.length < 13)) {
            printUsageAndExit("Unknown command line argument");
        }

        /* only print and pretty flags are given */
        if (prettyFlag && hasPrintFlag && !textFileFlag && (args.length > 13 || args.length < 13)) {
            printUsageAndExit("Unknown command line argument");
        }

        /* only textFile and pretty flags are given */
        if (textFileFlag && prettyFlag && !hasPrintFlag && (args.length > 14 || args.length < 14)) {
            printUsageAndExit("Unknown command line argument");
        }
    }

    /**
     * Main program that parses the command line, creates a new
     * <code>Airline</code> and <code>Flight</code>. Depending on
     * the given options, the program will call appropriate function
     * and throws a user friendly message if invalid option.
     * @param args
     *        command line arguments
     */
    public static void main(String[] args) throws IOException, ParserException {
        boolean hasREADMEFlag = false;
        boolean hasPrintFlag = false;
        boolean hasNoFlag = false;
        boolean textFileFlag = false;
        boolean prettyFlag = false;

        String fileName;

        int flightNumber;

        String src, dest;
        String departure, arrival;
        String airlineName;
        TextParser textParser;
        TextDumper textDumper;
        File file;
        AbstractAirline airline = null;
        PrettyPrinter prettyPrinter;

        int counter = 0;
        int fileLocation = 0;
        int prettyFile = 0;

        int i = 0;

        /* if command line argument contains only README */
        for (String str : args) {
            if (str.compareToIgnoreCase("-README") == 0) {
                handleREADME();
            }
        }

        if (args.length < 10 || args.length > 14)
            printUsageAndExit("Not enough or too many command line arguments");

        /* loop through the args to check if options exist(options can be in any order) */
        for (String s : args) {
            counter++;
            if (s.compareToIgnoreCase("-README") == 0) {
                hasREADMEFlag = true;
            }
            else if (s.compareToIgnoreCase("-print") == 0) {
                hasPrintFlag = true;
            }
            else if (s.compareToIgnoreCase("-textFile") == 0) {
                textFileFlag = true;
                fileLocation = counter;
            }
            else if (s.compareToIgnoreCase("-pretty") == 0) {
                prettyFlag = true;
                prettyFile = counter;
            }

            /* if invalid flag is given, error out */
            if (s.compareToIgnoreCase("-README") != 0 && s.compareToIgnoreCase("-print") != 0 &&
                    s.compareToIgnoreCase("-textFile") != 0 && s.compareToIgnoreCase("-pretty") != 0 &&
                    s.compareToIgnoreCase("-") != 0 && s.matches("-.*")) {
                printUsageAndExit("Invalid Option");
            }
        }

        /* check if no flag is given*/
        if (!hasPrintFlag && !textFileFlag && !hasREADMEFlag && !prettyFlag) {
            hasNoFlag = true;
        }

        /* First of All, if README flag is detected, print A README for this project and exit */
        if (hasREADMEFlag) {
            handleREADME();
        }

        // ERROR CHECKING
        checkErrosInCommandLine(hasNoFlag, hasPrintFlag, prettyFlag, textFileFlag, args);

        if (textFileFlag && prettyFlag) {
            if (args[fileLocation].compareToIgnoreCase(args[prettyFile]) == 0)
                printUsageAndExit("ERROR: textFile and pretty cannot use the SAME file. \nPlease use a different file for pretty print");
        }

        /* if textFile is given then check if print flag is given as well*/
        if (textFileFlag && hasPrintFlag) {
            if (args[fileLocation].compareToIgnoreCase("-print") == 0)
                printUsageAndExit("Invalid File or Missing File");
        }

        if (textFileFlag) {
            /* after validating command line arguments, extract the file name */
            fileName = args[fileLocation];

            // check if the file ends with text
            if (!fileName.endsWith(".txt"))
                fileName = fileName + ".txt";

            if (hasPrintFlag)
                i = 3;
            else
                i = 2;

            // if file does not exist, parse will throw file not found
            // exception. Make a new file and create an empty airline
            try {
                textParser = new TextParser(fileName);
                airline = textParser.parse();
            } catch (ParserException e) {
                if (e.getMessage().contains("File Is Empty")) {
                    throw new ParserException("File Is Empty");
                }
                if (e.getMessage().contains("File Not Found") || e.getMessage().contains("File Does Not Exist")) {
                    file = new File(fileName);

                    try {
                        file.createNewFile();
                    } catch (IOException e1){
                        throw new ParserException("File Write Error");
                    }
                }
            }
        }

        /* if -print flag is detected, call printFlag to print the description of the project */
        if (hasPrintFlag && !textFileFlag && !prettyFlag) {
            i = 1;
        }

        if (hasNoFlag)
            i = 0;
        if (prettyFlag && !textFileFlag && !hasPrintFlag)
            i = 2;
        if (prettyFlag && hasPrintFlag)
            i = 3;
        if (prettyFlag && textFileFlag)
            i = 4;
        if (prettyFlag && hasPrintFlag && textFileFlag)
            i = 5;

        airlineName = args[i];
        // if parsed airline is empty, make a new airline
        if (airline == null) {
            airline = new Airline(airlineName);
        }

        flightNumber = validateFlightNumber(args[i + 1]);

        src = validateThreeLetterCode(args[i + 2]);
        departure = validateDateAndTime(args[i + 3] + " " + args[i + 4] + " " + args[i + 5]);

        dest = validateThreeLetterCode(args[i + 6]);
        arrival = validateDateAndTime(args[i + 7] + " " + args[i + 8] + " " + args[i + 9]);

        Flight flight = new Flight(flightNumber, src, departure, dest, arrival);

        airline.addFlight(flight);

        if (hasPrintFlag)
            System.out.println(flight.toString());

        if (textFileFlag) {
            fileName = args[fileLocation];
            if (!fileName.endsWith(".txt"))
                fileName = fileName + ".txt";

            if (airline.getName().compareToIgnoreCase(airlineName) == 0) {
                textDumper = new TextDumper(fileName);
                textDumper.dump(airline);
            } else if (airline.getName().compareToIgnoreCase(airlineName) != 0) {
                throw new ParserException("Airline does not match");
            }
        }

        if (prettyFlag && args[prettyFile].contains("-")) {
            // print out the headers
            System.out.println("Flight #\tSource\t\t\tDeparture Date & Time\t\t\tDestination\t\t\tArrival Date & Time\t\t\tDuration");
            System.out.println("--------\t------\t\t\t---------------------\t\t\t-----------\t\t\t-------------------\t\t\t--------\n");

            Collection<Flight> flightList;
            flightList = airline.getFlights();

            // convert time duration into minutes
            for (AbstractFlight abstractFlight: flightList) {
                System.out.print(abstractFlight.getNumber() + "\t\t\t");
                System.out.print(((Flight) abstractFlight).getSrcCode() + "\t");
                System.out.print(abstractFlight.getDeparture() + "\t");
                System.out.print(((Flight) abstractFlight).getDestCode() + "\t");
                System.out.print("\t" + abstractFlight.getArrival() + "\t" + ((Flight) abstractFlight).getDuration() + "\n");
            }

            System.out.println(String.format("%-7s= %sflgiht" , "Flight", "blah" ));

        }

        if (prettyFlag && !textFileFlag && !hasPrintFlag && !args[prettyFile].contains("-")) {
            fileName = args[prettyFile];
            if (!fileName.endsWith(".txt"))
                fileName = fileName + ".txt";

            file = new File(fileName);

            try {
                file.createNewFile();
            } catch (IOException e1){
                throw new ParserException("File Write Error");
            }

            prettyPrinter = new PrettyPrinter(fileName);
            prettyPrinter.dump(airline);
        }

        System.exit(0);
    }

}