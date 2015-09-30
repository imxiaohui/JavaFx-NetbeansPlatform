/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventsviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadablePersonCapability;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.PersonCapability;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gail
 */
public class AllPeopleFactory extends ChildFactory<Person> {

    private final FamilyTreeManager ftm;
    private static final Logger logger = Logger.getLogger(AllPeopleFactory.class.getName());

    public AllPeopleFactory() {
        this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener((PropertyChangeEvent pce) -> {
            if (pce.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_ADDED)) {
                logger.log(Level.FINER, "Person Added {0}", pce.getOldValue());
                refresh(true);
            }
        });
    }

    @Override
    protected boolean createKeys(List<Person> toPopulate) {
        // get this capability from the lookup
        if (!EventQueue.isDispatchThread()) {
            logger.log(Level.FINER, "Not IN EDT");
        }
        final PersonCapability personCapability = new PersonCapability();
        ReloadablePersonCapability r = personCapability.getLookup().
                lookup(ReloadablePersonCapability.class);
        // and use the capability
        if (r != null) {
            try {
                logger.log(Level.FINER, "createKeys calling for reload");
                r.reload();
            } catch (Exception e) {
                logger.log(Level.WARNING, null, e);
            }
        }
        // Now populate the list of child entities
        toPopulate.addAll(personCapability.getPersons());
        // and return true since we're all set
        return true;
    }

    @Override
    protected Node createNodeForKey(Person key) {
        // PersonEventsViewerNode uses EventFactory for its
        // children
        PersonEventsViewerNode personNode =
                new PersonEventsViewerNode(key);
        return personNode;
    }
}
