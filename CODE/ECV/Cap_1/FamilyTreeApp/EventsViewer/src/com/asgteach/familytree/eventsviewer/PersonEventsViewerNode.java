/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventsviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadableViewCapability;
import com.asgteach.familytree.capabilityinterfaces.RemovablePersonCapability;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.PersonCapability;
import com.asgteach.familytree.utilities.PersonCapabilityNode;
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
public class PersonEventsViewerNode extends PersonCapabilityNode {
    // Note: PersonCapabilityNode has a PersonCapability object in its lookup!
    private static final Logger logger = Logger.getLogger(PersonEventsViewerNode.class.getName());

    public PersonEventsViewerNode(final Person person) {
        super(person, Children.create(new EventFactory(person), true));
        this.instanceContent.add((ReloadableViewCapability) () -> {
            logger.log(Level.FINER, "ReloadableViewCapability: reloadChildren");
            // To reload this node, just set a new set of children
            // using AllPeopleFactory object, that retrieves
            // children asynchronously
            setChildren(Children.create(new EventFactory(person),
                    true));
        });
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        final Person person = getLookup().lookup(Person.class);
        final PersonCapability capability = getLookup().lookup((PersonCapability.class));

        // Find the root node so we can do a complete
        // node tree reload
        Node parentnode = this.getParentNode();
        Node saveNode = parentnode;
        while (parentnode != null) {
            saveNode = parentnode;
            parentnode = parentnode.getParentNode();
        }
        final Node saveParentNode = saveNode;

        SwingWorker<Person, Void> worker = new SwingWorker<Person, Void>() {
            @Override
            public Person doInBackground() {
                RemovablePersonCapability rec = capability.getLookup().lookup(RemovablePersonCapability.class);
                try {
                    logger.log(Level.FINE, "Destroy called for {0}", person);
                    rec.remove(person);
                } catch (Exception e) {
                    logger.log(Level.WARNING, null, e);
                }
                return null;
            }

            @Override
            public void done() {
                if (saveParentNode != null) {
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
            }
        };
        worker.execute();
    }
}
