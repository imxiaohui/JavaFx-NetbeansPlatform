/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.javafxtasks;

import com.asgteach.familytree.capabilityinterfaces.ReloadableEventCapability;
import com.asgteach.familytree.capabilityinterfaces.ReloadablePersonFromEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.utilities.EventCapability;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author gail
 */
public class GetEventsTask extends Service<List<EventPeople>> {
    // This service gets the list of events associated with a Person
    // then gets the list of people associated with each event

    private Person person = null;

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    protected Task<List<EventPeople>> createTask() {
        return new Task<List<EventPeople>>() {
            @Override
            protected List<EventPeople> call() throws Exception {
                List<EventPeople> eventpeople = new ArrayList<>();
                EventCapability eventCapability = new EventCapability();
                ReloadableEventCapability rEventCapability = eventCapability.getLookup().
                        lookup(ReloadableEventCapability.class);
                // and use the capability
                if (rEventCapability != null && person != null) {
                    try {
                        // get the events for this person
                        rEventCapability.reload(person);

                        // getEvents() returns an unmodifiable list
                        for (Event e : eventCapability.getEvents()) {
                            EventPeople ep = new EventPeople();
                            ep.setEvent(e);
                            eventpeople.add(ep);
                        }
                        // now get the people associated with each event
                        for (EventPeople ep : eventpeople) {
                            EventCapability eventCapabilityForPeople = new EventCapability();
                            ReloadablePersonFromEventCapability rPersonCapability = eventCapabilityForPeople.getLookup().
                                    lookup(ReloadablePersonFromEventCapability.class);
                            if (rPersonCapability != null) {
                                rPersonCapability.reload(ep.getEvent());
                                // getPersons() returns an unmodifiable list
                                ep.setPeople(eventCapabilityForPeople.getPersons());
                            }
                        }
                    } catch (Exception e) {
                        // empty
                    }
                }
                return Collections.unmodifiableList(eventpeople);
            }
        };
    }
}
