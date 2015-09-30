package com.asgteach.familytree.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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
public class FamilyTreeManager {
    
    //Observable Map. Tiene la capacidad de disparar cambios a change y invalidation listeners.
    private final ObservableMap<Long,Person> observableMap = 
                                FXCollections.observableMap(new ConcurrentHashMap<>());
    
    private static FamilyTreeManager instance = null;
        
    private FamilyTreeManager(){
        //Singleton prevention
    }
    
    //regresa una instancia de esta clase.
    public synchronized static FamilyTreeManager getInstance(){
        if(instance == null)
            instance = new FamilyTreeManager();
        return instance;
    }
    
    //Agrego un change listener
    public synchronized void addListener(
                  MapChangeListener<? super Long,? super Person> ml){
        observableMap.addListener(ml);
    }
    
    //Remuevo un change listener
    public synchronized void removeListener(
                  MapChangeListener<? super Long,? super Person> ml){
        observableMap.removeListener(ml);
    }
    
    //Agrego un invalidation listener
    public synchronized void addListener(
                  InvalidationListener il){
        observableMap.addListener(il);
    }
    
    //Remuevo un invalidation listener
    public synchronized void removeListener(
                  InvalidationListener il){
        observableMap.removeListener(il);
    }
    
    //Manejo de eventos del HashMap.
    
    
    public synchronized void addPerson(Person p){
        Person person = new Person(p);
        observableMap.put(person.getId(),person);
    }
    
    /*
     *  Nota: Es importante mencionar que el objeto actualizado es una copia del objeto
     *  Original. Se sustituye el objeto original por la copia modificada, para que
     *  el evento de update pueda ser disparado.
     */
    public synchronized void updatePerson(Person p){
        Person person = new Person(p);
        observableMap.put(person.getId(),person);
    }
    
    public synchronized void deletePerson(Person p){
        Person person = new Person(p);
        observableMap.remove(person.getId(),person);
    }
    
    public synchronized List<Person> getAllPeople(){
        List<Person> copyList = new ArrayList<>();
        observableMap.values().stream().forEach((p)->copyList.add(new Person(p)));
        return copyList;
    }
}

