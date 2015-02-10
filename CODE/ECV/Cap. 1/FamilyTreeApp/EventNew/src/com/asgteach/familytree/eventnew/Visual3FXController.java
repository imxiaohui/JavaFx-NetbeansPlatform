/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventnew;

import com.asgteach.familytree.capabilityinterfaces.ReloadablePersonCapability;
import com.asgteach.familytree.model.Birth;
import com.asgteach.familytree.model.Divorce;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Marriage;
import com.asgteach.familytree.model.ParentChildEvent;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.PersonCapability;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.swing.SwingUtilities;
import org.openide.util.NbBundle.Messages;

public final class Visual3FXController extends JFXPanel implements Initializable {
    // Note: because this class extends JFXPanel, methods firePropertyChange()
    // and addPropertyChangeListener() are available for property change support.

    // FX Controls
    @FXML
    private Label personLabel1;
    @FXML
    private Label personLabel2;
    @FXML
    private ComboBox<Person> comboBox1;
    @FXML
    private ComboBox<Person> comboBox2;
    @FXML
    private TextArea eventDisplay;
    @FXML
    private Label personLabel;
    @FXML
    private Label eventName;
    private boolean isValid = false;
    private String message = "";
    public static final String PROP_EVENT_INFO = "eventInfo";
    private static final Logger logger = Logger.getLogger(Visual3FXController.class.getName());
    private static final String UNKNOWN = "Unknown";
    private PersonCapability personCapability = new PersonCapability();
    private ObservableList<Person> allPeople = FXCollections.observableArrayList();
    private Person person;
    private ObjectProperty<Event> eventProperty = 
            new SimpleObjectProperty<>(this, "event", new Birth(new Long(-5)));


    @Messages({
        "CTL_Panel3Name=Set Relationships and Review",
        "# {0} - String(SpousePerson)",
        "CTL_Panel3_UnknownSpouseErr=Please specify a Spouse for {0}",
        "# {0} - String(ExPerson)",
        "CTL_Panel3_UnknownExErr=Please specify an Ex for {0}",
        "# {0} - String(NewPerson)",
        "# {1} - String(OldPerson)",
        "# {2} - String Eventname",
        "CTL_Panel3_InfoEventUpdate={0} added to {1}s {2} Record"
    })
    // Safe to call from Wizard
    @Override
    public String getName() {
        return Bundle.CTL_Panel3Name();
    }

    private ComboBox<Person> getOppositeCombo(Person newValue) {
        if (comboBox1.getSelectionModel().getSelectedItem().equals(newValue)) {
            return comboBox2;
        }
        if (comboBox2.getSelectionModel().getSelectedItem().equals(newValue)) {
            return comboBox1;
        }
        return null;
    }

    private void addItem(Person p, ComboBox<Person> c) {
        // When you add an item to the selecteItems list,
        // the selection can change, so we save it to prevent this
        Person p1 = c.getSelectionModel().getSelectedItem();
        if (!c.getItems().contains(p)) {
            c.getItems().add(p);
        }
        c.getSelectionModel().select(p1);
    }

    // This method keeps the combobox controls current by removing the selected person
    // from the "other" control and adding back in the old value to the "other" control
    // This method cannot be invoked from the combobox change listeners and you
    // must remove the change listeners before making updates to the items/selections.
    private void updateControls(Person oldValue, Person newValue) {
        logger.log(Level.INFO, "updateControls: newValue =  {0}", newValue);
        logger.log(Level.INFO, "updateControls: oldValue =  {0}", oldValue);
        if (oldValue == null || newValue == null) {
            return;
        }
        // temporarily remove the change listeners and update the controls
        if (eventProperty.get() instanceof ParentChildEvent) {
            comboBox1.getSelectionModel().selectedItemProperty().removeListener(combo1ChangeListener);
            comboBox2.getSelectionModel().selectedItemProperty().removeListener(combo2ChangeListener);
            if (!newValue.getLastname().equals(UNKNOWN)) {
                ComboBox<Person> c = getOppositeCombo(newValue);
                if (c != null) {
                    Person p1 = c.getSelectionModel().getSelectedItem();
                    c.getItems().remove(newValue);
                    c.getSelectionModel().select(p1);
                }
            }
            if (!oldValue.getLastname().equals(UNKNOWN)) {
                if (comboBox1.getSelectionModel().getSelectedItem().getLastname().equals(UNKNOWN)
                        && comboBox2.getSelectionModel().getSelectedItem().getLastname().equals(UNKNOWN)) {
                    addItem(oldValue, comboBox1);
                    addItem(oldValue, comboBox2);
                } else {
                    ComboBox<Person> c = getOppositeCombo(newValue);
                    if (c != null) {
                        addItem(oldValue, c);
                    }
                }
            }
            comboBox1.getSelectionModel().selectedItemProperty().addListener(combo1ChangeListener);
            comboBox2.getSelectionModel().selectedItemProperty().addListener(combo2ChangeListener);
        }
    }

    private void resetControls() {
        comboBox1.getSelectionModel().selectedItemProperty().removeListener(combo1ChangeListener);
        comboBox2.getSelectionModel().selectedItemProperty().removeListener(combo2ChangeListener);
        comboBox1.setItems(FXCollections.observableArrayList(allPeople));
        comboBox2.setItems(FXCollections.observableArrayList(allPeople));
        comboBox1.getItems().remove(person);
        comboBox2.getItems().remove(person);
        comboBox1.getSelectionModel().selectFirst();
        comboBox2.getSelectionModel().selectFirst();
        isValid = true;
        message = "";
        if (eventProperty.get() instanceof ParentChildEvent) {
            ParentChildEvent event = (ParentChildEvent) eventProperty.get();
            logger.log(Level.FINE, "resetControls: parents =  {0}", event.getParents());
            Iterator<Person> iterator = event.getParents().iterator();
            if (iterator.hasNext()) {
                Person p1 = iterator.next();
                logger.log(Level.FINER, "resetControls: Selecting combo1 =  {0}", p1);
                comboBox1.getSelectionModel().select(p1);
                System.out.println("combo1 selection = " + comboBox1.getSelectionModel().getSelectedItem());
                comboBox2.getItems().remove(p1);
                if (iterator.hasNext()) {
                    Person p2 = iterator.next();
                    logger.log(Level.FINER, "resetControls: Selecting combo2 =  {0}", p2);
                    comboBox2.getSelectionModel().select(p2);
                    System.out.println("combo2 selection = " + comboBox2.getSelectionModel().getSelectedItem());
                    comboBox1.getItems().remove(p2);
                    // removing an item messes up the current selection
                    // so re-select it
                    comboBox1.getSelectionModel().select(p1);
                }
            }
        } else if (eventProperty.get() instanceof Marriage) {
            Marriage event = (Marriage) eventProperty.get();
            logger.log(Level.FINER, "resetControls: event =  {0}", event);
            Person p = event.getSpouse();
            if (p != null) {
                comboBox1.getSelectionModel().select(p);
            } else {
                isValid = false;
                message = Bundle.CTL_Panel3_UnknownSpouseErr(person);
            }
        } else if (eventProperty.get() instanceof Divorce) {
            Divorce event = (Divorce) eventProperty.get();
            logger.log(Level.FINER, "resetControls: event =  {0}", event);
            Person p = event.getEx();
            if (p != null) {
                comboBox1.getSelectionModel().select(p);
            } else {
                isValid = false;
                message = Bundle.CTL_Panel3_UnknownExErr(person);
            }
        }
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setIsValid(isValid);
        eventInfo.setEvent(eventProperty.get());
        eventInfo.setMessage(message);
        eventInfo.setOldValue(null);
        eventInfo.setNewValue(null);
        comboBox1.getSelectionModel().selectedItemProperty().addListener(combo1ChangeListener);
        comboBox2.getSelectionModel().selectedItemProperty().addListener(combo2ChangeListener);
        SwingUtilities.invokeLater(() -> {
            // force the wizard to check validity
            firePropertyChange(PROP_EVENT_INFO, null, eventInfo);
        });
    }

    public void setEventDisplay(final Event event, final boolean includeControls,
            final Person oldValue, final Person newValue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                eventDisplay.setText(event.toString());
                eventProperty.set(event);
                logger.log(Level.INFO, "setEventDisplay: event =  {0}", event);

                if (includeControls) {
                    eventName.setText(event.getEventName());
                    resetControls();
                } else {
                    updateControls(oldValue, newValue);
                }
            }
        });
    }

    public void setPerson(final Person p) {
        Platform.runLater(() -> {
            person = p;
            personLabel.setText(person.toString());
        });
    }
    BooleanBinding marriageBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get().getEventName().contentEquals("Marriage");
        }
    };
    BooleanBinding divorceBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get().getEventName().contentEquals("Divorce");
        }
    };
    BooleanBinding adoptBirthBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get().getEventName().contentEquals("Adoption")
                    || eventProperty.get().getEventName().equals("Birth");
        }
    };
    BooleanBinding deathBinding = new BooleanBinding() {
        {
            super.bind(eventProperty);
        }

        @Override
        protected boolean computeValue() {
            return eventProperty.get().getEventName().contentEquals("Death");
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox1.visibleProperty().bind(deathBinding.not());
        comboBox2.visibleProperty().bind(adoptBirthBinding);
        personLabel1.visibleProperty().bind(deathBinding.not());
        personLabel2.visibleProperty().bind(adoptBirthBinding);
        personLabel1.textProperty().bind(new When(marriageBinding).then("Spouse")
                .otherwise(new When(divorceBinding).then("Ex").otherwise("Parent")));
        personLabel2.setText("Parent");
        ReloadablePersonCapability r =
                personCapability.getLookup().lookup(ReloadablePersonCapability.class);
        Person unknown = new Person(new Long(-5));
        unknown.setFirstname("");
        unknown.setLastname(UNKNOWN);
        allPeople.add(unknown);
        try {
            r.reload();
            allPeople.addAll(personCapability.getPersons());
            logger.log(Level.FINE, "initialize: setting up allPeople for comboboxes {0}", allPeople);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
    private ChangeListener<Person> combo1ChangeListener = (ObservableValue<? extends Person> ov, final Person oldValue, final Person newValue) -> {
        logger.log(Level.INFO, "Selected combo1: {0}", newValue);
        if (newValue == null || oldValue == null) {
            return;
        }
        isValid = true;
        message = "";
        if (eventProperty.get() instanceof ParentChildEvent) {
            ParentChildEvent event = (ParentChildEvent) eventProperty.get();
            logger.log(Level.INFO, "parents = {0}", event.getParents());
            replacePerson(oldValue, newValue, event);
        } else if (eventProperty.get() instanceof Marriage) {
            Marriage event = (Marriage) eventProperty.get();
            if (!newValue.getLastname().equals(UNKNOWN)) {
                event.setSpouse(newValue);
            } else {
                isValid = false;
                message = Bundle.CTL_Panel3_UnknownSpouseErr(person);
                event.setSpouse(null);
            }
        } else if (eventProperty.get() instanceof Divorce) {
            Divorce event = (Divorce) eventProperty.get();
            if (!newValue.getLastname().equals(UNKNOWN)) {
                event.setEx(newValue);
            } else {
                isValid = false;
                message = Bundle.CTL_Panel3_UnknownExErr(person);
                event.setEx(null);
            }
        }
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setIsValid(isValid);
        eventInfo.setEvent(eventProperty.get());
        eventInfo.setMessage(message);
        eventInfo.setOldValue(oldValue);
        eventInfo.setNewValue(newValue);
        SwingUtilities.invokeLater(() -> {
            // force the wizard to check validity
            firePropertyChange(PROP_EVENT_INFO, null, eventInfo);
        });
    };
    private final ChangeListener<Person> combo2ChangeListener = (ObservableValue<? extends Person> ov, final Person oldValue, final Person newValue) -> {
        logger.log(Level.INFO, "Selected combo2: {0}", newValue);
        if (newValue == null || oldValue == null) {
            return;
        }
        isValid = true;
        message = "";
        if (eventProperty.get() instanceof ParentChildEvent) {
            ParentChildEvent event = (ParentChildEvent) eventProperty.get();
            logger.log(Level.INFO, "parents = {0}", event.getParents());
            replacePerson(oldValue, newValue, event);
        }
        final EventInfo eventInfo = new EventInfo();
        eventInfo.setIsValid(isValid);
        eventInfo.setEvent(eventProperty.get());
        eventInfo.setMessage(message);
        eventInfo.setOldValue(oldValue);
        eventInfo.setNewValue(newValue);
        SwingUtilities.invokeLater(() -> {
            // force the wizard to check validity
            firePropertyChange(PROP_EVENT_INFO, null, eventInfo);
        });
    } // "In general is is considered bad practice to modify the observed value in this method."
    // This means we can't add/remove items or change the selection property
    ;

    private void replacePerson(Person oldPerson, Person newPerson, ParentChildEvent event) {
        // add newPerson to parents and remove OldPerson from parents
        logger.log(Level.INFO, "replacePerson: old person = {0}", oldPerson);
        logger.log(Level.INFO, "replacePerson: new person = {0}", newPerson);

        if (!newPerson.getLastname().equals(UNKNOWN)) {
            event.addParent(newPerson);
        }
        // Is this replacing a previously selected Person?
        if (!oldPerson.getLastname().equals(UNKNOWN)) {
            event.removeParent(oldPerson);
        }
        logger.log(Level.INFO, "replacePerson: now parents = {0}", event.getParents());
    }
}
