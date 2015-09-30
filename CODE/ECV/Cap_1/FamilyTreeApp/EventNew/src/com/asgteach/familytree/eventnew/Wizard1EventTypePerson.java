/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventnew;

import com.asgteach.familytree.capabilityinterfaces.CheckEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.EventCapability;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle.Messages;

public class Wizard1EventTypePerson implements
        WizardDescriptor.AsynchronousValidatingPanel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private Visual1FXController component = null;
    private WizardDescriptor model = null;
    private Event event;
    private Person person;
    private final EventCapability eventCapability = new EventCapability();
    private static final Logger logger = Logger.getLogger(Wizard1EventTypePerson.class.getName());

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Visual1FXController getComponent() {
        if (component == null) {
            // The FXML Loader creates Visual1FXController
            final CountDownLatch latch = new CountDownLatch(1);
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                try {
                    createScene();
                } finally {
                    latch.countDown();
                }
            });
            try {
                latch.await();
            } catch (InterruptedException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return component;
    }

    private void createScene() {
        // Do this on the FX Application Thread
        try {
            URL location = getClass().getResource("Visual1FX.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            component = (Visual1FXController) fxmlLoader.getController();
            // since Visual1FXController is a JFXPanel, we can do this
            component.setScene(scene);
            String css = this.getClass().getResource("eventfxStyleSheet.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    private void setMessage(String message) {
        model.getNotificationLineSupport().setInformationMessage(message);
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        this.model = wiz;
        getComponent().addPropertyChangeListener(this);
        event = (Event) model.getProperty(Visual1FXController.PROP_EVENT);
        if (event != null) {
            getComponent().setEventDisplay(event);
        }
        // grab the person from the properties store and pass to visual component
        person = (Person) model.getProperty(Visual1FXController.PROP_PERSON);
        if (person != null) {
            getComponent().setPerson(person);
        }       
        logger.log(Level.FINE, "readSettings: person={0}", person);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        getComponent().removePropertyChangeListener(this);
    }

    @Override
    public void prepareValidation() {
        // Get the most up-to-date event
        event = (Event) model.getProperty(Visual1FXController.PROP_EVENT);
    }

    @Messages({
        "# {0} - Birth or Adopt",
        "# {1} - String(targetperson)",
        "CTL_Panel1_DuplicateRecordErr=A {0} record already exists for {1}",
        "# {0} - String(dupPerson)",
        "# {1} - String(dupEvent)",
        "CTL_Panel1_DuplicateRecordErrDetail=Person = {0}, Event = {1}",
        "CTL_Panel1_NoPeople=Persons Database is empty, you must create Person data first"
    })
    @Override
    public void validate() throws WizardValidationException {
        // perform validation and throw WizardValidationException if there
        // is a problem

        // Make sure person is not null, which mean the person database
        // is empty
        if (person == null) {
            throw new WizardValidationException(null,
                    Bundle.CTL_Panel1_NoPeople(),
                    Bundle.CTL_Panel1_NoPeople());
        }

        // Determine if there already is a Birth or Adoption record for person
        // if so display error and throw exception

        CheckEventCapability cec =
                eventCapability.getLookup().lookup(CheckEventCapability.class);
        try {
            cec.checkEvent(event);
        } catch (Exception e) {
            logger.log(Level.WARNING, null, e);
        }
        if (eventCapability.isDuplicateEvent()) {
            throw new WizardValidationException(null,
                    Bundle.CTL_Panel1_DuplicateRecordErrDetail(person.toString(),
                    event.toString()),
                    Bundle.CTL_Panel1_DuplicateRecordErr(event.getEventName(),
                    person.toString()));
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(Visual1FXController.PROP_EVENTTYPE_INFO)) {
            if (pce.getNewValue() instanceof EventInfo) {
                EventInfo eventInfo = (EventInfo) pce.getNewValue();
                event = eventInfo.getEvent();
                model.putProperty(Visual1FXController.PROP_EVENT, event);
            }
        }
    }
}
