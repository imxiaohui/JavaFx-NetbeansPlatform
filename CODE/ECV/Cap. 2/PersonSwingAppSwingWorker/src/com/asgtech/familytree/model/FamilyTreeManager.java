package com.asgtech.familytree.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * FamilyTreeManager es una clase que simula el uso de CRUD applications.
 * Cuenta con un objeto property change support
 * para poder informar a los interesados acerca de la modificacion de su estado.
 * 
 * @author ernesto cantu
 * 18 de marzo 2015
 */
public class FamilyTreeManager {
    
    private final ConcurrentMap<Long, Person> personMap = new ConcurrentHashMap<>();
    private SwingPropertyChangeSupport propertyChangeSupport = null;
    
    /*
     * Constantes que dan nombre a las acciones a informar. 
     */
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    
    /* Singleton class*/
    private static FamilyTreeManager instance = null;
    
    protected FamilyTreeManager(){
        //Constructor protegido para que no se pueda crear una instancia.
    }
    
    public synchronized static FamilyTreeManager getInstance(){
        if(instance == null){
            instance = new FamilyTreeManager();
            instance.propertyChangeSupport = new SwingPropertyChangeSupport(instance);
        }   
        return instance;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void addPerson(Person p){
        Person person;
        synchronized(this){
            person = new Person(p);
            personMap.put(person.getId(), person);
        }
        propertyChangeSupport.firePropertyChange(PROP_PERSON_ADDED, null, person);
    }
    
    public void deletePerson(Person p){
        Person person;
        synchronized(this){
            person = personMap.remove(p.getId());
            if(person == null){
                return;
            }
        }
        propertyChangeSupport.firePropertyChange(PROP_PERSON_DESTROYED, null, person);
    }
    
    public void updatePerson(Person p){
        Person person; 
        synchronized(this){
            person = new Person(p);
            personMap.put(person.getId(), person);
        }
        propertyChangeSupport.firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }
    
    public synchronized List<Person> getAllPeople(){
        List<Person> copyList = new ArrayList<>();
        for(Person p : personMap.values()){
            copyList.add(p);
        }
        return copyList;
    }
}
