/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

/**
 *
 * @author gail
 */
public class Marriage extends Event {

    private Person spouse;
    public static final String PROP_SPOUSE = "spouse";

    public Marriage() {
        setEventName("Marriage");
    }

    public Marriage(Long id) {
        super(id);
        setEventName("Marriage");
    }

    public Person getSpouse() {
        synchronized (lock) {
            return spouse;
        }
    }

    public void setSpouse(Person spouse) {
        Person oldSpouse;
        synchronized (lock) {
            oldSpouse = this.spouse;
            this.spouse = spouse;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_SPOUSE, oldSpouse, spouse);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        synchronized (lock) {
            if (spouse != null) {
                sb.append(" [to ").append(spouse).append("]");
            }
            return sb.toString();
        }
    }
}
