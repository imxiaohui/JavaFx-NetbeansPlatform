/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

import com.asgteach.familytree.capabilities.RefreshCapability;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;

/**
 *
 * @author gail
 */
public class PersonChildFactory extends ChildFactory<Person> {

    private static final Logger logger = Logger.getLogger(PersonChildFactory.class.getName());
    private final PersonCapability personCapability = new PersonCapability();
    private FamilyTreeManager ftm = null;

    public PersonChildFactory() {        
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        } else {
            ftm.addPropertyChangeListener(WeakListeners.propertyChange(familytreelistener, ftm));
        }
    }

    @Override
    protected boolean createKeys(List<Person> list) {
        RefreshCapability refreshCapability = personCapability.getLookup().lookup(RefreshCapability.class);
        if (refreshCapability != null) {
            try {
                refreshCapability.refresh();
                list.addAll(personCapability.getPersonList());
                logger.log(Level.FINER, "createKeys called: {0}", list);
            } catch (IOException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Person key) {
        logger.log(Level.FINER, "createNodeForKey: {0}", key);
        PersonNode node = new PersonNode(key);
        return node;
    }

    // PropertyChangeListener for FamilyTreeManager
    private final PropertyChangeListener familytreelistener = (PropertyChangeEvent evt) -> {
        if (evt.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_ADDED)
                || evt.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_DESTROYED)) {
            logger.log(Level.INFO, "detected add or destroy");
            this.refresh(true);
        }
    };
}
