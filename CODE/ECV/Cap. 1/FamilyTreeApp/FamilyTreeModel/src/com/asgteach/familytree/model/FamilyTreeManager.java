/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author gail
 */
public interface FamilyTreeManager {

    public Person getPerson(Long id);

    public Person newPerson(String first, String last, Person.Gender gender);

    public Person newPerson(Person person);

    public Person updatePerson(Person p);

    public void removePerson(Person p);
    
    public Picture getPicture(Person p);
    
    public void storePicture(Picture pic);

    public List<Person> getAllPeople();

    public List<Event> getAllEvents();

    public ParentChildEvent findBirthAdopt(Long id);

    public Birth newBirth(Person p, Date d, String town,
            String state, String country);

    public Adoption newAdoption(Person p, Date d, String town,
            String state, String country);

    public ParentChildEvent addParent(Person child, Person parent);

    public ParentChildEvent addAdoptedParent(Person child, Person parent);

    public Death newDeath(Person p, Date d, String town,
            String state, String country);

    public Marriage newMarriage(Person p, Person spouse, Date d, String town,
            String state, String country);

    public Divorce newDivorce(Person p, Person exSpouse, Date d, String town,
            String state, String country);

    public void removeEvent(Event event);

    public Event updateEvent(Event event);

    public Event createEvent(Event event);

    public List<Event> findAllEvents(Person person);

    public Set<Person> getTopAncestors();

    public Set<Person> getLeafDescendents();

    public Set<Person> getChildren(Person parent);

    public List<Person> findPeopleFromEvent(Event event);

    public boolean isDuplicateEvent(Event event);

    public Death findDeathRecord(Person person);

    public Birth findBirthRecord(Person person);

    public List<Marriage> findMarriages(Person person);

    public List<Divorce> findDivorces(Person person);

    public Adoption findAdoptionRecord(Person person);

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);
    public static final String PROP_PERSON_DESTROYED = "PersonDestroyed";
    public static final String PROP_EVENT_DESTROYED = "EventDestroyed";
    public static final String PROP_PERSON_ADDED = "PersonAdded";
    public static final String PROP_EVENT_ADDED = "EventAdded";
    public static final String PROP_PERSON_UPDATED = "PersonUpdated";
    public static final String PROP_EVENT_UPDATED = "EventUpdated";
    public static final String PROP_IMAGE_STORED = "ImageStored";
}
