/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gail
 */
public class ParentChildEvent extends Event {

    protected final Set<Person> parents =
            Collections.synchronizedSet(new HashSet<Person>());
    protected boolean bioBirth = true;
    public static final String PROP_PARENTS = "parents";

    public ParentChildEvent() {
    }

    public ParentChildEvent(Long id) {
        super(id);
    }

    public synchronized boolean isBioBirth() {
        return bioBirth;
    }

    protected synchronized void setBioBirth(boolean bioBirth) {
        this.bioBirth = bioBirth;
    }

    public Set<Person> getParents() {
        return Collections.unmodifiableSet(this.parents);
    }

    public void setParents(Set<Person> parents) {
        this.parents.clear();
        this.parents.addAll(parents);
        getPropertyChangeSupport().firePropertyChange(PROP_PARENTS, null, parents);
    }

    public void addParent(Person parent) {
        parents.add(parent);
        getPropertyChangeSupport().firePropertyChange(PROP_PARENTS, null, parents);
    }

    public void removeParent(Person parent) {
        parents.remove(parent);
        getPropertyChangeSupport().firePropertyChange(PROP_PARENTS, null, parents);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        synchronized (lock) {

            for (Person parent : parents) {
                sb.append(" [");
                sb.append(parent).append("]");
            }
            return sb.toString();
        }
    }
}
