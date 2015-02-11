/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 */
public class FamilyTreeManager {
    /*
        Hashmap donde se guardan las instancias de personas. Simula una bd
    */
    private final Map<Long,Person> personMap = new HashMap<>();
    
    /*
        Objeto que permite avisar a los interesados de algun cambio
    */
    private PropertyChangeSupport propertyChangeSuport = null;
    
    /*
        Ocurrencias en la base de datos
    */
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    
    private static FamilyTreeManager instance = null;

    protected FamilyTreeManager() {
    
    }
    
    public static FamilyTreeManager getInstance(){
        if( instance == null ){
            instance = new FamilyTreeManager();
            instance.propertyChangeSuport = new PropertyChangeSupport(instance);
        }
        return instance;
    }
    
    /*
        Permite dar de Alta a Receptores de cambios
    */
    public void addPropertyChangeListener(PropertyChangeListener pcl){
        this.propertyChangeSuport.addPropertyChangeListener(pcl);
    }
    
    /*
        Permite dar de Alta a Receptores de cambios
    */
    public void removePropertyChangeListener(PropertyChangeListener pcl){
        this.propertyChangeSuport.removePropertyChangeListener(pcl);
    }
    
    public void addPerson(Person p){
        Person person = new Person(p);
        personMap.put(person.getId(), person);
        this.propertyChangeSuport.firePropertyChange(PROP_PERSON_ADDED, null, person);
    }
    
    public void updatePerson(Person p){
        Person person = new Person(p);
        personMap.put(person.getId(), person);
        this.propertyChangeSuport.firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }
    
    public void deletePerson(Person p){
        Person person = personMap.remove(p.getId());
        
        if(person != null){
            this.propertyChangeSuport.firePropertyChange(PROP_PERSON_DESTROYED, null, person);
        }
    }
    
    public List<Person> getAll(){
        List<Person> copyList = new ArrayList<>();
        for(Person p: personMap.values()){
            copyList.add(p);
        }
        return copyList;
    }
}
