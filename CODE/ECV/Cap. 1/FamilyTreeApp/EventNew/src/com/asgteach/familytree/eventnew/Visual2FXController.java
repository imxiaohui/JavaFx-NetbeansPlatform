/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventnew;

import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.SwingUtilities;
import org.openide.util.NbBundle.Messages;

public final class Visual2FXController extends JFXPanel implements Initializable {
    // Note: because this class extends JFXPanel, methods firePropertyChange()
    // and addPropertyChangeListener() are available for property change support.

    // FX Controls
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField stateTextField;
    @FXML
    private TextArea eventDisplay;
    @FXML
    private Label personLabel;
    @FXML
    private Label eventName;
    private static final Logger logger = Logger.getLogger(Visual2FXController.class.getName());
    public static final String PROP_EVENT_DATE = "eventDate";
    public static final String PROP_LOCATION_INFO = "locationInfo";
    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
    private Person person;
    private Event event;
    private boolean isValid = false;
    private String message = Bundle.CTL_Panel2_EmptyDate();

    @Messages({"CTL_Panel2_EmptyDate=Date field cannot be empty."})
    // Listen for both the Return and Tab to process input
    @FXML
    private void handleDateAction(ActionEvent ae) {
        handleDate();
    }

    @FXML
    private void handleKeyTypedDate(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            handleDate();
        }
    }

    // Whenever isValid is updated, 
    // you must fire a property change event
    // to send EventInfo to wizard.
    private void handleDate() {
        logger.log(Level.FINE, "Action/Key Event for {0}", dateTextField.getText());
        if (dateTextField.getText().isEmpty()) {
            isValid = false;
            message = Bundle.CTL_Panel2_EmptyDate();
        } else {
            try {
                Date tempdate =
                        DateFormat.getDateInstance().parse(dateTextField.getText());
                dateTextField.setText(format.format(tempdate));
                event.setEventDate(tempdate);
                isValid = true;
                message = "";
            } catch (ParseException ex) {
                message = ex.getLocalizedMessage();
                isValid = false;
            }
        }
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setIsValid(isValid);
        eventInfo.setMessage(message);
        eventInfo.setEvent(event);
        // Tell the wizard to check the validity
        SwingUtilities.invokeLater(() -> {
            firePropertyChange(PROP_EVENT_DATE, null, eventInfo);
        });
    }

    @FXML
    private void handleFieldInput(ActionEvent actionEvent) {
        handleLocation();
    }

    @FXML
    private void handleKeyTypedLocation(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            handleLocation();
        }
    }

    // Method handleLocation doesn't ever change isValid, but it does
    // update the location information in the event.
    // Fire a property change event to send new info to the wizard.
    private void handleLocation() {
        event.setTown(cityTextField.getText());
        event.setState_province(stateTextField.getText());
        event.setCountry(countryTextField.getText());
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setIsValid(isValid);
        eventInfo.setMessage(message);
        eventInfo.setEvent(event);
        SwingUtilities.invokeLater(() -> {
            firePropertyChange(PROP_LOCATION_INFO, null, eventInfo);
        });
    }

    @Messages("CTL_Panel2Name=Date and Place")
    // Safe to call from Wizard
    @Override
    public String getName() {
        return Bundle.CTL_Panel2Name();
    }

    private void updateControls() {
        cityTextField.setText(event.getTown());
        stateTextField.setText(event.getState_province());
        countryTextField.setText(event.getCountry());
        if (event.getEventDate() != null) {
            dateTextField.setText(format.format(event.getEventDate()));
            isValid = true;
            message = "";
        } else {
            dateTextField.setText("");
            isValid = false;
            message = Bundle.CTL_Panel2_EmptyDate();
        }
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setIsValid(isValid);
        eventInfo.setMessage(message);
        eventInfo.setEvent(event);
        SwingUtilities.invokeLater(() -> {
            // force the wizard to check the validity of the date
            firePropertyChange(PROP_EVENT_DATE, null, eventInfo);
        });
    }

    public void setEventDisplay(final Event e, final boolean includeControls) {
        Platform.runLater(() -> {
            event = e;
            eventDisplay.setText(event.toString());
            eventName.setText(event.getEventName());
            if (includeControls) {
                updateControls();
            }
        });
    }

    public void setPerson(final Person p) {        
        Platform.runLater(() -> {
            person = p;
            personLabel.setText(person.toString());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
