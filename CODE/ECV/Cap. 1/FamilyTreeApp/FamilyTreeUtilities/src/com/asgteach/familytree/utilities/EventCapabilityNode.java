/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.utilities;

import com.asgteach.familytree.capabilityinterfaces.HappyMammothCapability;
import com.asgteach.familytree.capabilityinterfaces.ReloadableViewCapability;
import com.asgteach.familytree.editorinterfaces.EventEditor;
import com.asgteach.familytree.editorinterfaces.EventEditorManager;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.FamilyTreeManager;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.api.actions.Openable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.actions.DeleteAction;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author gail
 */
@Messages({
    "CTL_HappyMammothTitle=Happy Mammoth Action"
})
public class EventCapabilityNode extends BeanNode<Event> {

    protected InstanceContent instanceContent;
    protected FamilyTreeManager ftm;
    private static final Logger logger = Logger.getLogger(EventCapabilityNode.class.getName());

    public EventCapabilityNode(
            Event event,
            Children children) throws IntrospectionException {
        this(event, new InstanceContent(), children);
    }

    private EventCapabilityNode(
            final Event event,
            InstanceContent ic, final Children children) throws IntrospectionException {
        super(event, children, new AbstractLookup(ic));

        this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                switch (pce.getPropertyName()) {
                    case FamilyTreeManager.PROP_EVENT_DESTROYED:
                        {
                            Event event = getLookup().lookup(Event.class);
                            if (pce.getOldValue() != null
                                    && Objects.equals(((Event) pce.getOldValue()).getId(), event.getId())) {
                                logger.log(Level.FINER,
                                        "Event destroyed from FTM for event {0}", event);
                                ftm.removePropertyChangeListener(this);
                            }       break;
                        }
                    case FamilyTreeManager.PROP_EVENT_UPDATED:
                    {
                        Event event = getLookup().lookup(Event.class);
                        if (pce.getOldValue() != null
                                && Objects.equals(((Event) pce.getOldValue()).getId(), event.getId())) {
                            
                            logger.log(Level.FINER,
                                    "Event updated {0}", event);
                            // update from OldValue
                            Event changedEvent = ((Event) pce.getOldValue());
                            event.setCountry(changedEvent.getCountry());
                            event.setEventDate(changedEvent.getEventDate());
                            event.setState_province(changedEvent.getState_province());
                            event.setTown(changedEvent.getTown());
                            WindowManager.getDefault().invokeWhenUIReady(() -> {
                                // update the node display name
                                setDisplayName(getHtmlDisplayName());
                            });

                    }       break;
                        }
                }
            }
        });

        this.instanceContent = ic;
        this.setIconBaseWithExtension("com/asgteach/familytree/utilities/eventIcon.png");
        this.setDisplayName(event.toString());
        // ic.add(personCapability);
        ic.add(event);
        ic.add(new EventCapability());
        ic.add((HappyMammothCapability) () -> {
            HappyMammothPanel hmp = new HappyMammothPanel();
            hmp.setText("<html>Happy Mammoth applied to event<p/>"
                    + event + "</html>");
            DialogDescriptor dd = new DialogDescriptor(hmp,
                    Bundle.CTL_HappyMammothTitle());
            DialogDisplayer.getDefault().notify(dd);  // display & block
        });
        ic.add((ReloadableViewCapability) () -> {
            logger.log(Level.FINER, "reloadChildren for event {0}", event);
            setChildren(children);
        });
        // add an "Openable" capability
        ic.add((Openable) () -> {
            logger.log(Level.FINER, "Openable: Open event {0}", event);
            
            EventEditorManager eem = Lookup.getDefault().lookup(EventEditorManager.class);
            if (eem == null) {
                logger.log(Level.WARNING, "Cannot get EventEditorManager object");
                return;
            }
            EventEditor eventEditor = eem.getEditorForEvent(event);
            if (eventEditor == null) {
                logger.log(Level.WARNING, "Cannot get EventEditor");
                return;
            }
            eventEditor.setNode(EventCapabilityNode.this);
            TopComponent tc = eventEditor.getTopComponent();
            if (tc != null) {
                if (!tc.isOpened()) {
                    tc.open();
                }
                tc.requestActive();
            }
        });
    }

    @Override
    public String getHtmlDisplayName() {
        Event event = getLookup().lookup(Event.class);
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("E MMM dd, yyyy");
        sb.append(event.getEventName()).append(": <i>")
                .append(format.format(event.getEventDate())).append("</i>");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> eventActions = (List<Action>) Utilities.actionsForPath("Actions/OpenNode");
        eventActions.add(null);
        eventActions.addAll((List<Action>) Utilities.actionsForPath("Actions/ReloadNode"));
        eventActions.add(null);
        eventActions.addAll((List<Action>) Utilities.actionsForPath("Actions/Extra"));
        eventActions.add(null);
        eventActions.add(SystemAction.get(DeleteAction.class));
        return eventActions.toArray(new Action[eventActions.size()]);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action getPreferredAction() {
        List<Action> eventActions = (List<Action>) Utilities.actionsForPath("Actions/OpenNode");
        if (!eventActions.isEmpty()) {
            // Make 'Open' the default action
            return eventActions.get(0);
        } else {
            return super.getPreferredAction();
        }
    }
}
