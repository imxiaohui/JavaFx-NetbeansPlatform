package com.asgteach.familytree.manager.impl;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementacion de la interface FamilyTreeManager. 
 * La anotacion ServiceProvider la convierte automaticamente en singleton
 * @author ernesto
 */
@ServiceProvider(service=FamilyTreeManager.class)
public class FamilyTreeManagerImpl implements FamilyTreeManager {

    private final Map<Long,Person> personMap = new HashMap<>();
    private PropertyChangeSupport propertyChangeSupport = null;

    private PropertyChangeSupport getPropertyChangeSupport(){
        if(this.propertyChangeSupport == null){
            this.propertyChangeSupport = new PropertyChangeSupport(this);
        }
        return this.propertyChangeSupport;
    }
    
    //Agrego y elmimino listeners!!!
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
	this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener){
	this.propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    @Override
    public void addPerson(Person p){
    
        Person person = new Person(p);
        personMap.put(person.getId(),person);
        getPropertyChangeSupport().firePropertyChange(PROP_PERSON_ADDED, null, person);
    }
    
    @Override
    public void updatePerson(Person p){
        Person person = new Person(p);
        personMap.put(person.getId(),person);
        getPropertyChangeSupport().firePropertyChange(PROP_PERSON_UPDATED, null, person);
    }
    
    @Override
    public void deletePerson(Person p){

        Person person = personMap.remove(p.getId());
        if(person != null){
            getPropertyChangeSupport().firePropertyChange(PROP_PERSON_DESTROYED, null, person);
        }
    }
    
    @Override
    public List<Person> getAllPeople(){
        List<Person> copyList = new ArrayList<>();
        personMap.values().forEach((Person p) -> {
            copyList.add(p);
        });
        
        return copyList;
    }
    
}
