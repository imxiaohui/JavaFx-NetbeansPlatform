/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.editormanagers;

import com.asgteach.familytree.editorinterfaces.EventEditor;
import com.asgteach.familytree.editorinterfaces.EventEditorManager;
import com.asgteach.familytree.model.Event;
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
@ServiceProvider(service = EventEditorManager.class)
public class EventEditorManagerImpl implements EventEditorManager {

    // This class maintains the association between an editor and an event
    private final Map<Event, EventEditor> editorEventMap = new HashMap<>();
    private static EventEditor singletonEventEditor = null;
    private static final Logger logger = Logger.getLogger(EventEditorManagerImpl.class.getName());

    @Override
    public EventEditor getEditorForEvent(Event event) {
        EventEditor eventEditor = editorEventMap.get(event);
        if (eventEditor == null) {
            if (singletonEventEditor == null) {
                singletonEventEditor = Lookup.getDefault().lookup(EventEditor.class);
                if (singletonEventEditor == null) {
                    logger.log(Level.WARNING, "problem getting editor from lookup");
                    return null;
                }
            }
            Class<?> editorClass = singletonEventEditor.getClass();
            try {
                logger.log(Level.FINER, "creating new editor instance");
                eventEditor = (EventEditor)editorClass.newInstance();
                editorEventMap.put(event, eventEditor);
                
            } catch (InstantiationException | IllegalAccessException ex) {
                logger.log(Level.WARNING, null, ex);
                return null;
            }
        }
        return eventEditor;        
    }

    @Override
    public void unregisterEditor(Event event) {
        editorEventMap.remove(event);
        logger.log(Level.FINER, "unregistering editor for {0}", event);
    }
}
