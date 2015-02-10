/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventsviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadableViewCapability;
import com.asgteach.familytree.capabilityinterfaces.RemovableEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.utilities.*;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class EventNode extends EventCapabilityNode {
    // Note: EventCapabilityNode has EventCapability in its lookup!

    private static final Logger logger = Logger.getLogger(EventNode.class.getName());

    public EventNode(final Event event) throws IntrospectionException {
        super(event, Children.create(new PeopleFromEventsFactory(event), true));
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        final Event event = getLookup().lookup(Event.class);
        final EventCapability capability = getLookup().lookup((EventCapability.class));

        // Find the root node so we can do a complete
        // node tree reload
        Node parentnode = this.getParentNode();
        Node saveNode = parentnode;
        while (parentnode != null) {
            saveNode = parentnode;
            parentnode = parentnode.getParentNode();
        }
        final Node saveParentNode = saveNode;

        SwingWorker<Event, Void> worker = new SwingWorker<Event, Void>() {
            @Override
            public Event doInBackground() {
                RemovableEventCapability rec = capability.getLookup().lookup(RemovableEventCapability.class);
                try {
                    logger.log(Level.FINER, "destroy called for {0}", event);
                    rec.remove(event);
                } catch (Exception e) {
                    logger.log(Level.WARNING, null, e);
                }
                return null;
            }

            @Override
            public void done() {
                // Reload the entire node hierarchy
                ReloadableViewCapability rvc =
                        saveParentNode.getLookup().lookup(ReloadableViewCapability.class);
                try {
                    if (rvc != null) {
                        rvc.reloadChildren();
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, null, e);
                }
            }
        };
        worker.execute();
    }
}
