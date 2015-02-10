/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

/**
 *
 * @author gail
 */
public class Divorce extends Event {

    private Person ex;
    public static final String PROP_EX = "ex";

    public Divorce() {
        setEventName("Divorce");
    }

    public Divorce(Long id) {
        super(id);
        setEventName("Divorce");
    }

    public synchronized Person getEx() {
        synchronized (lock) {
            return ex;
        }
    }

    public void setEx(Person ex) {
        Person oldEx;
        synchronized (lock) {
            oldEx = this.ex;
            this.ex = ex;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_EX, oldEx, ex);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(super.toString());
        synchronized (lock) {
            if (ex != null) {
                sb.append(" [from ").append(ex).append("]");
            }
            return sb.toString();
        }
    }
}
