/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.groupviewer;

import com.asgteach.familytree.model.Person;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author gail
 */
public class PersonChildFactory extends ChildFactory.Detachable<Person> implements ChangeListener {

    private static final Logger logger = Logger.getLogger(PersonChildFactory.class.getName());
    private final PersonModel personModel;

    public PersonChildFactory(final PersonModel personModel) {
        this.personModel = personModel;      
    }
    
    @Override
    protected boolean createKeys(List<Person> list) {
        list.addAll(personModel.getPersons());    
        logger.log(Level.INFO, "createKeys called: {0}", list);
        return true;
    }

    @Override
    protected Node createNodeForKey(Person key) {
        logger.log(Level.INFO, "createNodeForKey: {0}", key);
        PersonNode node = new PersonNode(key, personModel);
        return node;
    }
    
    // Called immediately before the first call to createKeys().
    @Override
    protected void addNotify() {
        personModel.addChangeListener(this);
    }

    // Called when this child factory is no longer in use, 
    // to dispose of resources, detach listeners, etc.
    @Override
    protected void removeNotify() {
        personModel.removeChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        refresh(false);
    }

}
