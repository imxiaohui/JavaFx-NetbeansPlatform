/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public class FamilyTreeManager {

    private final ConcurrentMap<Long, Person> personMap = new ConcurrentHashMap<>();
    // A PropertyChangeSupport instance (and SwingPropertyChangeSupport instance) is thread safe
    private SwingPropertyChangeSupport propChangeSupport = null;
    // FamilyTreeManager property change names
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    private static FamilyTreeManager instance = null;

    protected FamilyTreeManager() {
        // Exists only to defeat instantiation.
    }

    // Thread-safe lazy initialization (Java Concurrency in Practice, p. 347)
    public synchronized static FamilyTreeManager getInstance() {
        if (instance == null) {
            instance = new FamilyTreeManager();
            instance.propChangeSupport = new SwingPropertyChangeSupport(instance, true);
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
        Person person;
        synchronized (this) {
            person = new Person(p);
            personMap.put(person.getId(), person);
        }
        this.propChangeSupport.firePropertyChange(PROP_PERSON_ADDED, null, person);
    }

    public void updatePerson(Person p) {
        Person person;
        synchronized (this) {
            person = new Person(p);
            personMap.put(person.getId(), person);
        }
        this.propChangeSupport.firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }

    public void deletePerson(Person p) {
        Person person;
        synchronized (this) {
            person = personMap.remove(p.getId());
            if (person == null) {
                return;
            }
        }
        this.propChangeSupport.firePropertyChange(PROP_PERSON_DESTROYED, null, person);
    }

    public synchronized List<Person> getAllPeople() {
        List<Person> copyList = new ArrayList<>();
        personMap.values().forEach(p -> copyList.add(new Person(p)));
        return copyList;
    }
}
