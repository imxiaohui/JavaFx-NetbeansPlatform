/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventnew;

import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class Wizard2DatePlace implements
        WizardDescriptor.Panel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private Visual2FXController component;
    private WizardDescriptor model = null;
    private boolean isValid = false;
    private static final Logger logger = Logger.getLogger(Wizard2DatePlace.class.getName());

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Visual2FXController getComponent() {
        if (component == null) {
            // The FXML Loader creates Visual2FXController
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
            URL location = getClass().getResource("Visual2FX.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            component = (Visual2FXController) fxmlLoader.getController();
            // since Visual2FXController is a JFXPanel, we can do this
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
        return isValid;

        // Make sure that the date is well-formed
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    private void setMessage(String message) {
        model.getNotificationLineSupport().setErrorMessage(" " + message);
    }
    private final EventListenerList listeners =
            new EventListenerList();

    @Override
    public void addChangeListener(ChangeListener l) {
        listeners.add(ChangeListener.class, l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(ChangeListener.class, l);
    }

    protected final void fireChangeEvent(
            Object source, boolean oldState, boolean newState) {
        ChangeEvent ev = new ChangeEvent(source);
        for (ChangeListener listener : listeners.getListeners(ChangeListener.class)) {
            listener.stateChanged(ev);
        }
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        this.model = wiz;
        getComponent().addPropertyChangeListener(this);
        getComponent().setEventDisplay((Event) model.getProperty(Visual1FXController.PROP_EVENT), true);
        getComponent().setPerson((Person) model.getProperty(Visual1FXController.PROP_PERSON));
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        getComponent().removePropertyChangeListener(this);
    }

    private void updateEvent(EventInfo eventInfo) {
        Event event = (Event) model.getProperty(Visual1FXController.PROP_EVENT);
        if (isValid) {
            logger.log(Level.FINER, "updateEvent: date is valid (event={0})", event);
            event = eventInfo.getEvent();
        }
        model.putProperty(Visual1FXController.PROP_EVENT, event);
        logger.log(Level.FINE, "updateEvent: event={0}", event);
        getComponent().setEventDisplay(event, false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case Visual2FXController.PROP_EVENT_DATE:
                if (pce.getNewValue() instanceof EventInfo) {
                    boolean oldState = isValid;
                    EventInfo eventInfo = (EventInfo) pce.getNewValue();
                    isValid = eventInfo.isIsValid();
                    setMessage(eventInfo.getMessage());
                    fireChangeEvent(this, oldState, isValid);
                    updateEvent(eventInfo);
                }   break;
            case Visual2FXController.PROP_LOCATION_INFO:
                if (pce.getNewValue() instanceof EventInfo) {
                    EventInfo eventInfo = (EventInfo) pce.getNewValue();
                    updateEvent(eventInfo);
            }   break;
        }
    }
}
