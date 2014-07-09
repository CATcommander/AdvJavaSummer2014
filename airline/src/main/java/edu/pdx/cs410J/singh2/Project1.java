package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

    private static final String USAGE="usage: java edu.pdx.cs410J.singh2.Project1 [options] <args>" +
                        "\nargs are (in this order):" +
                        "\nname          The name of the airline" +
                        "\nflightNumber  The flight number" +
                        "\nsrc           Three-letter code of departure airport" +
                        "\ndepartTime    Departure date and time (24-hour time)" +
                        "\ndest          Three-letter code of arrival airport" +
                        "\narriveTime    Arrival date and time (24-hour time)" +
                        "\noptions are (options may appear in any order):" +
                        "\n-print        Prints a description of the new flight" +
                        "\n-README       Prints a README for this project and exits";

    private static final String INVALID_FN = "Invalid Flight Number";
    private static final String INVALID_SRC = "Invalid departure airport code";
    private static final String INVALID_DATE = "Invalid date";

    private static void printREADME() {
        System.out.println("\n in function printREADME");
    }

    private static void printFlag(String []args) {
        int flightNumber;

        flightNumber = validateFlightNumber(args[2]);
        Airline airline = new Airline(args[1]);

        Flight flight = new Flight(flightNumber, args[3], args[4], args[5], args[6]);
        airline.addFlight(flight);

//      System.out.println(airline.toString());
        System.out.println(flight.toString());
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
            throw new AssertionError("This statement is unreachable. It should never get here");
        }

        if (flightNumber < 0) {
            printUsageAndExit(INVALID_FN);
            throw new AssertionError("This statement is unreachable. It should never get here");
        }

        return flightNumber;
    }

    /**
     * Validate the date so the date format is mm/dd/yyyy hh:mmm
     * @param args
     *        the date and time of departure/arrival
     */

    private static void validateDate(String args) {

        String space[] = args.split(" ");

        //String date[] = space[0].split("/");
       // String time[] = space[1].split(" ");

        System.out.println(space[0]);// date[0]);// + time.toString());

        SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy hh:mm");



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

    public static void main(String[] args) {
        for (String s: args) {
            if (s.contains("-README")){
                printREADME();
            }
            else if (s.contains("-print")) {
                printFlag(args);
            }
            else if(!s.contains("-README") && !s.contains("-print")){
                validateDate(args[3]);

            }
        }

        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

/*        System.err.println("Missing command line arguments");

        for (String arg : args) {
            System.out.println(arg);
        }
        */
        System.exit(1);
    }



}