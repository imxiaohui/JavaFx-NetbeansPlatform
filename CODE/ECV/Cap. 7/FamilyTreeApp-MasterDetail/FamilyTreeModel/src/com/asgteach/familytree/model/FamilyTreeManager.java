package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FamilyTreeManager.
 * 
 * Este es otro ejemplo de Bounding Properties para control sobre cambios en objetos
 * a un nivel más general. Se detectan cambios en el conjunto de datos con el que se trabaja
 * y no en las propiedades de un objeto.
 * 
 * @author Ernesto Cantu
 */
public interface FamilyTreeManager {
   
    //PropertyChange Strings
    public static final String PROP_PERSON_DESTROYED = "removePerson";
    public static final String PROP_PERSON_ADDED = "addPerson";
    public static final String PROP_PERSON_UPDATED = "updatePerson";
    
    //Agrego y elmimino listeners!!!
    public void addPropertyChangeListener(PropertyChangeListener listener);
    
    public void removePropertyChangeListener(PropertyChangeListener listener);
    
    public void addPerson(Person p);
    
    public void updatePerson(Person p);
    
    public void deletePerson(Person p);
        
    public List<Person> getAllPeople();
    
}
