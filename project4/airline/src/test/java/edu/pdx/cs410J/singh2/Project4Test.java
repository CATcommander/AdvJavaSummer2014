package edu.pdx.cs410J.singh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4Test extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = String.valueOf(8080);

    /*@Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project4.class);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Not enough or too many command line arguments"));
    }

    @Test
    public void testReadMe() {
        MainMethodResult result = invokeMain(Project4.class, "-README");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void testMissingHostNameArg() {
        MainMethodResult result = invokeMain(Project4.class, "-host");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Not enough or too many command line arguments"));
    }

    @Test
    public void testMissingHostNameArgWithOtherOption() {
        MainMethodResult result = invokeMain(Project4.class, "-host", "-port");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Not enough or too many command line arguments"));
    }

    @Test
    public void testMissingPortArg() {
        MainMethodResult result = invokeMain(Project4.class, "-port");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Not enough or too many command line arguments"));
    }

    @Test
    public void testMissingPortArgWithOtherOption() {
        MainMethodResult result = invokeMain(Project4.class, "-port", "-print");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Not enough or too many command line arguments"));
    }

    @Test
    public void testInvalidPortArg() {
        MainMethodResult result = invokeMain(Project4.class, "-host", "localhost", "-port", "abc", "name", "123", "pdx", "10/21/2013", "12:35", "pm", "sea", "10/21/2013", "1:00", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Port \"abc\" must be an integer"));
    }*/

/*
    @Test
    public void testSearchOptionWithZeroArgs() {
        MainMethodResult result = invokeMain(Project4.class, "-search");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("only name, src and dest must be supplied when -search is supplied"));
    }

    @Test
    public void testSearchOptionWithTwoArgs() {
        MainMethodResult result = invokeMain(Project4.class, "-search", "airline", "PDX");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("only name, src and dest must be supplied when -search is supplied"));
    }

    @Test
    public void testSearchOptionWithFourArgs() {
        MainMethodResult result = invokeMain(Project4.class, "-search", "airline", "PDX", "LAX", "foo");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("only name, src and dest must be supplied when -search is supplied"));
    }

    @Test
    public void testSearchOptionWithBadAirportCode1() {
        MainMethodResult result = invokeMain(Project4.class, "-search", "airline", "APV", "LAX");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Argument `APV': Airport code is not valid"));
    }

    @Test
    public void testSearchOptionWithBadAirportCode2() {
        MainMethodResult result = invokeMain(Project4.class, "-search", "airline", "PDX", "FOO");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Argument `FOO': Airport code is not valid"));
    }


    @Test
    public void testSearchOptionWithBadHost() {
        MainMethodResult result = invokeMain(Project4.class, "-host", "badHost", "-port", "8080", "-search", "airline", "PDX", "LAX");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Connection Error with server badHost:8080:"));
    }

    @Test
    public void testSearchOptionWithBadPort() {
        MainMethodResult result = invokeMain(Project4.class, "-host", "localhost", "-port", "4", "-search", "airline", "PDX", "LAX");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Connection Error with server localhost:4"));
    }
*/
    /*@Test
    public void testNotEnoughArgs() {
        MainMethodResult result = invokeMain(Project4.class, "name", "123", "pdx", "10/21/2013", "12:35", "pm", "sea", "10/21/2013", "1:00");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("** Error: Host and Port Missing. Missing command line arguments"));
    }
 @Test
    public void testAddValidFlight1() {
        MainMethodResult result = invokeMain(Project4.class, "Foo Airlines", "123", "pdx", "10/21/2013", "12:35", "pm", "sea", "10/21/2013", "1:00", "pm");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), containsString("Added flight 123 to \"Foo Airlines\""));
    }

    @Test
    public void testAddValidFlight2() {
        MainMethodResult result = invokeMain(Project4.class, "-print", "Foo Airlines", "456", "pdx", "3/21/2013", "12:35", "am", "sea", "3/21/2013", "1:15", "am");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), containsString("Added flight 456 to \"Foo Airlines\""));
        assertThat(result.getOut(), containsString("Flight duration is 40 minutes"));
    }

    @Test
    public void testAddValidFlight3() {
        MainMethodResult result = invokeMain(Project4.class, "-print", "Foo Airlines", "592", "LAX", "12/25/2013", "12:00", "pm", "JFK", "12/25/2013", "7:15", "pm");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), containsString("Added flight 592 to \"Foo Airlines\""));
        assertThat(result.getOut(), containsString("Flight duration is 435 minutes"));
    }

    @Test
    public void testValidFlightSearch() {
        MainMethodResult result = invokeMain(Project4.class, "-search", "Foo Airlines", "pdx", "sea");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), containsString("Flight duration is 25 minutes."));
    }

    @Test
    public void testSearchNoFind() {
        MainMethodResult result = invokeMain(Project4.class, "-search", "Ghost Airlines", "pdx", "sea");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), containsString("Airline \"Ghost Airlines\" not found"));
    }

*/
/*

    @Test
    public void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
        assertThat(result.getErr(), result.getExitCode(), equalTo(0));
        String out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(0)));
    }

    @Test
    public void test3NoValues() {
        String key = "KEY";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, key );
        assertThat(result.getErr(), result.getExitCode(), equalTo(0));
        String out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(0)));
        assertThat(out, out, containsString(Messages.formatKeyValuePair(key, null)));
    }

    @Test
    public void test4AddValue() {
        String key = "KEY";
        String value = "VALUE";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, key, value );
        assertThat(result.getErr(), result.getExitCode(), equalTo(0));
        String out = result.getOut();
        assertThat(out, out, containsString(Messages.mappedKeyValue(key, value)));

        result = invokeMain( Project4.class, HOSTNAME, PORT, key );
        out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(1)));
        assertThat(out, out, containsString(Messages.formatKeyValuePair(key, value)));

        result = invokeMain( Project4.class, HOSTNAME, PORT );
        out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(1)));
        assertThat(out, out, containsString(Messages.formatKeyValuePair(key, value)));
    }
*/
}