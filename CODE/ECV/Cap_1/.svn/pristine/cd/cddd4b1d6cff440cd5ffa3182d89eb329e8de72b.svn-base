/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.javafxtasks;

import com.asgteach.familytree.capabilityinterfaces.ReloadablePictureCapability;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
import com.asgteach.familytree.utilities.PersonCapability;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author gail
 */
public class GetPictureTask extends Service<Picture> {

    private Person person = null;

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    protected Task<Picture> createTask() {
        return new Task<Picture>() {
            @Override
            protected Picture call() throws Exception {
                final PersonCapability personCapability = new PersonCapability();
                final ReloadablePictureCapability picCapability = personCapability.getLookup().lookup(
                        ReloadablePictureCapability.class);
                Picture picture = null;
                if (picCapability != null && person != null) {
                    try {
                        picCapability.reload(person);
                        // get the picture        
                        picture = personCapability.getPicture();
                    } catch (Exception e) {
                        // empty
                    }
                }
                // returns Picture (could be null)                
                return picture;
            }
        };
    }
}
