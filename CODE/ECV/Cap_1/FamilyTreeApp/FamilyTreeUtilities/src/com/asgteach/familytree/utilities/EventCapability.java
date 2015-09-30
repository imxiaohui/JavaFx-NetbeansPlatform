/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.utilities;

import com.asgteach.familytree.capabilityinterfaces.CheckEventCapability;
import com.asgteach.familytree.capabilityinterfaces.CreatableEventCapability;
import com.asgteach.familytree.capabilityinterfaces.ReloadableEventCapability;
import com.asgteach.familytree.capabilityinterfaces.ReloadablePersonFromEventCapability;
import com.asgteach.familytree.capabilityinterfaces.RemovableEventCapability;
import com.asgteach.familytree.capabilityinterfaces.SavableEventCapability;
import com.asgteach.familytree.model.Event;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
public class EventCapability implements Lookup.Provider {

    private List<Event> events = Collections.synchronizedList(new ArrayList<Event>());
    private List<Person> persons = Collections.synchronizedList(new ArrayList<Person>());
    private final Lookup lookup;
    private final InstanceContent instanceContent;
    private boolean duplicateEvent;
    private FamilyTreeManager ftm = null;
    private static final Logger logger = Logger.getLogger(EventCapability.class.getName());

    public EventCapability() {
        // Create an InstanceContent to hold abilities
        instanceContent = new InstanceContent();
        // Create an AbstractLookup to expose InstanceContent contents
        lookup = new AbstractLookup(instanceContent);
        // Add a "Reloadable" capability to this entity
        instanceContent.add((ReloadableEventCapability) (Person person) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "ReloadableEventCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "ReloadableEventCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "ReloadableEventCapability: IN Background");
            }
            logger.log(Level.FINER,
                    "ReloadableEventCapability: reload events for {0}",
                    person);
            ProgressHandle handle = ProgressHandleFactory.createHandle("Events for " + person);
            try {
                handle.start();
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                        logger.log(Level.WARNING, null, e);
//                    }
                initFTM();
                List<Event> result = ftm.findAllEvents(person);
                events.clear();
                events.addAll(result);
                logger.log(Level.FINER,
                        "ReloadableEventCapability: Got data from ftm size={0}",
                        events.size());
            } finally {
                handle.finish();
            }
        });
        // Add a "Reloadable" capability to this entity
        instanceContent.add((ReloadablePersonFromEventCapability) (Event event) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "ReloadablePersonFromEventCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "ReloadablePersonFromEventCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "ReloadablePersonFromEventCapability: IN Background");
            }
            logger.log(Level.FINER,
                    "ReloadablePersonFromEventCapability: reload persons for {0}",
                    event);
            ProgressHandle handle = ProgressHandleFactory.createHandle("People for " + event);
            try {
                handle.start();
                initFTM();
                List<Person> result = ftm.findPeopleFromEvent(event);
                persons.clear();
                persons.addAll(result);
                logger.log(Level.FINER,
                        "ReloadablePersonFromEventCapability): Got data from ftm, size={0}",
                        persons.size());
            } finally {
                handle.finish();
            }
        });
        instanceContent.add((SavableEventCapability) (Event event) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "SavableEventCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "SavableEventCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "SavableEventCapability: IN Background");
            }
            logger.log(Level.FINER,
                    "SavableEventCapability: saving {0}", event);
            initFTM();
            ftm.updateEvent(event);
        });
        // add a "Creatable" capability to this entity
        instanceContent.add((CreatableEventCapability) (Event event) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "CreatableEventCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "CreatableEventCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "CreatableEventCapability: IN Background");
            }
            initFTM();
            ftm.createEvent(event);
        });
        // add a CheckEventCapability to this entity
        instanceContent.add((CheckEventCapability) (Event event) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "CheckEventCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "CheckEventCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "CheckEventCapability: IN Background");
            }
            initFTM();
            duplicateEvent = ftm.isDuplicateEvent(event);
        });

        // add a "Removable" capability 
        instanceContent.add((RemovableEventCapability) (Event event) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "RemovableEventCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "RemovableEventCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "RemovableEventCapability: IN Background");
            }
            // this updates the database
            initFTM();
            ftm.removeEvent(event);
        });
    }

    private void initFTM() {
        if (this.ftm == null) {
            this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
            if (ftm == null) {
                logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
                LifecycleManager.getDefault().exit();
            }
        }
    }

    public boolean isDuplicateEvent() {
        return duplicateEvent;
    }

    public List<Person> getPersons() {
        return Collections.unmodifiableList(persons);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public String toString() {
        return "findAllEvents";
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
}
