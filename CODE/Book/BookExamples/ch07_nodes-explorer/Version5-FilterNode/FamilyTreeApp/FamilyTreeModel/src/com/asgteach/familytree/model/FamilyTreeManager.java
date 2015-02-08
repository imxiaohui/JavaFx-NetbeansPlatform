/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author gail
 */
public interface FamilyTreeManager {
    
    // FamilyTreeManager property change names
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);
    
    public void addPerson(Person p);

    public void updatePerson(Person p);

    public void deletePerson(Person p);
    
    public List<Person> getAllPeople();
}
