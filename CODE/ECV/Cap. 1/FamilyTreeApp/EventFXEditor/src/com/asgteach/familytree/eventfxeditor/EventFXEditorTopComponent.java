/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventfxeditor;

import com.asgteach.familytree.capabilityinterfaces.SavableEventCapability;
import com.asgteach.familytree.editorinterfaces.EventEditor;
import com.asgteach.familytree.editorinterfaces.EventEditorManager;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.EventCapability;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.SwingWorker;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familytree.eventfxeditor//EventFXEditor//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "EventFXEditorTopComponent",
        iconBase = "com/asgteach/familytree/utilities/eventIcon.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EventFXEditorAction",
        preferredID = "EventFXEditorTopComponent")
@Messages({
    "CTL_EventFXEditorAction=EventFXEditor",
    "CTL_EventFXEditorTopComponent=EventFXEditor Window",
    "HINT_EventFXEditorTopComponent=This is a EventFXEditor window",
    "CTL_EventFXEditorSaveDialogTitle=Unsaved Data",
    "# {0} - event",
    "CTL_EventFXEditorSaveDialogMsg=Event {0} has Unsaved Data. \nSave Event?"
})
@ServiceProvider(service = EventEditor.class, 
        supersedes = "com.asgteach.familytree.eventeditor.EventEditorTopComponent.class")
public final class EventFXEditorTopComponent extends TopComponent implements
        EventEditor, PropertyChangeListener {

    private static JFXPanel fxPanel;
    private EventFXEditorController controller;
    private FamilyTreeManager ftm;
    private EventCapability eventCapability;
    private Event event;
    private boolean noUpdate;
    private boolean readyToListen = false;
    private Node eventNode;
    InstanceContent ic = new InstanceContent();
    private static final Logger logger = Logger.getLogger(EventFXEditorTopComponent.class.getName());

    public EventFXEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_EventFXEditorTopComponent());
        setToolTipText(Bundle.HINT_EventFXEditorTopComponent());

        associateLookup(new AbstractLookup(ic));
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(this::createScene);
    }

    private void createScene() {
        try {
            URL location = getClass().getResource("EventFXEditor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            String cssURL = "eventfxEditorStyleSheet.css";
            String css = this.getClass().getResource(cssURL).toExternalForm();
            scene.getStylesheets().add(css);
            controller = (EventFXEditorController) fxmlLoader.getController();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        setName(event.getEventName() + ": " + event.getPerson());
        this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener(this);
        controller.addPropertyChangeListener(this);
    }

    @Override
    public void componentClosed() {
        ftm.removePropertyChangeListener(this);
        controller.removePropertyChangeListener(this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private void updateForm() {
        logger.log(Level.FINER, "updateForm");

        this.readyToListen = false;
        this.noUpdate = true;

        // make sure the tab name is current
        setName(event.getEventName() + ": " + event.getPerson());
        // Invoke a method in the controller using the JavaFX
        // Application thread and wait
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Update the form in the controller
            // Must be in JavaFX Application Thread
            try {
                controller.updateForm(event);
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
            // when done do this:
            this.noUpdate = false;
            this.readyToListen = true;
        } catch (InterruptedException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }

    private void updateEvent() {
        if (this.noUpdate) {
            return;
        }
        // Invoke a method in the controller in the JavaFX Application
        // thread to read the controls and update event
        // wait for it to finish
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Update the form in the controller
            // Must be in JavaFX Application Thread
            try {
                event = controller.updateEvent(event);
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

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case FamilyTreeManager.PROP_PERSON_UPDATED:
                if (pce.getOldValue() != null
                        && Objects.equals(((Person) pce.getOldValue()).getId(), event.getPerson().getId())) {
                    event.setPerson((Person) pce.getOldValue());
                    WindowManager.getDefault().invokeWhenUIReady(() -> {
                        setName(event.getEventName() + ": " + event.getPerson());
                        updateForm();
                    });
                }   break;
            case FamilyTreeManager.PROP_EVENT_DESTROYED:
                if (pce.getOldValue() != null
                        && Objects.equals(((Event) pce.getOldValue()).getId(), event.getId())) {
                    logger.log(Level.FINER, "detected Event destroyed for {0}", event);
                    clearSaveCapability();
                    EventEditorManager eem = Lookup.getDefault().lookup(EventEditorManager.class);
                    if (eem != null) {
                        eem.unregisterEditor(event);
                        EventFXEditorTopComponent.shutdown(this);
                    }
            }   break;
            case FamilyTreeManager.PROP_EVENT_UPDATED:
                if (pce.getOldValue() != null
                        && Objects.equals(((Event) pce.getOldValue()).getId(), event.getId())) {
                    logger.log(Level.FINER, "updated event {0}", event);
                    // update from oldValue
                    this.readyToListen = false;
                    this.noUpdate = true;
                    Event changedEvent = ((Event) pce.getOldValue());
                    event.setCountry(changedEvent.getCountry());
                    event.setEventDate(changedEvent.getEventDate());
                    event.setState_province(changedEvent.getState_province());
                    event.setTown(changedEvent.getTown());
                updateForm();
                }   break;
            case EventFXEditorController.PROP_EVENTEDITOR_MODIFIED:
                if (readyToListen) {
                modify();
            }   break;
        }
    }

    private void modify() {
        if (getLookup().lookup(SavableViewCapability.class) == null) {
            ic.add(new SavableViewCapability());
        }
    }

    @Override
    public boolean canClose() {
        SavableViewCapability savable = getLookup().lookup(SavableViewCapability.class);
        if (savable == null) {
            return true;
        }
        String msg = Bundle.CTL_EventFXEditorSaveDialogMsg(event.getEventName());
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation(msg, NotifyDescriptor.YES_NO_CANCEL_OPTION);
        nd.setTitle(Bundle.CTL_EventFXEditorSaveDialogTitle());
        Object result = DialogDisplayer.getDefault().notify(nd);
        if (result == NotifyDescriptor.CANCEL_OPTION || result == NotifyDescriptor.CLOSED_OPTION) {
            return false;
        }
        if (result == NotifyDescriptor.NO_OPTION) {
            clearSaveCapability();
            return true;
        }
        try {
            clearSaveCapability();
            savable.handleSave();
            return true;
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
            return false;
        }
    }

    private void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public void setNode(Node node) {
        this.eventNode = node;
        eventCapability = eventNode.getLookup().lookup(EventCapability.class);
        setEvent(eventNode.getLookup().lookup(Event.class));
        // Add eventNode to the lookup
        ic.add(eventNode);
        logger.log(Level.FINER, "Editing Event {0}", event);
        updateForm();
    }

    @Override
    public TopComponent getTopComponent() {
        return EventFXEditorTopComponent.this;
    }

    private void clearSaveCapability() {
        SavableViewCapability svc = getLookup().lookup(SavableViewCapability.class);
        while (svc != null) {
            svc.removeSavable();
            this.ic.remove(svc);
            svc = this.getLookup().lookup(SavableViewCapability.class);
        }

    }

    private class SavableViewCapability extends AbstractSavable {

        SavableViewCapability() {
            register();
        }

        public void removeSavable() {
            unregister();
        }

        @Override
        protected String findDisplayName() {
            return event.getEventName() + ": " + event.getPerson().toString();
        }

        @Override
        protected void handleSave() throws IOException {
            final SavableEventCapability saveEvent =
                    eventCapability.getLookup().lookup(SavableEventCapability.class);
            tc().ic.remove(this);
            updateEvent();

            SwingWorker<Event, Void> worker = new SwingWorker<Event, Void>() {
                @Override
                public Event doInBackground() {
                    try {
                        saveEvent.save(event);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, null, e);
                    }
                    return event;
                }

                @Override
                public void done() {
                    updateForm();
                }
            };
            worker.execute();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof SavableViewCapability) {
                SavableViewCapability sv = (SavableViewCapability) other;
                return tc() == sv.tc();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }

        EventFXEditorTopComponent tc() {
            return EventFXEditorTopComponent.this;
        }
    }

    public static void shutdown(final TopComponent tc) {
        WindowManager.getDefault().invokeWhenUIReady(tc::close);
    }
}