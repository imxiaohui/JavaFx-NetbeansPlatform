/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

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
public class PersonNode extends PersonCapabilityNode {
    // Note: PersonCapabilityNode has its own private instance of 
    // PersonCapability in its lookup
    private static final Logger logger = Logger.getLogger(PersonNode.class.getName());

    protected PersonNode(final Person person, final Children children) {
        super(person, children);
        instanceContent.add((ReloadableViewCapability) () -> {
            logger.log(Level.FINER, "ReloadableViewCapability: reloadChildren for {0}", person);
            setChildren(children);
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
        final Node parentNode = this.getParentNode();

        SwingWorker<Person, Void> worker = new SwingWorker<Person, Void>() {
            @Override
            public Person doInBackground() {
                RemovablePersonCapability rec = capability.getLookup().lookup(RemovablePersonCapability.class);
                try {
                    if (rec != null) {
                        logger.log(Level.FINER, "destroy called for {0}", person);
                        rec.remove(person);
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, null, e);
                }
                return null;
            }

            @Override
            public void done() {
                if (parentNode != null) {
                    ReloadableViewCapability rvc =
                            parentNode.getLookup().lookup(ReloadableViewCapability.class);
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
