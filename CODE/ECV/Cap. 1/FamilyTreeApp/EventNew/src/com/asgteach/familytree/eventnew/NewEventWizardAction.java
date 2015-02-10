/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventnew;

import com.asgteach.familytree.capabilityinterfaces.CreatableEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.EventCapability;
import com.asgteach.familytree.utilities.PersonCapabilityNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "PersonNode",
id = "org.asgteach.familytree.eventnew.NewEventWizardAction")
@ActionRegistration(iconBase = "com/asgteach/familytree/eventnew/eventIcon.png",
displayName = "#CTL_NewEventWizardAction")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0, separatorAfter = 50),
    @ActionReference(path = "Toolbars/File", position = -90)
})
@Messages({
    "CTL_NewEventWizardAction=New &Event",
    "# {0} - person", "CTL_NewEventDialogTitle=Create New Event for {0}"})
public final class NewEventWizardAction implements ActionListener {

    private final PersonCapabilityNode context;
    private static final Logger logger = Logger.getLogger(NewEventWizardAction.class.getName());

    public NewEventWizardAction(PersonCapabilityNode context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // extract the person from the context and use for this new event
        Person person = context.getLookup().lookup(Person.class);
        if (person == null) {
            return;
        }
        WizardDescriptor wiz = new WizardDescriptor(new NewEventWizardIterator());
        // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
        // {1} will be replaced by WizardDescriptor.Iterator.name()
        wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
        wiz.setTitle(Bundle.CTL_NewEventDialogTitle(person));
        // Store the person in the Wizard Properties store
        wiz.putProperty(Visual1FXController.PROP_PERSON, person);
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {

            final Event event = (Event) wiz.getProperty(Visual1FXController.PROP_EVENT);
            logger.log(Level.FINER, "New Event {0}", event);

            // Use EventCapability to persist the event
            final EventCapability eventCapability = new EventCapability();
            final CreatableEventCapability cec =
                    eventCapability.getLookup().lookup(CreatableEventCapability.class);

            SwingWorker<Event, Void> worker = new SwingWorker<Event, Void>() {
                @Override
                public Event doInBackground() {
                    try {
                        cec.create(event);
                        logger.log(Level.FINER, "Event {0} created", event);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, null, e);
                    }
                    return null;
                }
            };                
            worker.execute();
        }
    }
}
