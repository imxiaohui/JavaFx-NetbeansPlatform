/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.editorinterfaces;

import com.asgteach.familytree.model.Event;

/**
 *
 * @author gail
 */
public interface EventEditorManager {
    
    public EventEditor getEditorForEvent(Event event);
    
    public void unregisterEditor(Event event); 
    
}
