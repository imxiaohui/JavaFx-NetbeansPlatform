/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.utilities;

import com.asgteach.familytree.capabilityinterfaces.CreatablePersonCapability;
import com.asgteach.familytree.capabilityinterfaces.ReloadablePersonCapability;
import com.asgteach.familytree.capabilityinterfaces.ReloadablePictureCapability;
import com.asgteach.familytree.capabilityinterfaces.RemovablePersonCapability;
import com.asgteach.familytree.capabilityinterfaces.SavablePersonCapability;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
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
public class PersonCapability implements Lookup.Provider {

    private List<Person> persons = Collections.synchronizedList(new ArrayList<Person>());
    private final Lookup lookup;
    private final InstanceContent instanceContent;
    private Picture picture = null;
    private FamilyTreeManager ftm = null;
    private static final Logger logger = Logger.getLogger(PersonCapability.class.getName());

    public PersonCapability() {
        // Create an InstanceContent to hold abilities
        instanceContent = new InstanceContent();
        // Create an AbstractLookup to expose InstanceContent contents
        lookup = new AbstractLookup(instanceContent);
        // Add a "Reloadable" capability to this entity
        instanceContent.add((ReloadablePersonCapability) () -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "ReloadablePersonCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "ReloadablePersonCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "ReloadablePersonCapability: IN Background");
            }
            ProgressHandle handle = ProgressHandleFactory.createHandle("All People . . . ");
            try {
                handle.start();
                // testing only!!!!
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                        logger.log(Level.WARNING, null, e);
//                    }
                initFTM();
                List<Person> result = ftm.getAllPeople();
                
                persons.clear();
                persons.addAll(result);
                
            } finally {
                handle.finish();
            }
        });
        // Add a "ReloadablePicture" capability to this entity
        instanceContent.add((ReloadablePictureCapability) (Person person) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "ReloadablePictureCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "ReloadablePictureCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "ReloadablePictureCapability: IN Background");
            }
            logger.log(Level.FINER, "ReloadablePictureCapability: reload pic for {0}", person);
            
            ProgressHandle handle = ProgressHandleFactory.createHandle("Picture " + person);
            try {
                handle.start();
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e) {
//                        logger.log(Level.WARNING, null, e);
//                    }
                initFTM();
                picture = ftm.getPicture(person);
            } finally {
                handle.finish();
            }
        });
        instanceContent.add((SavablePersonCapability) (Person person) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "SavablePersonCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "SavablePersonCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "SavablePersonCapability: IN Background");
            }
            initFTM();
            logger.log(Level.FINER, "SavablePersonCapability: saving Person {0}", person);
            ftm.updatePerson(person);
        });
        // add a "Creatable" capability to this entity
        instanceContent.add((CreatablePersonCapability) (Person person) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "CreatablePersonCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "CreatablePersonCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "CreatablePersonCapability: IN Background");
            }
            initFTM();
            ftm.newPerson(person);
        } // Once a person is created, it is stored in persistent store
        // and the reload capability will grab it along with the
        // other Person objects
        );

        // add a "Removable" capability to this entity
        instanceContent.add((RemovablePersonCapability) (Person person) -> {
            if (EventQueue.isDispatchThread()) {
                logger.log(Level.FINER, "RemovablePersonCapability: IN EDT");
            } else if (Platform.isFxApplicationThread()) {
                logger.log(Level.FINER, "RemovablePersonCapability: IN FX Thread");
            } else {
                logger.log(Level.FINER, "RemovablePersonCapability: IN Background");
            }
            // this updates the database
            initFTM();
            ftm.removePerson(person);
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

    public List<Person> getPersons() {
        return Collections.unmodifiableList(persons);
    }

    public Picture getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "person capability";
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
}
