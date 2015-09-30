/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.editorinterfaces;

import com.asgteach.familytree.model.Person;

/**
 *
 * @author gail
 */
public interface PersonEditorManager {
    
    public PersonEditor getEditorForPerson(Person person);
    
    public void unregisterEditor(Person person); 
    
}
