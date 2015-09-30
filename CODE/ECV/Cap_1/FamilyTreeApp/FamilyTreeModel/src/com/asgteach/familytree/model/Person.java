/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public final class Person implements Serializable {

    private Long id;
    private String firstname = "";
    private String middlename = "";
    private String lastname = "Unknown";
    private String suffix = "";
    private Gender gender = Gender.UNKNOWN;
    private String notes = "";
    private SwingPropertyChangeSupport propChangeSupport = null;
    public static final String PROP_FIRST = "firstname";
    public static final String PROP_MIDDLE = "middlename";
    public static final String PROP_LAST = "lastname";
    public static final String PROP_SUFFIX = "suffix";
    public static final String PROP_GENDER = "gender";
    public static final String PROP_NOTES = "notes";
    // Use one lock for each Person object
    private final Object lock = new Object();

    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    public Person() {
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(String first, String last, Gender gender) {
        this.firstname = first;
        this.lastname = last;
        this.gender = gender;
    }

    private SwingPropertyChangeSupport getPropertyChangeSupport() {
        synchronized (lock) {
            if (this.propChangeSupport == null) {
                this.propChangeSupport = new SwingPropertyChangeSupport(this, true);
            }
        }
        return this.propChangeSupport;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    public String getNotes() {
        synchronized (lock) {
            return notes;
        }
    }

    public synchronized void setNotes(String notes) {
        String oldNotes;
        synchronized (lock) {
            oldNotes = this.notes;
            this.notes = notes;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_NOTES, oldNotes, notes);
    }

    public String getFirstname() {
        synchronized (lock) {
            return firstname;
        }
    }

    public void setFirstname(String firstname) {
        String oldFirst;
        synchronized (lock) {
            oldFirst = this.firstname;
            this.firstname = firstname;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_FIRST, oldFirst, firstname);
    }

    public Gender getGender() {
        synchronized (lock) {
            return gender;
        }
    }

    public void setGender(Gender gender) {
        Gender oldGender;
        synchronized (lock) {
            oldGender = this.gender;
            this.gender = gender;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_GENDER, oldGender, gender);
    }

    public String getLastname() {
        synchronized (lock) {
            return lastname;
        }
    }

    public void setLastname(String lastname) {
        String oldLast;
        synchronized (lock) {
            oldLast = this.lastname;
            this.lastname = lastname;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_LAST, oldLast, lastname);
    }

    public String getMiddlename() {
        synchronized (lock) {
            return middlename;
        }
    }

    public void setMiddlename(String middlename) {
        String oldMiddle;
        synchronized (lock) {
            oldMiddle = this.middlename;
            this.middlename = middlename;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_MIDDLE, oldMiddle, middlename);
    }

    public String getSuffix() {
        synchronized (lock) {
            return suffix;
        }
    }

    public void setSuffix(String suffix) {
        String oldSuffix;
        synchronized (lock) {
            oldSuffix = this.suffix;
            this.suffix = suffix;
        }
        getPropertyChangeSupport().firePropertyChange(PROP_SUFFIX, oldSuffix, suffix);
    }

    public Long getId() {
        synchronized (lock) {
            return id;
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
        return this.getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        synchronized (lock) {
            if (!firstname.isEmpty()) {
                sb.append(firstname);
            }
            if (!middlename.isEmpty()) {
                sb.append(" ").append(middlename);
            }
            if (!lastname.isEmpty()) {
                sb.append(" ").append(lastname);
            }
            if (!suffix.isEmpty()) {
                sb.append(" ").append(suffix);
            }
        }
        return sb.toString();
    }
}
