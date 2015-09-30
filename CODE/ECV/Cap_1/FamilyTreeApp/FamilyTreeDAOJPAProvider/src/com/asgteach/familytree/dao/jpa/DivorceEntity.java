/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.model.Event;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author gail
 */
@Entity
@Table(name="Divorce")
public class DivorceEntity extends EventEntity {
    
    @ManyToOne
    @JoinColumn(nullable=true)
    private PersonEntity ex;
    
    public DivorceEntity() {
        eventName = "Divorce";
    }
    
    public synchronized PersonEntity getEx() {
        return ex;
    }

    public synchronized void setEx(PersonEntity ex) {
        this.ex = ex;
    }
    
    
    @Override
    public synchronized String toString() {
        
        StringBuilder sb = new StringBuilder(super.toString());
        if (getEx() != null) {
            sb.append(" [from ").append(getEx()).append("]");
        }       
        return sb.toString();
    }
    
    @Override
    public Event update(Event event, FamilyTreeDAOJPA ftm) {
        return ftm.updateDivorce(event);
    }
    
}
