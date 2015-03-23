package com.asgtech.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FamilyTreeManager es una clase que simula el uso de CRUD applications.
 * Cuenta con un objeto property change support
 * para poder informar a los interesados acerca de la modificacion de su estado.
 * 
 * @author ernesto cantu
 * 18 de marzo 2015
 */
public class FamilyTreeManager {
    
    private final Map<Long, Person> personMap = new HashMap<>();
    private PropertyChangeSupport propertyChangeSupport = null;
    
    /*
     * Constantes que dan nombre a las acciones a informar. 
     */
    private static final String PROP_PERSON_DESTROYED = "removePerson";
    private static final String PROP_PERSON_ADDED = "addPerson";
    private static final String PROP_PERSON_UPDATED = "updatePerson";
    
    /* Singleton class*/
    private static FamilyTreeManager instance = null;
    
    protected FamilyTreeManager(){
        //Constructor protegido para que no se pueda crear una instancia.
    }
    
    public static FamilyTreeManager getInstance(){
        if(instance == null){
            instance = new FamilyTreeManager();
            instance.propertyChangeSupport = new PropertyChangeSupport(instance);
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
        Person person = new Person(p);
        personMap.put(person.getId(), person);
        propertyChangeSupport.firePropertyChange(PROP_PERSON_ADDED, null, person);
    }
    
    public void deletePerson(Person p){
        Person person = personMap.remove(p.getId());
        propertyChangeSupport.firePropertyChange(PROP_PERSON_DESTROYED, null, person);
    }
    
    public void updatePerson(Person p){
        Person person = new Person(p);
        personMap.put(person.getId(), person);
        propertyChangeSupport.firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }
    
    public List<Person> getAllPeople(){
        List<Person> copyList = new ArrayList<>();
        for(Person p : personMap.values()){
            copyList.add(p);
        }
        return copyList;
    }
}
