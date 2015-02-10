/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventnew;

import com.asgteach.familytree.model.Adoption;
import com.asgteach.familytree.model.Birth;
import com.asgteach.familytree.model.Death;
import com.asgteach.familytree.model.Divorce;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Marriage;
import com.asgteach.familytree.model.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javax.swing.SwingUtilities;
import org.openide.util.NbBundle.Messages;

@Messages({"CTL_Panel1Name=Event Type"})
public final class Visual1FXController extends JFXPanel implements Initializable {
    // Note: because this class extends JFXPanel, methods firePropertyChange()
    // and addPropertyChangeListener() are available for property change support.

    // FXML controls
    @FXML
    private RadioButton birthRadioButton;
    @FXML
    private RadioButton adoptRadioButton;
    @FXML
    private RadioButton marriageRadioButton;
    @FXML
    private RadioButton divorceRadioButton;
    @FXML
    private RadioButton deathRadioButton;
    @FXML
    private Label personLabel;
    @FXML
    private TextArea eventDisplay;
    @FXML
    private Label eventName;
    public static final String PROP_PERSON = "person";
    public static final String PROP_EVENT = "event";
    public static final String PROP_EVENTTYPE_INFO = "eventTypeInfo";
    private Event event = new Birth(new Long(-5));
    private Person person;

    private void fireEventPropertyChange(Event event) {
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setEvent(event);
        // Tell the wizard to check the validity
        SwingUtilities.invokeLater(() -> {
            firePropertyChange(PROP_EVENTTYPE_INFO, null, eventInfo);
        });
    }

    @FXML
    private void eventSelectionBirth(ActionEvent actionEvent) {
        // the event type changed, but not necessary to notify wizard panel
        event = new Birth((long) -5);
        event.setPerson(person);
        setEventDisplay(event);
        fireEventPropertyChange(event);
    }

    @FXML
    private void eventSelectionAdoption(ActionEvent actionEvent) {
        event = new Adoption((long) -4);
        event.setPerson(person);
        setEventDisplay(event);
        fireEventPropertyChange(event);
    }

    @FXML
    private void eventSelectionMarriage(ActionEvent actionEvent) {
        event = new Marriage((long) -3);
        event.setPerson(person);
        setEventDisplay(event);
        fireEventPropertyChange(event);
    }

    @FXML
    private void eventSelectionDivorce(ActionEvent actionEvent) {
        event = new Divorce((long) -2);
        event.setPerson(person);
        setEventDisplay(event);
        fireEventPropertyChange(event);
    }

    @FXML
    private void eventSelectionDeath(ActionEvent actionEvent) {
        event = new Death((long) -1);
        event.setPerson(person);
        setEventDisplay(event);
        fireEventPropertyChange(event);
    }

    // called both from the wizard class and from this class
    // therefore, must check to see which thread is current
    public void setEventDisplay(final Event e) {
        if (Platform.isFxApplicationThread()) {
            eventDisplay.setText(e.toString());
            eventName.setText(e.getEventName());
        } else {
            Platform.runLater(() -> {
                eventDisplay.setText(e.toString());
                eventName.setText(e.getEventName());
            });
        }
    }

    // Safe to call from Wizard
    @Override
    public String getName() {
        return Bundle.CTL_Panel1Name();
    }

    // called exclusively from the wizard class
    public void setPerson(final Person p) {
        Platform.runLater(() -> {
            person = p;
            event.setPerson(p);
            personLabel.setText(person.toString());
            setEventDisplay(event);
            fireEventPropertyChange(event);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        event = new Birth((long) -5);
        eventName.setText(event.getEventName());
    }
}
