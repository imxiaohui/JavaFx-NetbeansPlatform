/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

import com.asgteach.familytree.capabilities.CreatablePersonCapability;
import com.asgteach.familytree.capabilities.RefreshCapability;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
public final class PersonCapability implements Lookup.Provider {

    private final Lookup lookup;
    private final InstanceContent instanceContent = new InstanceContent();
    private static final Logger logger = Logger.getLogger(PersonCapability.class.getName());
    private final List<Person> personList = new ArrayList<>();
    private FamilyTreeManager ftm = null;
    
    public PersonCapability() {
        lookup = new AbstractLookup(instanceContent);
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        instanceContent.add(new RefreshCapability() {

            @Override
            public void refresh() throws IOException {     
                if (ftm != null) {
                    personList.clear();
                    personList.addAll(ftm.getAllPeople());  
                } else {
                    logger.log(Level.SEVERE, "Cannot get FamilyTreeManager");
                }
            }
        });
        
        instanceContent.add(new CreatablePersonCapability() {

            @Override
            public void create(Person person) throws IOException {
                if (ftm != null) {
                    ftm.addPerson(person);
                }
            }
        });
    }
    
    public List<Person> getPersonList() {
        return personList;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }    

}
