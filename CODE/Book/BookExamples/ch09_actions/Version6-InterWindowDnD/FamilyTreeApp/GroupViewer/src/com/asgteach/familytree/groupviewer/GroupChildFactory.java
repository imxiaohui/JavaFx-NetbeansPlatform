/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.groupviewer;

import com.asgteach.familytree.groupviewer.GroupNode.PersonGroup;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.LifecycleManager;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gail
 */
public class GroupChildFactory extends ChildFactory<PersonGroup> {
    
    private static final Logger logger = Logger.getLogger(GroupChildFactory.class.getName());
    private final FamilyTreeManager ftm;
    private final List<Person> people = new ArrayList<>();
    
    GroupChildFactory() {
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        people.addAll(ftm.getAllPeople());
    }
    
    @Override
    protected boolean createKeys(List<PersonGroup> list) {
        list.addAll(Arrays.asList(GroupNode.PersonGroup.values()));
        return true;
    }

    @Override
    protected Node createNodeForKey(PersonGroup key) {
        // Put all people in group UNDECIDED to start
        if (key.equals(PersonGroup.UNDECIDED)) {
            return new GroupNode(key, new PersonModel(people));
        }
        return new GroupNode(key, new PersonModel(new ArrayList<>())); 
    }    
    
}
