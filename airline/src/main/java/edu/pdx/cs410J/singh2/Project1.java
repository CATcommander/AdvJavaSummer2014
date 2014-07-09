package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;

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
    private static final String INVALID_CODE = "Invalid Three-letter Code";
    private static final String INVALID_DATE = "Invalid Date";
    private static final String INVALID_TIME = "Invalid Time";

    private static void printREADME() {
        System.out.println("\n in function printREADME");
    }

    private static void printFlag(String []args) {
        int flightNumber;
        String name = args[1];
        String src, dest;
        String departDate, departTime;
        String arriveDate, arriveTime;

        Airline airline = new Airline(name);
        flightNumber = validateFlightNumber(args[2]);
        src = validateThreeLetterCode(args[3]);
        departDate = validateDate(args[4]);
        departTime = validateTime(args[5]);
        dest = validateThreeLetterCode(args[6]);
        arriveDate = validateDate(args[7]);
        arriveTime = validateTime(args[8]);

        Flight flight = new Flight(flightNumber, src, departDate + " " + departTime, dest, arriveDate + " " + arriveTime);
        airline.addFlight(flight);

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
     *         return of valid date
     */

    private static String validateDate(String args) {

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

        if (year < 1800 || year > 3000) {
            printUsageAndExit(INVALID_DATE);
            throw new AssertionError("Unreachable statement");
        }

        return month + "/" + day + "/" + year;
    }

    /**
     * Validates the time format. if <code>args</code> is not
     * valid time, program exits
     * @param args
     *        the time in 24-hour format (string to parse)
     * @return
     *        validated time
     */
    private static String validateTime(String args) {
        String time[] = args.split(":");
        int hour, minute;

        try {

            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);

        } catch (NumberFormatException ex) {
            printUsageAndExit(INVALID_TIME);
            throw new AssertionError("Unreachable statement");
        }


        if (hour > 24 || hour < 0) {
            printUsageAndExit(INVALID_TIME);
            throw new AssertionError("Unreachable statement");
        }

        if (minute > 60 || minute < 0) {
            printUsageAndExit(INVALID_TIME);
            throw new AssertionError("Unreachable statement");
        }

        return hour + ":" + minute;
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

    private static String validateThreeLetterCode(String arg) {
        if (arg.length() != 3) {
            printUsageAndExit(INVALID_CODE);
            throw new AssertionError("Unreachable statement");
        }

        String code = arg.toUpperCase();
        code.toString();

        return code;
    }
    public static void main(String[] args) {
        if (args.length < 8)
            printUsageAndExit("Not enough command line arguments");

        for (String s: args) {
            if (s.contains("-README")){
                printREADME();
            }
            else if (s.contains("-print")) {
                printFlag(args);
            }
            else if(!s.contains("-README") && !s.contains("-print")){
                //validateDate(args[3]);
                break;
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