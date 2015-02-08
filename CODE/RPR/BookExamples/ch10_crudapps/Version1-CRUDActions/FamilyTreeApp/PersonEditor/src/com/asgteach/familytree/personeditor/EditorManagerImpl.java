/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personeditor;

import com.asgteach.familytree.editor.manager.EditorManager;
import com.asgteach.familytree.model.Person;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.Node;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = EditorManager.class)
public class EditorManagerImpl implements EditorManager {

    private static final Logger logger = Logger.getLogger(EditorManagerImpl.class.getName());
    Map<Person, PersonEditorTopComponent> tcMap = new HashMap<>(); 

    @Override
    public void openEditor(Node node) {
        Person person = node.getLookup().lookup(Person.class);
        if (person == null) {
            return;
        }
        logger.log(Level.INFO, "Request editor for {0}", person);
        PersonEditorTopComponent tc = tcMap.get(person);
        if (tc != null) {    
            if (!tc.isOpened()) {
                logger.log(Level.INFO, "Found [unopened] Editor for {0}", person);
                tc.open();
            }
            tc.setPerson(node);
            logger.log(Level.INFO, "Found [unopened or opened] Editor for {0}", person);
            tc.requestActive();
            return;
        }

        // Create a new TopComponent and open it...
        logger.log(Level.INFO, "Creating new Editor for {0}", person);
        tc = new PersonEditorTopComponent();
        tcMap.put(person, tc);
        tc.setPerson(node);
        tc.open();
        tc.requestActive();
    }

    @Override
    public void unRegisterEditor(Person person) {
        logger.log(Level.INFO, "Unregistering editor for {0}", person);
        tcMap.remove(person);       
    }
}
