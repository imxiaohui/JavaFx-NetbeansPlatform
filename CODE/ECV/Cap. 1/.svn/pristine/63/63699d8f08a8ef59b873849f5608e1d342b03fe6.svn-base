/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

/**
 *
 * @author gail
 */
public class ChildParentEvent extends Event {

    protected boolean bioBirth = true;
    private Person child;
    public static final String PROP_CHILD = "child";

    public ChildParentEvent() {
    }

    public ChildParentEvent(Long id) {
        super(id);
    }

    public boolean isBioBirth() {
        synchronized (lock) {
            return bioBirth;
        }
    }

    protected void setBioBirth(boolean bioBirth) {
        synchronized (lock) {
            this.bioBirth = bioBirth;
        }
    }

    public Person getChild() {
        synchronized (lock) {
            return child;
        }
    }

    public void setChild(Person child) {
        Person oldChild;
        synchronized (lock) {
            oldChild = this.child;
            this.child = child;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_CHILD, oldChild, child);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        synchronized (lock) {
            if (child != null) {
                sb.append(" [").append(child).append("]");
            }
            return sb.toString();
        }
    }
}
