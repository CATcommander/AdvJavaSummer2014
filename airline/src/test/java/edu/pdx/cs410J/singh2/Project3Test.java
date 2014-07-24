package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;

import org.junit.Test;

import java.io.*;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**

 */
public class Project3Test extends InvokeMainTestCase {
/*
    private String fileName = "file.txt";
    private TextParser parser = new TextParser(fileName);
    private TextDumper dumper = new TextDumper(fileName);
    private Airline airline;

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     *
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    private void createFile(String str) {
        Writer outFile;
        try {
            outFile = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF-8"));
            outFile.write(str);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile() {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     *
    //@Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains( "Not enough or too many command line arguments" ));
    }
/*
    @Test
    public void testMissingFile() {
        deleteFile();
        TextParser parser = new TextParser(fileName);
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false); //should not get here
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Does Not Exist");
        }
    }
*
 //   @Test
    public void testEmptyFile() {
        createFile("");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Is Empty");
        }
        deleteFile();
    }
/*
    @Test
    public void testFileAlreadyAssociated() {
        createFile("Foo Airlines");
        MainMethodResult result = invokeMain("-print", "-textFile", fileName, "Bar Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        deleteFile();
        assertTrue(result.getErr().contains("Filename is already associated with airline 'Foo Airlines'"));
        assertEquals(new Integer(1), result.getExitCode());
    }
*
    @Test
    public void testFlightNumber() throws IOException {
        createFile("Foo\n12d pdx 1/1/2012 12:11 am sea 01/1/2011 11:11 am");
        try {
            AbstractAirline use = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Flight Number");
        }
    }

    @Test
    public void wrongFormatFile() {
        createFile("Foo 10 pdx 1/1/2012 12:11 am sea 01/1/2011 11:11 am");
        try {
            AbstractAirline a = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "Invalid Format");
        }
    }

    @Test
    public void BadSourceCode() throws IOException {
        createFile("Foo\n101 c3d 01/12/2001 01:00 am SEA 01/01/2001 01:40 am");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Three-Letter source code");
        }
    }

    @Test
    public void badDestCode() throws IOException {
        createFile("Foo\n101 cod 01/12/2001 01:00 am S3A 01/01/2001 01:40 am");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Three-Letter destination code");
        }
    }

    @Test
    public void badDay() throws IOException {
        createFile("Foo\n10 pdx 01/39/2011 01:00 am SEA 01/01/2001 01:40 am");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Day");
        }
        deleteFile();
    }

    @Test
    public void badYear() throws IOException {
        createFile("Airlines\n101 PDX 1/01/20/1 1:00 am SEA 01/01/2001 01:40 am");
        try {
            AbstractAirline notUsed = parser.parse();
            Collection<Flight> flights = notUsed.getFlights();
            for (Flight flight : flights) {
                System.out.println(flight.toString());
            }
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Year");
        }

    }

    @Test
    public void badMonth() throws IOException {
        createFile("Airlines\n101 PDX 13/3/2001 1:00 am SEA 01/01/2001 01:40 am");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Month");
        }
        deleteFile();
    }
/*
    @Test
    public void extraDepartureArgument() throws IOException {
        createFile("Airlines\n101 PDX 1/01/2001 1:00 pm SEA 01/01/2/1 01:40");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Three-Letter destination code");
        }
        deleteFile();
    }
*
    @Test
    public void extraArrivalArgument() throws IOException {
        createFile("Airlines\n101 PDX 1/01/2001 1:00 SEA 01/01/2001 01:40 pm");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Extra Argument");
        }
        deleteFile();
    }

    @Test
    public void testReadWithBadTime() throws IOException {
        createFile("Airlines\n101 PDX 1/01/2001 33:00 SEA 01/01/2001 01:40");
        try {
            AbstractAirline notUsed = parser.parse();
            assertTrue(false);
        } catch (ParserException e) {
            assertEquals(e.getMessage(), "File Read Error: Invalid Hour");
        }
        deleteFile();
    }

    @Test
    public void testSuccessfulRead() throws ParserException, IOException {
        airline = new Airline("Punjabi Airlines");
        airline.addFlight(new Flight(101, "MPG", "1/1/2000 12:00", "am", "SPA", "1/1/2000 12:40", "pm"));

        dumper.dump(airline);
        AbstractAirline test = parser.parse();
       // deleteFile();
        assertEquals(test.getName(), "Punjabi Airlines");
    }

    @Test
    public void canInvokeReadme() {
        MainMethodResult result = invokeMain("-README");
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testValidArguments() {
        MainMethodResult result = invokeMain("Foo Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testValidArgumentsWithPrint() {
        MainMethodResult result = invokeMain("-print", "Foo Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        assertEquals(new Integer(0), result.getExitCode());
    }
/*
    @Test
    public void testTooManyArguments() {
        MainMethodResult result = invokeMain("-print", "Foo Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40", "extra");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Too many arguments"));
    }

    @Test
    public void canInvokeReadmeWithOtherArguments() {
        MainMethodResult result = invokeMain("-print", "Foo Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40", "-README");
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void CanPrintAndWriteValidArguments() {
        MainMethodResult result = invokeMain("-print", "-textFile", fileName, "Foo Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        assertEquals(new Integer(0), result.getExitCode());
        deleteFile();
    }

    @Test
    public void CanPrintAndWriteWithSwappedOptions() {
        MainMethodResult result = invokeMain("-textFile", fileName, "-print", "Foo Airlines", "101", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        assertEquals(new Integer(0), result.getExitCode());
        deleteFile();
    }

    @Test
    public void testBadFlightNumber() {
        MainMethodResult result = invokeMain("-textFile", fileName, "-print", "Foo Airlines", "101a", "PDX", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Flight number must be represented as an integer number"));
        deleteFile();
    }

    @Test
    public void testBadAirportCodeArgument() {
        MainMethodResult result = invokeMain("-textFile", fileName, "-print", "Foo Airlines", "101", "PDx", "1/1/2000", "12:00", "SEA", "1/1/2000", "12:40");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Airport codes must be in the format of Three (3) upper-case letters"));
        deleteFile();
    }

    @Test
    public void testMissingFileNameArgument() {
        MainMethodResult result = invokeMain("-textFile");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing file name after -textFile argument"));
        deleteFile();
    }

    @Test
    public void testMissingArgumentsWithTextFileOption() {
        MainMethodResult result = invokeMain("-textFile", fileName);
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing command line arguments."));
        deleteFile();
    }

    @Test
    public void testMissingArgumentsWithTextFileAndPrintOption() {
        deleteFile();
        MainMethodResult result = invokeMain("-textFile", fileName, "-print");
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing command line arguments."));
        deleteFile();
    }
*/
}

