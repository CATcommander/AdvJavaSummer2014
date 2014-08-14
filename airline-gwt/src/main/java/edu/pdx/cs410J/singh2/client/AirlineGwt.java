package edu.pdx.cs410J.singh2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

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
    private Button cancel = new Button("Cancel");
    private RadioButton addFlightRadioButton;
    private RadioButton searchRadioButton;

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
        menu.setWidth("800px");
        menu.setAnimationEnabled(true);

        MenuBar helpMenu = new MenuBar(true);

        menu.addItem(new MenuItem("Help", true, helpMenu));
        menu.addSeparator();

        helpMenu.addItem("README", helpCommand);

        mainPanel.add(menu);

        FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
        flexTable.setWidth("32em");
        flexTable.setCellSpacing(5);
        flexTable.setCellPadding(3);

        mainPanel.setBorderWidth(1);

        rightPanel.setBorderWidth(1);
        rightPanel.setSpacing(2);


        insideTextBoxPanel();

        addFlightRadioButton = new RadioButton("Add a Flight", "Add a Flight");
        searchRadioButton = new RadioButton("Search for flight(s)", "Search for flight(s)");


        rightPanel.add(addFlightRadioButton);
        rightPanel.add(searchRadioButton);

        HorizontalPanel submitAndCancelPanel = new HorizontalPanel();
        submitAndCancelPanel.add(submit);
        submitAndCancelPanel.setSpacing(3);
        submitAndCancelPanel.add(cancel);

        rightPanel.add(submitAndCancelPanel);

        mainPanel.add(flexTable) ;
        mainPanel.add(rightPanel);

        dockPanel.add(new HTML("CS410J Airline Database"), dockPanel.NORTH);
        dockPanel.add(menu, dockPanel.NORTH);

        dockPanel.add(mainPanel, dockPanel.WEST);
        dockPanel.add(rightPanel, dockPanel.EAST);

        RootPanel.get().add(dockPanel);


/*        // example in class
        TextBox textBox = new TextBox();
        textBox.getElement().setAttribute("placeholder", "PDX");

        // Airline airline;
        // if (airline)

        Button button = new Button("Check the flights");
        button.addClickHandler(new ClickHandler() {
            public void onClick( ClickEvent clickEvent )
            {
                AirlineServiceAsync async = GWT.create( AirlineService.class );
                async.airline( new AsyncCallback<AbstractAirline>() {

                    public void onFailure( Throwable ex )
                    {
                        Window.alert(ex.toString());
                    }

                    public void onSuccess( AbstractAirline airline )
                    {
                        StringBuilder sb = new StringBuilder( airline.toString() );
                        Collection<AbstractFlight> flights = airline.getFlights();
                        int size = airline.getName().length();
                        for ( AbstractFlight flight : flights ) {
                            sb.append(flight);
                            sb.insert(15 + size, "\n");
                            sb.append("\n");
                        }
                        System.out.println(sb.toString());
                        Window.alert( sb.toString() );
                    }
                });
            }
        });
        */
    }

    private void insideTextBoxPanel() {
        TextBox airlineBox = new TextBox();
        airlineBox.setFocus(true);
        airlineBox.getElement().setAttribute("placeholder", "Delta Airlines");
        Label airlineBoxLabel = new Label("Airline");
        airlineBoxLabel.setVisible(true);

        insidePanel.add(airlineBoxLabel);
        insidePanel.add(airlineBox);

        TextBox flightNumberBox = new TextBox();
        flightNumberBox.getElement().setAttribute("placeholder", "423");
        Label flightNumberLabel = new Label("Flight Number");
        flightNumberBox.setFocus(true);

        insidePanel.add(flightNumberLabel);
        insidePanel.add(flightNumberBox);

        TextBox departureBox = new TextBox();
        departureBox.getElement().setAttribute("placeholder", "PDX");
        Label departureLabel = new Label("Departure");
        departureBox.setFocus(true);

        insidePanel.add(departureLabel);
        insidePanel.add(departureBox);

        TextBox departureDateBox = new TextBox();
        departureDateBox.getElement().setAttribute("placeholder", "mm/dd/yyyy");
        Label departureDateLabel = new Label("Departure Date");
        departureDateBox.setFocus(true);

        insidePanel.add(departureDateLabel);
        insidePanel.add(departureDateBox);

        TextBox arrivalBox = new TextBox();
        arrivalBox.getElement().setAttribute("placeholder", "LAX");
        Label arrivalLabel = new Label("Arrive");
        arrivalBox.setFocus(true);

        insidePanel.add(arrivalLabel);
        insidePanel.add(arrivalBox);

        TextBox arrivalDateBox = new TextBox();
        arrivalDateBox.getElement().setAttribute("placeholder", "mm/dd/yyyy");
        Label arrivalDateLabel = new Label("Arrival Date");
        arrivalDateBox.setFocus(true);

        insidePanel.add(arrivalDateLabel);
        insidePanel.add(arrivalDateBox);

        rightPanel.add(insidePanel);
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
}
