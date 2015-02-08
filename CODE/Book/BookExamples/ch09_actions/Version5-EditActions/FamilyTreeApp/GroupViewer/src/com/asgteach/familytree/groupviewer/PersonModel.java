/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.groupviewer;

import com.asgteach.familytree.model.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;

/**
 *
 * @author gail
 */
public class PersonModel {
    
    private final List<Person> persons;
    private final ChangeSupport cs = new ChangeSupport(this);

    public PersonModel(List<Person> persons) {
        this.persons = new ArrayList<>(persons);
    }

    public List<? extends Person> getPersons() {
        return persons;
    }

    public void add(Person p) {
        persons.add(p);
        cs.fireChange();
    }

    public void remove(Person p) {
        persons.remove(p);
        cs.fireChange();
    }

    public void reorder(int[] indexOrder) {
        Person[] reordered = new Person[persons.size()];
        for (int i = 0; i < indexOrder.length; i++) {
            int j = indexOrder[i];
            Person p = persons.get(i);
            reordered[j] = p;
        }
        persons.clear();
        persons.addAll(Arrays.asList(reordered));
        cs.fireChange();
    }

    public void addChangeListener(ChangeListener l) {
        cs.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        cs.removeChangeListener(l);
    }
    
}
