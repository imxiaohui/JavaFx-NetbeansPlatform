/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;


import com.asgteach.familytree.model.Person.Gender;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author gail
 */
@Entity
@Table(name="Person")
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    private int version;
    
    private String firstname = "";
    private String middlename = "";
    private String lastname = "Unknown";
    private String suffix = "";
    private Gender gender = Gender.UNKNOWN;
    private String notes = "";
    
    @OneToOne(mappedBy = "owner", cascade={CascadeType.ALL}, orphanRemoval=true, fetch = FetchType.LAZY)
    @CascadeOnDelete
    private PictureEntity picture;
       
    @OneToMany(mappedBy = "person", cascade={CascadeType.ALL})
    @CascadeOnDelete
    private List<EventEntity> events = 
            new ArrayList<>();
    
    @OneToMany(mappedBy = "spouse", cascade={CascadeType.ALL})
    @CascadeOnDelete
    private Set<MarriageEntity> marriageEvents = 
            new HashSet<>();
    
    @OneToMany(mappedBy = "ex", cascade={CascadeType.ALL})
    @CascadeOnDelete
    private Set<DivorceEntity> divorceEvents = 
            new HashSet<>();
    
    @OneToMany(mappedBy = "child", cascade={CascadeType.ALL})
    @CascadeOnDelete
    private Set<ChildParentEventEntity> childEvents = 
            new HashSet<>();
    
    @ManyToMany(mappedBy = "parents", cascade={CascadeType.MERGE})
    @CascadeOnDelete
    private Set<ParentChildEventEntity> parentEvents = 
            new HashSet<>();

    public PersonEntity() {
    }

    public synchronized PictureEntity getPicture() {
        return picture;
    }

    public synchronized void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public synchronized Set<ParentChildEventEntity> getParentEvents() {
        return Collections.unmodifiableSet(parentEvents);
    }

    public synchronized void setParentEvents(Set<ParentChildEventEntity> parentEvents) {
        this.parentEvents = parentEvents;
    }

    public synchronized Set<DivorceEntity> getDivorceEvents() {
        return Collections.unmodifiableSet(divorceEvents);
    }

    public synchronized void setDivorceEvents(Set<DivorceEntity> divorceEvents) {
        this.divorceEvents = divorceEvents;
    }

    public synchronized Set<MarriageEntity> getMarriageEvents() {
        return Collections.unmodifiableSet(marriageEvents);
    }

    public synchronized void setMarriageEvents(Set<MarriageEntity> marriageEvents) {
        this.marriageEvents = marriageEvents;
    }

    public synchronized Set<ChildParentEventEntity> getChildEvents() {
        return Collections.unmodifiableSet(childEvents);
    }

    public synchronized void setChildEvents(Set<ChildParentEventEntity> childEvents) {
        this.childEvents = childEvents;
    }

    public synchronized List<EventEntity> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public synchronized void setEvents(List<EventEntity> events) {
        this.events = events;
    }

    public synchronized String getFirstname() {
        return firstname;
    }

    public synchronized void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public synchronized String getLastname() {
        return lastname;
    }

    public synchronized void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public synchronized String getMiddlename() {
        return middlename;
    }

    public synchronized void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public synchronized String getNotes() {
        return notes;
    }

    public synchronized void setNotes(String notes) {
        this.notes = notes;
    }

    public synchronized Gender getGender() {
        return gender;
    }

    public synchronized void setGender(Gender gender) {
        this.gender = gender;
    }

    public synchronized String getSuffix() {
        return suffix;
    }

    public synchronized void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public synchronized Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {        
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        return this.getId().equals(((PersonEntity) o).getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getId()).append("] ");
        if (!this.getFirstname().isEmpty()) {
            sb.append(this.getFirstname());
        }
        if (!this.getMiddlename().isEmpty()) {
            sb.append(" ").append(this.getMiddlename());
        }
        if (!this.getLastname().isEmpty()) {
            sb.append(" ").append(this.getLastname());
        }
        if (!this.getSuffix().isEmpty()) {
            sb.append(" ").append(this.getSuffix());
        }
        return sb.toString();
    }
}
