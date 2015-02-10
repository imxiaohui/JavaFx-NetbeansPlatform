/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventsviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadablePersonFromEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.EventCapability;
import java.awt.EventQueue;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class PeopleFromEventsFactory extends ChildFactory<Person> {

    // Create the Person keys for this event
    private final Event event;
    private final EventCapability eventCapability = new EventCapability();
    private static final Logger logger = Logger.getLogger(EventFactory.class.getName());

    public PeopleFromEventsFactory(Event event) {
        this.event = event;
    }

    @Override
    protected boolean createKeys(List<Person> toPopulate) {
        if (!EventQueue.isDispatchThread()) {
            logger.log(Level.FINER, "Not IN EDT");
        }
        // get this capability from the lookup
        ReloadablePersonFromEventCapability r = eventCapability.getLookup().
                lookup(ReloadablePersonFromEventCapability.class);
        // and use the capability
        if (r != null) {
            try {
                logger.log(Level.FINER, "createKeys reload for {0}", event);
                r.reload(event);
            } catch (Exception e) {
                logger.log(Level.WARNING, null, e);
            }
        }
        // Now populate the list of child entities
        toPopulate.clear();
        toPopulate.addAll(eventCapability.getPersons());
        // and return true since we're all set
        return true;
    }

    @Override
    protected Node createNodeForKey(Person key) {
        // And we start the cycle again!
        PersonEventsViewerNode personNode = 
                new PersonEventsViewerNode(key);
        return personNode;
    }

    

    

    
}
