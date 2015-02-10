/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.model.Event;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gail
 */
@Entity
@Table(name="Death")
public class DeathEntity extends EventEntity {
    
    
    public DeathEntity() {
        eventName = "Death";
    }
    
    @Override
    public Event update(Event event, FamilyTreeDAOJPA ftm) {
        return ftm.updateDeath(event);
    }
    
}
