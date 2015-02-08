/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public final class Person implements Serializable {
    // Not thread-safe

    private final long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String suffix;
    private Person.Gender gender;
    private String notes;
    private SwingPropertyChangeSupport propChangeSupport = null;
    // Property names
    public static final String PROP_FIRST = "firstname";
    public static final String PROP_MIDDLE = "middlename";
    public static final String PROP_LAST = "lastname";
    public static final String PROP_SUFFIX = "suffix";
    public static final String PROP_GENDER = "gender";
    public static final String PROP_NOTES = "notes";
    // Use intrinsic lock "this" for each Person object
    private static AtomicLong count = new AtomicLong(0);

    public enum Gender {

        MALE, FEMALE, UNKNOWN
    }

    public Person() {
        this("", "", Gender.UNKNOWN);
    }

    public Person(String first, String last, Person.Gender gender) {
        this.firstname = first;
        this.middlename = "";
        this.lastname = last;
        this.suffix = "";
        this.gender = gender;
        this.id = count.incrementAndGet();
    }

    public Person(Person person) {
        this.firstname = person.getFirstname();
        this.middlename = person.getMiddlename();
        this.lastname = person.getLastname();
        this.suffix = person.getSuffix();
        this.gender = person.getGender();
        this.notes = person.getNotes();
        this.id = person.getId();
    }

    private PropertyChangeSupport getPropertyChangeSupport() {
        if (this.propChangeSupport == null) {
            this.propChangeSupport = new SwingPropertyChangeSupport(this, true);
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
        return notes;
    }

    public void setNotes(String notes) {
        String oldNotes = this.notes;
        this.notes = notes;
        getPropertyChangeSupport().firePropertyChange(PROP_NOTES, oldNotes, notes);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        String oldFirst = this.firstname;
        this.firstname = firstname;
        getPropertyChangeSupport().firePropertyChange(PROP_FIRST, oldFirst, firstname);
    }

    public Person.Gender getGender() {
        return gender;
    }

    public void setGender(Person.Gender gender) {
        Person.Gender oldGender = this.gender;
        this.gender = gender;
        getPropertyChangeSupport().firePropertyChange(PROP_GENDER, oldGender, gender);
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        String oldLast = this.lastname;
        this.lastname = lastname;
        getPropertyChangeSupport().firePropertyChange(PROP_LAST, oldLast, lastname);
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        String oldMiddle = this.middlename;
        this.middlename = middlename;
        getPropertyChangeSupport().firePropertyChange(PROP_MIDDLE, oldMiddle, middlename);
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        String oldSuffix = this.suffix;
        this.suffix = suffix;
        getPropertyChangeSupport().firePropertyChange(PROP_SUFFIX, oldSuffix, suffix);
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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
        return sb.toString();
    }
}
