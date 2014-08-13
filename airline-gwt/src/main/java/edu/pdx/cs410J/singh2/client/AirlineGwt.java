package edu.pdx.cs410J.singh2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

    public void onModuleLoad() {
        // example in class
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
        rootPanel.add(textBox);
    }
}
