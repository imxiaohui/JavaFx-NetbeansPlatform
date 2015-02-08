/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gail
 */
public class FamilyTreeManager {

    private final Map<Long, Person> personMap = new HashMap<>();
    private PropertyChangeSupport propChangeSupport = null;

    // FamilyTreeManager property change names
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";

    private static FamilyTreeManager instance = null;

    protected FamilyTreeManager() {
        // Exists only to defeat instantiation.
    }

    public static FamilyTreeManager getInstance() {
        if (instance == null) {
            instance = new FamilyTreeManager();
            instance.propChangeSupport = new PropertyChangeSupport(instance);
        }
        return instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propChangeSupport.removePropertyChangeListener(listener);
    }

    public void addPerson(Person p) {
        Person person = new Person(p);
        personMap.put(person.getId(), person);
        this.propChangeSupport.firePropertyChange(PROP_PERSON_ADDED, null, person);
    }

    public void updatePerson(Person p) {
        Person person = new Person(p);
        personMap.put(person.getId(), person);
        this.propChangeSupport.firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }

    public void deletePerson(Person p) {
        // remove returns null if person.getId() not in map
        if (personMap.remove(p.getId()) != null) {
            this.propChangeSupport.firePropertyChange(PROP_PERSON_DESTROYED, null, p);
        }
    }

    public List<Person> getAllPeople() {
        List<Person> copyList = new ArrayList<>();
        // Use lambdas
        personMap.values().forEach(p -> copyList.add(new Person(p)));
        return copyList;

    }
}
