package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * FamilyTreeManager.
 * 
 * Este es otro ejemplo de Bounding Properties para control sobre cambios en objetos
 * a un nivel más general. Se detectan cambios en el conjunto de datos con el que se trabaja
 * y no en las propiedades de un objeto.
 * 
 * @author Ernesto Cantu
 */
public class FamilyTreeManager {
    
    private final ConcurrentHashMap<Long,Person> personMap = new ConcurrentHashMap<>();
    private SwingPropertyChangeSupport propertyChangeSupport = null;
    private final Logger logger = Logger.getLogger(FamilyTreeManager.class.getName());
    //PropertyChange Strings
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    
    private static FamilyTreeManager instance = null;
    
    protected FamilyTreeManager(){
        logger.setLevel(Level.FINE);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
    }
    
    public synchronized static FamilyTreeManager getInstance(){
    
        if(instance == null){
            instance = new FamilyTreeManager();
            instance.propertyChangeSupport = new SwingPropertyChangeSupport(instance,true);
        }
        return instance;
    }
    
    //Agrego y elmimino listeners!!!
    public void addPropertyChangeListener(PropertyChangeListener listener){
	this.propertyChangeSupport.addPropertyChangeListener(listener);
        logger.log(Level.FINE, "Listener added");
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
	this.propertyChangeSupport.removePropertyChangeListener(listener);
        logger.log(Level.FINE, "Listener removed");
    }
    
    public void addPerson(Person p){
    
        Person person;
        synchronized(this){
            person = new Person(p);
            personMap.put(person.getId(),person);
        }
        logger.log(Level.FINE, "Person added");
        this.propertyChangeSupport.firePropertyChange(PROP_PERSON_ADDED, null, person);
    }
    
    public void updatePerson(Person p){
    
        Person person;
        synchronized(this){
            person = new Person(p);
            personMap.put(person.getId(),person);
        }
        logger.log(Level.FINE, "Person updated");
        this.propertyChangeSupport.firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }
    
    public void deletePerson(Person p){

       Person person;
        synchronized(this){
            person = personMap.remove(p);
            if(person != null){
                logger.log(Level.FINE, "Person deleted");
                this.propertyChangeSupport.firePropertyChange(PROP_PERSON_DESTROYED, null, person);
            }
        };
    }
    
//    public List<Person> getAllPeople(){
//        List<Person> copyList = new ArrayList<>();
//        for(Person p : personMap.values()){
//            copyList.add(p);
//        }
//        return copyList;
//    }
    
    public synchronized List<Person> getAllPeople(){
        logger.log(Level.FINE,"Getting all people");
        List<Person> copyList = new ArrayList<>();
        personMap.values().forEach((Person p) -> {
            copyList.add(p);
        });
        
        return copyList;
    }
}
