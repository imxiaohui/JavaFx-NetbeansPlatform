/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.model.Event;
import javax.persistence.*;

/**
 *
 * @author gail
 */
@Entity
@Table(name="Marriage")
public class MarriageEntity extends EventEntity {
    
    @ManyToOne
    @JoinColumn(nullable=true)
    private PersonEntity spouse;
    
    public MarriageEntity() {
        eventName = "Marriage";
    }
    

    public synchronized PersonEntity getSpouse() {
        return spouse;
    }

    public synchronized void setSpouse(PersonEntity spouse) {
        this.spouse = spouse;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder(super.toString());
        if (getSpouse() != null) {
            sb.append(" [to ").append(getSpouse()).append("]");
        }       
        return sb.toString();
    }

    @Override
    public Event update(Event event, FamilyTreeDAOJPA ftm) {
        return ftm.updateMarriage(event);
    }
    
}
