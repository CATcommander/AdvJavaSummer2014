package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments\n";

    public static final String USAGE="\nusage: java edu.pdx.cs410J.singh2.Project4 [options] <args>" +
            "\nargs are (in this order):" +
            "\nname             The name of the airline" +
            "\nflightNumber     The flight number" +
            "\nsrc              Three-letter code of departure airport" +
            "\ndepartTime       Departure date and time (12-hour time)" +
            "\ndest             Three-letter code of arrival airport" +
            "\narriveTime       Arrival date and time (12-hour time)" +
            "\noptions are (options may appear in any order):" +
            "\n-host hostname   Host computer on which the server runs" +
            "\n-port port       Port on which the server is listening" +
            "\n-search          Search for flights" +
            "\n-print           Prints a description of the new flight" +
            "\n-README          Prints a README for this project and exits";

    private static void handleREADME() {
        System.out.println("\nAuthor\n-------\nHarmanpreet Singh\nProject1\nCS410J\n7/29/2014\n");
        System.out.println("What is it?\n-----------");
        System.out.println("A simple basic program that keeps track of Flights. Flight can be created" +
                "\nand added to Airline database. An Airline has a name that consists of" +
                "\nmultiples flights. Each Flight departs from a source and leaves at a" +
                "\ngiven departure time and arrives at the destination at given arrival time.");

        System.out.println("\nHow to run?\n----------\nBefore running the application, read the USAGE section below. " +
                "To run this program \nenter the following on a terminal or command prompt:");
        System.out.println("\tjava edu.pdx.cs410J.singh2.Project4 [options] <args>\n" +
                "\tjava edu.pdx.cs410J.singh2.Project4 -host host -port 9909 -print \"Air Express\" 432 PDX 01/12/2014 03:34 am LAX 01/12/2014 05:40 am");

        System.out.println("\nUSAGE\n-------\nNote: Host and Port are required! Arguments must be in order. Options order does not matter and they are optional.\n\n" + USAGE);

        System.exit(0);
    }

    /**
     * Validates the Date, time and am/pm marker
     * @param args
     * date and time string
     * @return
     * validated date and time
     * @throws ParserException
     * Throws ParserException on Invalid Date or Time
     */
    private static String validateDateAndTime(String args) throws ParserException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        try {
            sdf.parse(args);
        } catch (ParseException e) {
            throw new ParserException("Error: Invalid Date Format");
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
     * string to parse
     * @return flightNumber
     * parsed value of integer
     */

    private static int validateFlightNumber(String arg) {
        int flightNumber;

        try {

            flightNumber = Integer.parseInt(arg);

        } catch (NumberFormatException ex) {
            error("Error: Invalid Flight Number");
            throw new AssertionError("Unreachable statement");
        }

        if (flightNumber < 0) {
            error("Error: Invalid Flight Number");
            throw new AssertionError("Unreachable statement");
        }

        return flightNumber;
    }

    /**
     * Validate the date so the date format is mm/dd/yyyy
     * if <code>args</code> is invalid, the program exits
     * @param args
     * the date of departure/arrival
     * @return date
     * returns the valid date in correct format (mm/dd/yyyy)
     */

    private static String validateDate(String args) {

        for (char c: args.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                error("Error: Invalid Date. Can not contain characters or symbols");
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
            error("Error: Invalid Date");
            throw new AssertionError("Unreachable statement");
        }

        if (month < 1 || month > 12) {
            error("Error: Invalid Month");
            throw new AssertionError("Unreachable statement");
        }

        if (day < 1 || day > 31) {
            error("Error: Invalid Day");
            throw new AssertionError("Unreachable statement");
        }

        /* year is 9999 assuming humanity or earth survives that long */
        if (year < 1800 || year > 9999) {
            error("Error: Invalid Year");
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
     * the time in 24-hour format (string to parse)
     * @return
     * returns the validated time in correct format (hh:mm)
     */
    private static String validateTime(String args) {

        for (char c: args.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                throw new AssertionError("Error: Invalid Time. Can not contain characters or symbols");
            }
        }

        String time[] = args.split(":");
        int hour, minute;

        try {

            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);

        } catch (NumberFormatException ex) {
            error("Error: Invalid Time");
            throw new AssertionError("Unreachable statement");
        }

        /* check if time is in 24-hour format */
        if (hour > 12 || hour < 1) {
            error("Error: Invalid Hour");
            throw new AssertionError("Unreachable statement");
        }

        if (minute > 59 || minute < 0) {
            error("Error: Invalid Minute(s)");
            throw new AssertionError("Unreachable statement");
        }

        return time[0] + ":" + time[1];
    }


    private static String validateDay(String arg) {
        for (char c: arg.toCharArray()) {
            if (arg.length() != 2 || Character.isDigit(c)) {
                error("Error: Invalid am/pm marker");
                throw new AssertionError("Unreachable statement");
            }
        }

        return arg.toUpperCase();
    }

    /**
     * validates three letter code is characters only. If it contains
     * anything except characters, the program will exit
     * @param arg
     * the string to parse with three letter code
     * @return
     * validated parsed three letter code in upper case
     */
    private static String validateThreeLetterCode(String arg) {

        for (char c: arg.toCharArray()) {
            if (arg.length() != 3 || Character.isDigit(c)) {
                error("Error: Invalid Three-Letter Code");
                throw new AssertionError("Unreachable statement");
            }
        }

        String validCode = AirportNames.getName(arg.toUpperCase());

        if (validCode == null)
            error("Error: Airport code \'" + arg + "\' does not exist");

        return arg;
    }



    private static void checkErrorsInCommandLine(boolean hasNoFlag, boolean hasPrintFlag, boolean host, boolean port, boolean search, String... args) {

        // if no flag detected and argument length > 10, throw an error
        if (hasNoFlag && args.length > 10)
            error("Error: Unknown command line argument" + USAGE);

        if (!host && !port)
            error("Error: Host and Port Missing" + MISSING_ARGS + USAGE);
        if (host && port && search && hasPrintFlag)
            error("Error: Unknown command line argument" + USAGE);
        // only print flag is given
        if (hasPrintFlag && !host && !port && !search && (args.length > 11 || args.length < 11))
            error("Error: Unknown command line argument" + USAGE);

        if (hasPrintFlag && !host && !port && (args.length > 11 || args.length < 11))
            error("Error: Host and Port Missing" + MISSING_ARGS + USAGE);

        if (host && !port)
            error("Error: Missing Port" + MISSING_ARGS + USAGE);
        if (!host && port)
            error("Error: Missing Host" + MISSING_ARGS + USAGE);

        // only host and no port
        if (host && !port && (args.length > 12 || args.length < 12))
            error("Error: Missing Host" + MISSING_ARGS + USAGE);

        // only port flag is given and no host
        if (port && !host && (args.length > 12 || args.length < 12))
            error("Error: Missing Port" + MISSING_ARGS + USAGE);

        // if host, port and search is given
        if (host && port && search && !hasPrintFlag && (args.length > 8 || args.length < 8))
            error("Error: Unknown command line argument" + USAGE);

        // if host, port, search and print is given
        if (host && port && search && hasPrintFlag && (args.length > 8 || args.length < 8))
            error("Error: Unknown command line argument" + USAGE);

        // only textFile and pretty flags are given
        if (host && port && !hasPrintFlag && !search && (args.length > 14 || args.length < 14))
            error("Error: Unknown command line argument" + USAGE);
    }

    public static void main(String... args) throws ParserException {
        String hostName = null;
        String portString = null;

        int i = 0;
        int host = 0;
        int portNumber = 0;
        int search = 0;
        int counter = 0;

        boolean hasREADMEFlag = false;
        boolean hasPrintFlag = false;
        boolean hasHostFlag = false;
        boolean hasPortFlag = false;
        boolean hasNoFlag = false;
        boolean hasSearchFlag = false;

        // if command line argument contains only README
        for (String str : args) {
            if (str.compareToIgnoreCase("-README") == 0) {
                handleREADME();
            }
        }

        if (args.length < 8 || args.length > 16)
            error("Not enough or too many command line arguments");

        // loop through the args to check if options exist(options can be in any order)
        for (String s : args) {
            counter++;
            if (s.compareToIgnoreCase("-README") == 0) {
                hasREADMEFlag = true;
            }
            else if (s.compareToIgnoreCase("-print") == 0) {
                hasPrintFlag = true;
            }
            else if (s.compareToIgnoreCase("-host") == 0) {
                host = counter;
                hasHostFlag = true;
            }
            else if (s.compareToIgnoreCase("-port") == 0) {
                portNumber = counter;
                hasPortFlag = true;
            }
            else if (s.compareToIgnoreCase("-search") == 0) {
                search = counter;
                hasSearchFlag = true;
            }
            // if invalid flag is given, error out
            if (s.compareToIgnoreCase("-README") != 0 && s.compareToIgnoreCase("-print") != 0 &&
                    s.compareToIgnoreCase("-host") != 0 && s.compareToIgnoreCase("-port") != 0 &&
                    s.compareToIgnoreCase("-search") != 0 && s.matches("-.*")) {
                error("Invalid Option" + "\n" + USAGE);
            }
        }

        // check if no flag is given
        if (!hasPrintFlag && !hasREADMEFlag && !hasHostFlag && !hasPortFlag && !hasSearchFlag) {
            hasNoFlag = true;
        }

        // First of All, if README flag is detected, print A README for this project and exit
        if (hasREADMEFlag) {
            handleREADME();
        }

        // ERROR CHECKING
        checkErrorsInCommandLine(hasNoFlag, hasPrintFlag, hasHostFlag, hasPortFlag, hasSearchFlag, args);

        // if both host and port are given
        if (hasHostFlag && hasPortFlag) {

            if(args[host].compareToIgnoreCase("-port") == 0 || args[portNumber].compareToIgnoreCase("-host") == 0) {
                error("Error: Unknown command line argument" + USAGE);
            }

            hostName = args[1];
            portString = args[3];
            i = 4;

            if (hasPrintFlag) {
                i = 5;
            }
        }

        if(!hasHostFlag && !hasPortFlag) {
            if (hasPrintFlag)
                i = 1;
        }

        int port;
        try {
            port = Integer.parseInt( portString );
        } catch (NumberFormatException ex) {
            error("Port \"" + portString + "\" must be an integer" + USAGE);
            return;
        }

        String name, src, departTime, dest, arriveTime;
        int flightNumber;

        AirlineRestClient client = new AirlineRestClient(hostName, port);
        HttpRequestHelper.Response response = null;

        if (!hasSearchFlag || hasPrintFlag) {
            name = args[i];
            flightNumber = validateFlightNumber(args[i + 1]);
            src = validateThreeLetterCode(args[i + 2]);
            departTime = validateDateAndTime(args[i + 3] + " " + args[i + 4] + " " + args[i + 5]);
            dest = validateThreeLetterCode(args[i + 6]);
            arriveTime = validateDateAndTime(args[i + 7] + " " + args[i + 8] + " " + args[i + 9]);

            String flightString = Integer.toString(flightNumber);

            try {
                response = client.addNewFlight(name, flightString, src, departTime, dest, arriveTime);
                checkResponseCode(HttpsURLConnection.HTTP_OK, response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (hasPrintFlag)
                System.out.println(response.getContent());
        }

        // if there is search flag
        if (hasSearchFlag) {
            if(args[search].compareToIgnoreCase("-port") == 0 || args[search].compareToIgnoreCase("-host") == 0) {
                error("Error: Unknown command line argument" + USAGE);
            }

            try {
                response = client.search(args[5], args[6], args[7]);
                System.out.println(response.getContent());
                checkResponseCode(HttpsURLConnection.HTTP_OK, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode(int code, HttpRequestHelper.Response response)
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    /**
     * Prints error message and exits with code 1
     * @param message
     *        error message to print
     */
    private static void error(String message)
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }
}