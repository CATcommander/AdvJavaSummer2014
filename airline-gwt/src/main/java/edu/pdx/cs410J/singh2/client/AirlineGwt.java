package edu.pdx.cs410J.singh2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

    private DockPanel dockPanel = new DockPanel();

    private HorizontalPanel airlinePanel = new HorizontalPanel();
    private HorizontalPanel addFlightPanel = new HorizontalPanel();
    private HorizontalPanel searchFlightPanel = new HorizontalPanel();

    private FlexTable airlineTable = new FlexTable();

    private Button addFlightButton = new Button("Add a Flight");
    private Button searchButton = new Button("Search for Flights");
    private Button helpButton = new Button("Help");


    public void onModuleLoad() {


        dockPanel.setSpacing(4);
        dockPanel.setHorizontalAlignment(dockPanel.ALIGN_CENTER);

        setAirlineMenu(airlineTable);

        airlinePanel.add(airlineTable);
        addFlightPanel.add(addFlightButton);
        searchFlightPanel.add(searchButton);

        dockPanel.add(new HTML("CS410J Airline Database"), dockPanel.NORTH);

        dockPanel.add(airlinePanel, dockPanel.WEST);
        dockPanel.add(addFlightPanel, dockPanel.CENTER);
        dockPanel.add(searchFlightPanel, dockPanel.EAST);
        dockPanel.add(helpButton, dockPanel.SOUTH);

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
        RootPanel rootPanel = RootPanel.get();
        rootPanel.add(button);
        rootPanel.add(textBox);*/
    }

    private void setAirlineMenu(FlexTable flexTable) {
        flexTable.setCellPadding(2);
        flexTable.setBorderWidth(2);
        flexTable.setCellSpacing(2);
    }
}
