package edu.pdx.cs410J.singh2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.util.ArrayList;
import java.util.Date;


/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

    private final String README = "This is a simple Airline Database Application to keep track of airlines."
            + "\nClick \'Add a Flight\' to add a flight to the database or \'Search\' to search for flight(s)";

    private VerticalPanel mainPanel = new VerticalPanel();
    private VerticalPanel rightPanel = new VerticalPanel();
    private VerticalPanel insidePanel = new VerticalPanel();

    private DockPanel dockPanel = new DockPanel();

    private FlexTable flexTable = new FlexTable();

    private Button submit = new Button("Submit");
    private Button clear = new Button("Clear");

    public void onModuleLoad() {

        Command helpCommand = new Command() {
            @Override
            public void execute() {
                Window.alert(README);
            }
        };

        setAirlineHeader(flexTable);

        // Create a menu bar
        MenuBar menu = new MenuBar();
        menu.setAutoOpen(true);
        menu.setWidth("1200px");
        menu.setAnimationEnabled(true);

        MenuBar helpMenu = new MenuBar(true);

        menu.addItem(new MenuItem("Help", true, helpMenu));
        menu.addSeparator();

        helpMenu.addItem("README", helpCommand);

        mainPanel.add(menu);

        flexTable.setWidth("50em");
        flexTable.setCellSpacing(4);
        flexTable.setCellPadding(10);

        mainPanel.setBorderWidth(1);
        mainPanel.setSpacing(3);

        rightPanel.setSpacing(2);

        VerticalPanel panel1 = new VerticalPanel();
        VerticalPanel panel2 = new VerticalPanel();
        VerticalPanel panel3 = new VerticalPanel();

        panel1.setSpacing(2);
        panel2.setSpacing(2);
        panel3.setSpacing(3);

        final TextBox airlineBox = makeTextBoxWithLabel("Delta Airlines");
        airlineBox.setMaxLength(25);
        airlineBox.setVisibleLength(25);

        final TextBox flightNumberBox = makeTextBoxWithLabel("432");
        flightNumberBox.setMaxLength(6);
        flightNumberBox.setVisibleLength(6);

        final TextBox departureBox = makeTextBoxWithLabel("PDX");
        departureBox.setMaxLength(3);
        departureBox.setVisibleLength(3);

        final TextBox arrivalBox = makeTextBoxWithLabel("LAX");
        arrivalBox.setMaxLength(3);
        arrivalBox.setVisibleLength(3);

        panel1.add(new Label("Airline"));
        panel1.add(airlineBox);

        panel1.add(new Label("Flight Number"));
        panel2.add(flightNumberBox);

        panel2.add(new Label("Departure Airport"));
        panel2.add(departureBox);

        panel2.add(new Label(" Departure Date & Time"));

        HorizontalPanel hp = new HorizontalPanel();
        final TextBox departureDateBox = new TextBox();

        departureDateBox.getElement().setAttribute("placeholder", "hh:mm");
        departureDateBox.setFocus(true);
        departureDateBox.setMaxLength(5);
        departureDateBox.setVisibleLength(5);

        hp.setSpacing(4);

        final DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");

        final DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox.getDatePicker().setYearArrowsVisible(true);
        dateBox.getElement().setAttribute("placeholder", "Select Date");

        hp.add(dateBox);
        hp.add(departureDateBox);

        final ListBox departureDropBox = new ListBox(false);

        departureDropBox.addItem("am");
        departureDropBox.addItem("pm");

        hp.add(departureDropBox);

        panel2.add(hp);

        rightPanel.add(panel1);
        rightPanel.add(panel2);

        panel3.add(new Label("Arrival Airport"));
        panel3.add(arrivalBox);

        panel3.add(new Label(" Arrival Date & Time"));

        HorizontalPanel hp1 = new HorizontalPanel();
        final TextBox arrivalDateBox = new TextBox();

        arrivalDateBox.getElement().setAttribute("placeholder", "hh:mm");
        arrivalDateBox.setFocus(true);
        arrivalDateBox.setMaxLength(5);
        arrivalDateBox.setVisibleLength(5);

        hp1.setSpacing(4);

        final DateBox dateBox1 = new DateBox();
        dateBox1.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox1.getDatePicker().setYearArrowsVisible(true);
        dateBox1.getElement().setAttribute("placeholder", "Select Date");

        hp1.add(dateBox1);
        hp1.add(arrivalDateBox);

        final ListBox arrivalDropBox = new ListBox(false);

        arrivalDropBox.addItem("am");
        arrivalDropBox.addItem("pm");

        hp1.add(arrivalDropBox);
        panel3.add(hp1);

        rightPanel.add(panel3);

        final ListBox dropBox = new ListBox(false);
        dropBox.addItem("Add Flight");
        dropBox.addItem("Search for Flight");

        insidePanel.add(dropBox);
        insidePanel.setSpacing(3);

        rightPanel.add(insidePanel);

        HorizontalPanel submitAndClearPanel = new HorizontalPanel();
        submitAndClearPanel.add(submit);
        submitAndClearPanel.add(clear);
        submitAndClearPanel.setSpacing(3);

        rightPanel.add(submitAndClearPanel);

        dropBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                airlineBox.setEnabled(true);
                departureBox.setEnabled(true);
                arrivalBox.setEnabled(true);

                if (dropBox.getItemText(dropBox.getSelectedIndex()).equalsIgnoreCase("Search for Flight")) {

                    flightNumberBox.setEnabled(false);

                    departureDateBox.setEnabled(false);
                    departureDropBox.setEnabled(false);
                    dateBox.setEnabled(false);

                    arrivalDateBox.setEnabled(false);
                    arrivalDropBox.setEnabled(false);
                    dateBox1.setEnabled(false);
                }
                else {
                    flightNumberBox.setEnabled(true);

                    departureDateBox.setEnabled(true);
                    departureDropBox.setEnabled(true);
                    dateBox.setEnabled(true);

                    arrivalDateBox.setEnabled(true);
                    arrivalDropBox.setEnabled(true);
                    dateBox1.setEnabled(true);
                }
            }
        });

        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                String airlineName = airlineBox.getText();
                if (airlineName.equalsIgnoreCase("")) {
                    Window.alert("Please Enter Airline Name");
                    return;
                }
                else
                    validateAirlineName(airlineName);

                String departAirport = departureBox.getText().toUpperCase();
                if (departAirport.equalsIgnoreCase("")) {
                    Window.alert("Please Enter Departure Airport Code");
                    return;
                }
                else
                    validateThreeLetterCode(departAirport);

                String arriveAirport = arrivalBox.getText().toUpperCase();
                if (arriveAirport.equalsIgnoreCase("")) {
                    Window.alert("Please Enter Arrival Airport Code");
                    return;
                }
                else
                    validateThreeLetterCode(arriveAirport);

                if (dropBox.getItemText(dropBox.getSelectedIndex()).equalsIgnoreCase("Add Flight")) {

                    int flightNumber=0;
                    Window.alert(flightNumberBox.getText());
                    if (flightNumberBox.getText().equalsIgnoreCase("")) {
                        Window.alert("Please Enter Flight Number");
                        return;
                    }
                    else {
                        try {
                            flightNumber = Integer.parseInt(flightNumberBox.getText());
                        } catch (IllegalArgumentException e) {
                            Window.alert(flightNumberBox.getText() + " is not a flight number");
                            return;
                        }
                    }

                    String departDate = dateFormat.format(dateBox.getValue());
                    String departTime = departureDateBox.getText();
                    String departMarker = departureDropBox.getItemText(departureDropBox.getSelectedIndex());
                    String departFullDate = departDate + " " + departTime + " " + departMarker;

                    validateDateAndTime(departFullDate);

                    String arriveDate = dateFormat.format(dateBox1.getValue());
                    String arriveTime = arrivalDateBox.getText();
                    String arriveMarker = arrivalDropBox.getItemText(arrivalDropBox.getSelectedIndex());
                    String arriveFullDate = arriveDate + " " + arriveTime + " " + arriveMarker;
                    validateDateAndTime(arriveFullDate);

                    final Airline airline = new Airline(airlineName);
                    final Flight flight = new Flight(flightNumber, departAirport, departFullDate, arriveAirport, arriveFullDate);
                    airline.addFlight(flight);


                    AirlineServiceAsync async = GWT.create(AirlineService.class);
                    async.addFlight(airline, new AsyncCallback<ArrayList<AbstractAirline>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(ArrayList<AbstractAirline> result) {
                            StringBuilder sb = new StringBuilder();

                            for ( AbstractAirline flight : result) {
                                sb.append(flight);
                                sb.append("\n");
                            }
                            System.out.println(sb.toString());
                            Window.alert( sb.toString() );
                            Window.alert( airline.toString());
                        }
                    });
                }
                else
                {

                }
            }
        });

        clear.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                airlineBox.setText("");
                flightNumberBox.setText("");
                departureBox.setText("");
                departureDateBox.setText("");
                departureDropBox.setEnabled(true);
                arrivalBox.setText("");
                arrivalDateBox.setText("");
                arrivalDropBox.setEnabled(true);
            }

        });

        dockPanel.add(new HTML("CS410J Airline Database"), DockPanel.NORTH);
        dockPanel.add(menu, DockPanel.NORTH);

        dockPanel.add(flexTable, DockPanel.WEST);
        dockPanel.add(rightPanel, DockPanel.EAST);

        RootPanel.get().add(dockPanel);

    }

    private TextBox makeTextBoxWithLabel(String placeholder) {
        TextBox textBox = new TextBox();
        VerticalPanel panel = new VerticalPanel();

        panel.setSpacing(3);
        textBox.getElement().setAttribute("placeholder", placeholder);
        textBox.setFocus(true);

        panel.add(textBox);

        return textBox;
    }

    private void setAirlineHeader(FlexTable flexTable) {
        flexTable.setCellPadding(2);
        flexTable.setBorderWidth(1);
        flexTable.setCellSpacing(2);
        flexTable.setTitle("table");

        flexTable.setText(0, 0, "Airline");
        flexTable.setText(0, 1, "Flight Number");
        flexTable.setText(0, 2, "Departure");
        flexTable.setText(0, 3, "Date & Time");
        flexTable.setText(0, 4, "Arrival");
        flexTable.setText(0, 5, "Date & Time");
    }

    /**
     * Validates the Date, time and am/pm marker
     * @param args
     * date and time string
     * @return
     * validated date and time
     */
    public void validateDateAndTime(String args) {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        String [] dateAndTime = args.split(" ");

        try {
            dateTimeFormat.parseStrict(args);
        } catch (IllegalArgumentException e) {
            Window.alert("Invalid Date & Time. Must be in mm/dd/yyyy hh:mm am/pm format");
            return;
        }

        validateDate(dateAndTime[0]);
        validateTime(dateAndTime[1]);
        validateDay(dateAndTime[2]);
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
    public void validateAirlineName(String arg) {
        for (char c: arg.toCharArray()) {
            if (!Character.isLetter(c)) {
                Window.alert("Invalid Airline Name");
                return;
            }
        }
    }

    /**
     * validates three letter code is characters only. If it contains
     * anything except characters, the program will exit
     * @param arg
     * the string to parse with three letter code
     * @return
     * validated parsed three letter code in upper case
     */
    private void validateThreeLetterCode(String arg) {
        for (char c: arg.toCharArray()) {
            if (arg.length() != 3 || Character.isDigit(c)) {
                Window.alert("Invalid Three-Letter Code");
                return;
            }
        }
        String validCode = AirportNames.getName(arg.toUpperCase());
        if (validCode == null) {
            Window.alert("Airport code \'" + arg + "\' does not exist");
            return;
        }
    }

    /**
     * Validate the date so the date format is mm/dd/yyyy
     * if <code>args</code> is invalid, the program exits
     * @param args
     * the date of departure/arrival
     * @return date
     * returns the valid date in correct format (mm/dd/yyyy)
     */
    private void validateDate(String args) {
        for (char c: args.toCharArray()) {
            if (Character.isLetter(c)) {
                Window.alert("Invalid Date. Can not contain characters or symbols");
                return;
            }
        }

        String date[] = args.split("/");
        int month, day, year;
        try {
            month = Integer.parseInt(date[0]);
            day = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);
        } catch (NumberFormatException ex) {
            Window.alert("Invalid Date");
            return;
        }
        if (month < 1 || month > 12) {
            Window.alert("Invalid Month");
            return;
        }
        if (day < 1 || day > 31) {
            Window.alert("Invalid Day");
            return;
        }
        /* year is 9999 assuming humanity or earth survives that long */
        if (year < 1800 || year > 9999) {
            Window.alert("Invalid Year");
            return;
        }
    }

    /**
     * Validates the time format. if <code>args</code> is not
     * valid time, program exits
     * @param args
     * the time in 24-hour format (string to parse)
     */
    private void validateTime(String args) {
        for (char c: args.toCharArray()) {
            if (Character.isLetter(c)) {
                Window.alert("Invalid Time. Can not contain characters or symbols");
                return;
            }
        }
        String time[] = args.split(":");
        int hour = 0, minute = 0;
        try {
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        } catch (NumberFormatException ex) {
            Window.alert("Error: Invalid Time");
            return;
        }
        /* check if time is in 24-hour format */
        if (hour > 12 || hour < 1) {
            Window.alert("Error: Invalid Hour");
            return;
        }
        if (minute > 59 || minute < 0) {
            Window.alert("Invalid Minute(s)");
            return;
        }
    }
    private static void validateDay(String arg) {
        for (char c: arg.toCharArray()) {
            if (arg.length() != 2 || Character.isDigit(c)) {
                Window.alert("Invalid am/pm marker");
                return;
            }
        }
    }
}
