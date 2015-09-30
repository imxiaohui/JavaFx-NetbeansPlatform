/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.javafxtasks;

import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import java.util.List;

/**
 *
 * @author gail
 */
// A convenience class to package events and the Person objects
// associated with those events
// This class is not thread-safe, but it is always thread-contained
public class EventPeople {
    private Event event;
    private List<Person> people;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }  
}
