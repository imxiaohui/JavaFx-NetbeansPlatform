/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.editormanagers;

import com.asgteach.familytree.editorinterfaces.PersonEditor;
import com.asgteach.familytree.editorinterfaces.PersonEditorManager;
import com.asgteach.familytree.model.Person;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = PersonEditorManager.class)
public class PersonEditorManagerImpl implements PersonEditorManager {

    // This class maintains the association between an editor and an event
    private final Map<Person, PersonEditor> editorPersonMap = new HashMap<>();
    private static PersonEditor singletonPersonEditor = null;
    private static final Logger logger = Logger.getLogger(PersonEditorManagerImpl.class.getName());

    @Override
    public PersonEditor getEditorForPerson(Person person) {
//        // Look in registry of open topcomponents first
//        for (TopComponent tc : TopComponent.getRegistry().getOpened()) {
//            if (tc instanceof PersonEditor && tc.getLookup().lookup(Person.class) == person) {
//                return (PersonEditor) tc;
//            }
//        }
//        // If not there, create a new editor
//        if (singletonPersonEditor == null) {
//            singletonPersonEditor = Lookup.getDefault().lookup(PersonEditor.class);
//            if (singletonPersonEditor == null) {
//                System.err.println("getEditorForPerson: problem getting editor from lookup");
//                return null;
//            }
//        }
//        Class<?> editorClass = singletonPersonEditor.getClass();
//        try {
//            System.out.println("PersonEditorManagerImpl: creating new editor instance");
//            PersonEditor personEditor = (PersonEditor) editorClass.newInstance();
//            return personEditor;
//
//        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
//            return null;
//        }



        PersonEditor personEditor = editorPersonMap.get(person);
        if (personEditor == null) {
            if (singletonPersonEditor == null) {
                singletonPersonEditor = Lookup.getDefault().lookup(PersonEditor.class);
                if (singletonPersonEditor == null) {
                    logger.log(Level.WARNING, "problem getting editor from lookup");
                    return null;
                }
            }
            Class<?> editorClass = singletonPersonEditor.getClass();
            try {
                logger.log(Level.FINER, "creating new editor instance");
                personEditor = (PersonEditor) editorClass.newInstance();
                editorPersonMap.put(person, personEditor);

            } catch (InstantiationException | IllegalAccessException ex) {
                logger.log(Level.WARNING, null, ex);
                return null;
            }
        }
        return personEditor;
    }

    @Override
    public void unregisterEditor(Person person) {
        editorPersonMap.remove(person);
        logger.log(Level.FINER, "unregistering editor for {0}", person);
    }
}
