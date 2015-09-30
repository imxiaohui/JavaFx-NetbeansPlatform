/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventsviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadableEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.EventCapability;
import java.awt.EventQueue;
import java.beans.IntrospectionException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class EventFactory extends ChildFactory<Event>  {

    // Create the event keys for this Person
    // Then create the EventNode for an Event
    private final Person person;
    private final EventCapability eventCapability = new EventCapability();
    private static final Logger logger = Logger.getLogger(EventFactory.class.getName());

    public EventFactory(Person person) {
        this.person = person;
    }

    @Override
    protected boolean createKeys(List<Event> toPopulate) {
        if (!EventQueue.isDispatchThread()) {
            logger.log(Level.FINER, "Not IN EDT");
        }
        // get this capability from the lookup
        ReloadableEventCapability r = eventCapability.getLookup().
                lookup(ReloadableEventCapability.class);
        // and use the capability
        if (r != null) {
            try {
                logger.log(Level.FINER, "createKeys reload for {0}", person);
                r.reload(person);
            } catch (Exception e) {
                logger.log(Level.WARNING, null, e);
            }
        }
        // Now populate the list of child entities
        toPopulate.addAll(eventCapability.getEvents());
        // and return true since we're all set
        return true;
    }

    @Override
    protected Node createNodeForKey(Event key) {
        try {
            EventNode en = new EventNode(key);
            return en;
        } catch (IntrospectionException e) {
            logger.log(Level.WARNING, null, e);
            return null;
        }

    }
}
