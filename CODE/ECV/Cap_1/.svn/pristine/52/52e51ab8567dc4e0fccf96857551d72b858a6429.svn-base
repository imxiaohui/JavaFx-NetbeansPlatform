/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public class Event implements Serializable {

    private Long id;
    private Date eventDate;
    protected String eventName;
    private Person person;
    private String town;
    private String state_province;
    private String country;
    protected SwingPropertyChangeSupport propChangeSupport = null;
    public static final String PROP_DATE = "eventDate";
    public static final String PROP_TOWN = "town";
    public static final String PROP_COUNTRY = "country";
    public static final String PROP_STATE = "state_province";
    private static SimpleDateFormat format = new SimpleDateFormat("E MMM dd, yyyy");
    // Use one lock for each Event object
    protected final Object lock = new Object();

    protected SwingPropertyChangeSupport getPropertyChangeSupport() {
        synchronized (lock) {
            if (this.propChangeSupport == null) {
                this.propChangeSupport = new SwingPropertyChangeSupport(this, true);
            }
        }
        return this.propChangeSupport;
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event() {
    }

    public String getCountry() {
        synchronized (lock) {
            return country;
        }
    }

    public void setCountry(String country) {
        String oldCountry;
        synchronized (lock) {
            oldCountry = this.country;
            this.country = country;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_COUNTRY, oldCountry, country);
    }

    public Date getEventDate() {
        synchronized (lock) {
            return eventDate;
        }
    }

    public void setEventDate(Date eventDate) {
        Date oldDate;
        synchronized (lock) {
            oldDate = this.eventDate;
            this.eventDate = eventDate;
        }
        getPropertyChangeSupport().firePropertyChange(
                PROP_DATE, oldDate, eventDate);
    }

    public Long getId() {
        synchronized (lock) {
            return id;
        }
    }

    public String getState_province() {
        synchronized (lock) {
            return state_province;
        }
    }

    public void setState_province(String state_province) {
        String oldState_province;
        synchronized (lock) {
            oldState_province = this.state_province;
            this.state_province = state_province;
        }
        getPropertyChangeSupport().firePropertyChange(
                PROP_STATE, oldState_province, state_province);
    }

    public String getTown() {
        synchronized (lock) {
            return town;
        }
    }

    public void setTown(String town) {
        String oldTown;
        synchronized (lock) {
            oldTown = this.town;
            this.town = town;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_TOWN, oldTown, town);
    }

    public String getEventName() {
        synchronized (lock) {
            return eventName;
        }
    }

    protected void setEventName(String eventName) {
        synchronized (lock) {
            this.eventName = eventName;
        }
    }

    public void setPerson(Person person) {
        synchronized (lock) {
            this.person = person;
        }
    }

    public Person getPerson() {
        synchronized (lock) {
            return person;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        synchronized (lock) {
            StringBuilder sb = new StringBuilder();
            sb.append(eventName);
            sb.append(": ");
            sb.append(person.getFirstname()).append(" ").
                    append(person.getLastname());
            sb.append(", ");
            if (eventDate != null) {
                sb.append(format.format(eventDate));
            }
            if (town != null) {
                sb.append(", ");
                sb.append(town);
            }
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        synchronized (lock) {
            if (o == null) {
                return false;
            }
            if (getClass() != o.getClass()) {
                return false;
            }
        }
        return this.getId().equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (id != null ? id.hashCode() : 0);
        return hash;
    }
}
