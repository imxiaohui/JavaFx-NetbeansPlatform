/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreefx.model;

import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;

/**
 *
 * @author gail
 */
public interface FamilyTreeManager {
    
    public void addListener(MapChangeListener<? super Long, ? super Person> ml);
    
    public void removeListener(MapChangeListener<? super Long, ? super Person> ml);
   
    public void addListener(InvalidationListener il);
    
    public void removeListener(InvalidationListener il);
    public void addPerson(Person p);

    public void updatePerson(Person p);

    public void deletePerson(Person p);
           
    public List<Person> getAllPeople();
}
