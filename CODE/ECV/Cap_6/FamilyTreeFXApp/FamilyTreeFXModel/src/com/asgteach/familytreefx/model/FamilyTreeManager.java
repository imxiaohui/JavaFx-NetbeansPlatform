package com.asgteach.familytreefx.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 * Clase FamilyTreeManager con un Observable HashMap.
 * Esta clase encapsula todas las operaciones de un observable map.
 * 
 * @author Ernesto Cantú
 * 5 Agosto 2015
 */
public interface FamilyTreeManager {
    
    //Agrego un change listener
    public void addListener(
                  MapChangeListener<? super Long,? super Person> ml);
    
    //Remuevo un change listener
    public void removeListener(
                  MapChangeListener<? super Long,? super Person> ml);
    
    //Agrego un invalidation listener
    public void addListener(
                  InvalidationListener il);
    
    //Remuevo un invalidation listener
    public void removeListener(
                  InvalidationListener il);
    
    //Manejo de eventos del HashMap.
    
    
    public void addPerson(Person p);
    
    /*
     *  Nota: Es importante mencionar que el objeto actualizado es una copia del objeto
     *  Original. Se sustituye el objeto original por la copia modificada, para que
     *  el evento de update pueda ser disparado.
     */
    public void updatePerson(Person p);
    
    public void deletePerson(Person p);
    
    public List<Person> getAllPeople();
}

