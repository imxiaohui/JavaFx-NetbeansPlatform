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
@Table(name = "ChildParent")
public class ChildParentEventEntity extends EventEntity {

    @ManyToOne
    @JoinColumn(nullable=true)
    private PersonEntity child;
    protected boolean bioBirth = true;

    public synchronized PersonEntity getChild() {
        return child;
    }

    public synchronized void setChild(PersonEntity child) {
        this.child = child;
    }

    public synchronized boolean isBioBirth() {
        return bioBirth;
    }

    public synchronized void setBioBirth(boolean bioBirth) {
        this.bioBirth = bioBirth;
        if (this.bioBirth) {
            this.eventName = "Child";
        } else {
            this.eventName = "ChildAdoption";
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" [");
        sb.append(getChild()).append("]");

        return sb.toString();
    }

    @Override
    public Event update(Event event, FamilyTreeDAOJPA ftm) {
        return ftm.updateChildParentEvent(event);
    }
}
