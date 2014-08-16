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
import java.util.Collection;
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
    private Button displayAll = new Button("Display All");

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

        flexTable.setWidth("72em");
        flexTable.setCellSpacing(2);
        flexTable.setCellPadding(6);

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

        panel1.add(makeNewLabel("Airline"));
        panel1.add(airlineBox);

        panel1.add(makeNewLabel("Flight Number"));
        panel2.add(flightNumberBox);

        panel2.add(makeNewLabel("Departure Airport"));
        panel2.add(departureBox);

        panel2.add(makeNewLabel(" Departure Date & Time"));

        HorizontalPanel hp = new HorizontalPanel();
        final TextBox departureDateBox = new TextBox();

        departureDateBox.getElement().setAttribute("placeholder", "hh:mm");
        departureDateBox.setFocus(true);
        departureDateBox.setMaxLength(5);
        departureDateBox.setVisibleLength(5);
        departureDateBox.setStyleName("textBox");

        hp.setSpacing(4);

        final DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");

        final DateBox dateBox = new DateBox();
        dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox.getDatePicker().setYearArrowsVisible(true);
        dateBox.getElement().setAttribute("placeholder", "Select Date");
        dateBox.setStyleName("textBox");

        hp.add(dateBox);
        hp.add(departureDateBox);

        final ListBox departureDropBox = new ListBox(false);

        departureDropBox.addItem("am");
        departureDropBox.addItem("pm");

        hp.add(departureDropBox);

        panel2.add(hp);

        rightPanel.add(panel1);
        rightPanel.add(panel2);

        panel3.add(makeNewLabel("Arrival Airport"));
        panel3.add(arrivalBox);

        panel3.add(makeNewLabel("Arrival Date & Time"));

        HorizontalPanel hp1 = new HorizontalPanel();
        final TextBox arrivalDateBox = new TextBox();

        arrivalDateBox.getElement().setAttribute("placeholder", "hh:mm");
        arrivalDateBox.setFocus(true);
        arrivalDateBox.setMaxLength(5);
        arrivalDateBox.setVisibleLength(5);
        arrivalDateBox.setStyleName("textBox");

        hp1.setSpacing(4);

        final DateBox dateBox1 = new DateBox();
        dateBox1.setFormat(new DateBox.DefaultFormat(dateFormat));
        dateBox1.getDatePicker().setYearArrowsVisible(true);
        dateBox1.getElement().setAttribute("placeholder", "Select Date");
        dateBox1.setStyleName("textBox");

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
        submit.setStyleName("greenButton");
        submitAndClearPanel.add(clear);
        clear.setStyleName("redButton");
        submitAndClearPanel.add(displayAll);
        displayAll.setStyleName("yellowButton");
        submitAndClearPanel.setSpacing(3);

        rightPanel.add(submitAndClearPanel);

        dropBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                airlineBox.setEnabled(true);
                departureBox.setEnabled(true);
                arrivalBox.setEnabled(true);

                if (dropBox.getItemText(dropBox.getSelectedIndex()).equalsIgnoreCase("Search for Flight")) {
                    clear.click();
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

        displayAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                AirlineServiceAsync serviceAsync = GWT.create(AirlineService.class);
                serviceAsync.displayAll(new AsyncCallback<ArrayList<AbstractAirline>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(ArrayList<AbstractAirline> result) {
                        int row = 1;
                        int column = 0;

                        for (AbstractAirline absAirline: result) {

                            Collection<Flight> f = absAirline.getFlights();
                            flexTable.setText(row, column++, absAirline.getName());

                            for (Flight flight1: f) {

                                flexTable.setText(row, column++, Integer.toString(flight1.getNumber()));
                                flexTable.setText(row, column++, flight1.getSrcCode());
                                flexTable.setText(row, column++, flight1.getDepartNice());
                                flexTable.setText(row, column++, flight1.getDestCode());
                                flexTable.setText(row, column++, flight1.getArrivalNice());
                                flexTable.setText(row, column++, Integer.toString(((int) flight1.getDuration())));
                            }

                            row++;
                            column = 0;
                        }
                    }
                });
            }
        });

        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                if (dropBox.getItemText(dropBox.getSelectedIndex()).equalsIgnoreCase("Add Flight")) {

                    String airlineName = airlineBox.getText();
                    if (airlineName == null) {
                        Window.alert("Please Enter Airline Name");
                        return;
                    }
                    else {
                        if (!validateAirlineName(airlineName))
                            return;
                    }

                    int flightNumber;

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

                    String departAirport = departureBox.getText().toUpperCase();
                    if (departAirport.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Departure Airport Code");
                        return;
                    }
                    else {
                        if (!validateThreeLetterCode(departAirport))
                            return;
                    }

                    String departDate = dateFormat.format(dateBox.getValue());

                    if (dateBox.getTextBox().getText().equalsIgnoreCase("")) {
                        Window.alert("Please Choose a Date");
                        return;
                    }

                    String departTime = departureDateBox.getText();
                    if (departTime.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Time");
                        return;
                    }

                    String departMarker = departureDropBox.getItemText(departureDropBox.getSelectedIndex());
                    String departFullDate = departDate + " " + departTime + " " + departMarker;

                    if (!validateDateAndTime(departFullDate))
                        return;

                    String arriveAirport = arrivalBox.getText().toUpperCase();
                    if (arriveAirport.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Arrival Airport Code");
                        return;
                    }
                    else {
                        if (!validateThreeLetterCode(arriveAirport))
                            return;
                    }

                    String arriveDate = dateFormat.format(dateBox1.getValue());
                    String arriveTime = arrivalDateBox.getText();
                    if (arriveTime.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Time");
                        return;
                    }

                    String arriveMarker = arrivalDropBox.getItemText(arrivalDropBox.getSelectedIndex());
                    String arriveFullDate = arriveDate + " " + arriveTime + " " + arriveMarker;

                    if (!validateDateAndTime(arriveFullDate))
                        return;

                    final Airline airline = new Airline(airlineName);
                    final AbstractFlight flight = new Flight(flightNumber, departAirport, departFullDate, arriveAirport, arriveFullDate);

                    final AirlineServiceAsync async = GWT.create(AirlineService.class);
                    async.addFlight(airline, flight, new AsyncCallback<ArrayList<AbstractAirline>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                        int row = 1;
                        int column = 0;

                        @Override
                        public void onSuccess(ArrayList<AbstractAirline> result) {

                            for (AbstractAirline absAirline: result) {
                                Collection<Flight> f = absAirline.getFlights();
                                flexTable.setText(row, column++, absAirline.getName());

                                for (Flight flight1: f) {

                                    flexTable.setText(row, column++, Integer.toString(flight1.getNumber()));
                                    flexTable.setText(row, column++,flight1.getSrcCode());
                                    flexTable.setText(row, column++, flight1.getDepartNice());
                                    flexTable.setText(row, column++, flight1.getDestCode());
                                    flexTable.setText(row, column++, flight1.getArrivalNice());
                                    flexTable.setText(row, column++, Integer.toString(((int) flight1.getDuration())));

                                }
                                row++;
                                column = 0;

                                //flexTable.setText(row, column, prettyPrint(((Airline) absAirline)).toString());
                            }
                        }
                    });
                }
                else if (dropBox.getItemText(dropBox.getSelectedIndex()).equalsIgnoreCase("Search for Flight")) {

                    String airlineName = airlineBox.getText();
                    if (airlineName.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Airline Name");
                        return;
                    }
                    else {
                        if (!validateAirlineName(airlineName))
                            return;
                    }

                    String departAirport = departureBox.getText().toUpperCase();

                    if (departAirport.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Departure Airport Code");
                        return;
                    }
                    else {
                        if (!validateThreeLetterCode(departAirport))
                            return;
                    }

                    String arriveAirport = arrivalBox.getText().toUpperCase();
                    if (arriveAirport.equalsIgnoreCase("")) {
                        Window.alert("Please Enter Arrival Airport Code");
                        return;
                    }
                    else {
                        if (!validateThreeLetterCode(arriveAirport))
                            return;
                    }

                    final AirlineServiceAsync serviceAsync = GWT.create(AirlineService.class);

                    serviceAsync.search(airlineName, departAirport, arriveAirport, new AsyncCallback<Airline>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(Airline result) {
                            StringBuilder stringBuilder = new StringBuilder();

                            Collection<Flight> flights = result.getFlights();
                            for (Flight flight: flights) {
                                stringBuilder.append(prettyPrint(result));
                                Window.alert("inside for loop " + flight.toString());
                            }
                            Window.alert("search func" + stringBuilder.toString());
                            //Window.alert("prety print " + prettyPrint(result).toString());
                        }
                    });
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

    private Label makeNewLabel(String labelName) {
        Label label = new Label(labelName);
        label.setStyleName("label");
        label.setVisible(true);

        return label;
    }
    private StringBuilder prettyPrint(Airline airline) {


        Collection<Flight> flightList;
        flightList = airline.getFlights();

        StringBuilder stringBuilder = new StringBuilder();

        if (flightList == null) {
            Window.alert("Empty Airline List");
            return null;
        }
        stringBuilder.append("\nAirline " + airline.toString() + "\n");
        // 432 Portland OR, Sat Dec 27, 2010 1:22 PST LAX etc
        for (Flight flight : flightList) {

            stringBuilder.append("Flight " + flight.getNumber());
            stringBuilder.append(" Departs " + flight.getSrcCode());
            stringBuilder.append(" at " + flight.getDepartNice());
            stringBuilder.append(" Arrives " + flight.getDestCode());
            stringBuilder.append(" at " + flight.getArrivalNice() + ". Duration: " + flight.getDuration() + " minutes\n");
        }

        return stringBuilder;
    }

    private TextBox makeTextBoxWithLabel(String placeholder) {
        TextBox textBox = new TextBox();
        VerticalPanel panel = new VerticalPanel();
        textBox.setStyleName("textBox");

        panel.setSpacing(3);
        textBox.getElement().setAttribute("placeholder", placeholder);
        textBox.setFocus(true);

        panel.add(textBox);

        return textBox;
    }

    private void setAirlineHeader(FlexTable flexTable) {
        flexTable.setCellPadding(4);
        flexTable.setBorderWidth(1);
        flexTable.setCellSpacing(2);
        flexTable.setStyleName("table");

        flexTable.setText(0, 0, "Airline");
        flexTable.setText(0, 1, "Flight Number");
        flexTable.setText(0, 2, "Departure");
        flexTable.setText(0, 3, "Date & Time");
        flexTable.setText(0, 4, "Arrival");
        flexTable.setText(0, 5, "Date & Time");
        flexTable.setText(0, 6, "Duration in minutes");
    }

    /**
     * Validates the Date, time and am/pm marker
     * @param args
     * date and time string
     * @return
     * validated date and time
     */
    public boolean validateDateAndTime(String args) {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        String [] dateAndTime = args.split(" ");

        try {
            dateTimeFormat.parseStrict(args);
        } catch (IllegalArgumentException e) {
            Window.alert("Invalid Date & Time. Must be in mm/dd/yyyy hh:mm am/pm format");
            return false;
        }

        validateDate(dateAndTime[0]);
        validateTime(dateAndTime[1]);

        return true;
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
    public boolean validateAirlineName(String arg) {
        for (char c: arg.toCharArray()) {
            if (Character.isDigit(c)) {
                Window.alert("Invalid Airline Name");
                return false;
            }
        }
        return true;
    }

    /**
     * validates three letter code is characters only. If it contains
     * anything except characters, the program will exit
     * @param arg
     * the string to parse with three letter code
     * @return
     * validated parsed three letter code in upper case
     */
    private boolean validateThreeLetterCode(String arg) {
        for (char c: arg.toCharArray()) {
            if (arg.length() != 3 || Character.isDigit(c)) {
                Window.alert("Invalid Three-Letter Code");
                return false;
            }
        }
        String validCode = AirportNames.getName(arg.toUpperCase());
        if (validCode == null) {
            Window.alert("Airport code \'" + arg + "\' does not exist");
            return false;
        }
        return true;
    }

    /**
     * Validate the date so the date format is mm/dd/yyyy
     * if <code>args</code> is invalid, the program exits
     * @param args
     * the date of departure/arrival
     * @return date
     * returns the valid date in correct format (mm/dd/yyyy)
     */
    private boolean validateDate(String args) {
        for (char c: args.toCharArray()) {
            if (Character.isLetter(c)) {
                Window.alert("Invalid Date. Can not contain characters or symbols");
                return false;
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
            return false;
        }
        if (month < 1 || month > 12) {
            Window.alert("Invalid Month");
            return false;
        }
        if (day < 1 || day > 31) {
            Window.alert("Invalid Day");
            return false;
        }
        /* year is 9999 assuming humanity or earth survives that long */
        if (year < 1800 || year > 9999) {
            Window.alert("Invalid Year");
            return false;
        }
        return true;
    }

    /**
     * Validates the time format. if <code>args</code> is not
     * valid time, program exits
     * @param args
     * the time in 24-hour format (string to parse)
     */
    private boolean validateTime(String args) {
        for (char c: args.toCharArray()) {
            if (Character.isLetter(c)) {
                Window.alert("Invalid Time. Can not contain characters or symbols");
                return false;
            }
        }
        String time[] = args.split(":");
        int hour = 0, minute = 0;
        try {
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        } catch (NumberFormatException ex) {
            Window.alert("Error: Invalid Time");
            return false;
        }
        /* check if time is in 24-hour format */
        if (hour > 12 || hour < 1) {
            Window.alert("Error: Invalid Hour");
            return false;
        }
        if (minute > 59 || minute < 0) {
            Window.alert("Invalid Minute(s)");
            return false;
        }
        return true;
    }

}
