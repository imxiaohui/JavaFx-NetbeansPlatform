/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventfxeditor;

import com.asgteach.familytree.model.Birth;
import com.asgteach.familytree.model.ChildParentEvent;
import com.asgteach.familytree.model.Death;
import com.asgteach.familytree.model.Divorce;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Marriage;
import com.asgteach.familytree.model.ParentChildEvent;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public class EventFXEditorController implements Initializable {

    @FXML
    private Label idNum;
    @FXML
    private Label eventName;
    @FXML
    private Label personName;
    @FXML
    private Label relationship1Name;
    @FXML
    private Label relationship2Name;
    @FXML
    private Label personLabel1Name;
    @FXML
    private Label personLabel2Name;
    @FXML
    private TextField dateField;
    @FXML
    private TextField townField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField countryField;
    @FXML
    private ImageView imageView;
    boolean inSync = true;
    private Lighting lighting;
    private Date tempdate;
    private Event event;
    private static final Logger logger = Logger.getLogger(EventFXEditorController.class.getName());
    // SwingPropertyChangeSupport is thread-safe
    // true means fire property change events on the EDT
    private SwingPropertyChangeSupport propChangeSupport;             
    public static final String PROP_EVENTEDITOR_MODIFIED = "EventEditorModified";
    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
    private ObjectProperty<Event> eventProperty =
            new SimpleObjectProperty<>(this, "event", new Birth(new Long(-5)));

    @FXML
    private void handleKeyEvent(KeyEvent keyEvent) {
        // we only need to fire a property change event if the controls are
        // marked inSync with the model
        if (inSync) {
            inSync = false;
            logger.log(Level.FINER, "Key Event from {0}", keyEvent.getSource());
            this.propChangeSupport.firePropertyChange(PROP_EVENTEDITOR_MODIFIED, keyEvent, null);
        }
    }

    @FXML
    private void handleDateAction(ActionEvent ae) {
        logger.log(Level.INFO, "Action Event from {0}", ae.getSource());
        try {
            tempdate =
                    DateFormat.getDateInstance().parse(dateField.getText());

            dateField.setText(format.format(tempdate));
            if (inSync) {
                inSync = false;
                this.propChangeSupport.firePropertyChange(PROP_EVENTEDITOR_MODIFIED, ae, null);
            }
        } catch (ParseException exception) {
            this.dateField.setText(format.format(this.event.getEventDate()));
            tempdate = this.event.getEventDate();
        }
    }
    BooleanBinding marriageBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get() instanceof Marriage;
        }
    };
    BooleanBinding divorceBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get() instanceof Divorce;
        }
    };
    BooleanBinding adoptBirthBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get() instanceof ParentChildEvent;
        }
    };
    BooleanBinding childParentBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get() instanceof ChildParentEvent;
        }
    };
    BooleanBinding deathBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get() instanceof Death;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        propChangeSupport = new SwingPropertyChangeSupport(this, true);
        Light.Distant myLight = new Light.Distant();
        myLight.setAzimuth(225);
        lighting = new Lighting();
        lighting.setLight(myLight);
        lighting.setSurfaceScale(5);
        eventName.setEffect(lighting);

        // ImageView properties are set in fxml 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setRadius(10.0);
        imageView.setEffect(dropShadow);
        personLabel1Name.visibleProperty().bind(deathBinding.not());
        personLabel2Name.visibleProperty().bind(adoptBirthBinding);
        personLabel1Name.textProperty().bind(new When(marriageBinding).then("Spouse")
                .otherwise(new When(divorceBinding).then("Ex")
                .otherwise(new When(childParentBinding).then("Child").otherwise("Parent"))));
        personLabel2Name.setText("Parent");
        relationship1Name.visibleProperty().bind(deathBinding.not());
        relationship2Name.visibleProperty().bind(adoptBirthBinding);
    }

    // Called from the TopComponent to initialize/update the JavaFX controls
    // Must be called from the JavaFX Application thread
    public void updateForm(Event event) {
        this.event = event;
        eventProperty.set(event);
        idNum.setText(event.getId().toString());
        eventName.setText(event.getEventName());
        tempdate = event.getEventDate();
        dateField.setText(format.format(tempdate));
        personName.setText(event.getPerson().toString());
        townField.setText(event.getTown());
        stateField.setText(event.getState_province());
        countryField.setText(event.getCountry());
        if (event instanceof Marriage) {
            Marriage m = (Marriage) event;
            relationship1Name.setText(m.getSpouse().toString());
        } else if (event instanceof Divorce) {
            Divorce d = (Divorce) event;
            relationship1Name.setText(d.getEx().toString());
        } else if (event instanceof ParentChildEvent) {
            ParentChildEvent pce = (ParentChildEvent) event;
            relationship1Name.setText("");
            relationship2Name.setText("");

            Iterator<Person> iterator = pce.getParents().iterator();
            if (iterator.hasNext()) {
                relationship1Name.setText(iterator.next().toString());
                if (iterator.hasNext()) {
                    relationship2Name.setText(iterator.next().toString());
                }
            }
        } else if (event instanceof ChildParentEvent) {
            ChildParentEvent cpe = (ChildParentEvent) event;
            relationship1Name.setText(cpe.getChild().toString());
        }
        inSync = true;
    }

    // Called from the TopComponent to update Event from
    // the JavaFX controls
    // Must be called from the JavaFX Application thread
    public Event updateEvent(Event event) {
        event.setEventDate(tempdate);
        event.setTown(townField.getText());
        event.setState_province(stateField.getText());
        event.setCountry(countryField.getText());
        return event;
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.removePropertyChangeListener(listener);
    }
}
