/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.manager.jpa;

import com.asgteach.familytree.model.Person;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

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
    private Person.Gender gender = Person.Gender.UNKNOWN;
    private String notes = "";
    
    public PersonEntity() {
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

    public synchronized Person.Gender getGender() {
        return gender;
    }

    public synchronized void setGender(Person.Gender gender) {
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
