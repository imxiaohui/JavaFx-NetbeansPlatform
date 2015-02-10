/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.model.Event;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author gail
 */
@Entity
@Table(name="Event")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
abstract public class EventEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    private int version;
    
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    
    protected String eventName;
    
    @ManyToOne
    @JoinColumn(nullable=false)
    private PersonEntity person;
    
    private String town;
    private String state_province;
    private String country;
    
    public synchronized String getCountry() {
        return country;
    }

    public synchronized void setCountry(String country) {
        this.country = country;
    }

    public synchronized Date getEventDate() {
        return eventDate;
    }

    public synchronized void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public synchronized Long getId() {
        return id;
    }

    public synchronized String getState_province() {
        return state_province;
    }

    public synchronized void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public synchronized String getTown() {
        return town;
    }

    public synchronized void setTown(String town) {
        this.town = town;
    }


    public synchronized String getEventName() {
        return eventName;
    }


    public synchronized void setPerson(PersonEntity person) {
        this.person = person;
    }

    public synchronized PersonEntity getPerson() {
        return person;
    }
    
    abstract public Event update(Event event, FamilyTreeDAOJPA ftm);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEventName());
        sb.append(": ");
        sb.append(getPerson().getFirstname()).append(" ").append(getPerson().getLastname());
        sb.append(", ");
        SimpleDateFormat format = new SimpleDateFormat("E MMM dd, yyyy");
        sb.append(format.format(this.getEventDate()));
        if (this.getTown() != null) {
            sb.append(", ");
            sb.append(getTown());
        }
        return sb.toString();
    }
}
