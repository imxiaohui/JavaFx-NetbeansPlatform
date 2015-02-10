/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import com.asgteach.familytree.model.Event;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author gail
 */
@Entity
@Table(name = "ParentChild")
public class ParentChildEventEntity extends EventEntity {

    @ManyToMany(cascade = {CascadeType.MERGE})
    @CascadeOnDelete
    private Set<PersonEntity> parents = new HashSet<>();
    protected boolean bioBirth = true;

    public ParentChildEventEntity() {
    }

    public synchronized boolean isBioBirth() {
        return bioBirth;
    }

    public synchronized void setBioBirth(boolean bioBirth) {
        this.bioBirth = bioBirth;
        if (this.bioBirth) {
            this.eventName = "Birth";
        } else {
            this.eventName = "Adoption";
        }
    }

    public synchronized Set<PersonEntity> getParents() {
        return Collections.unmodifiableSet(this.parents);
    }

    public synchronized void setParents(Set<PersonEntity> parents) {
        this.parents = parents;
    }

    public synchronized void addParent(PersonEntity parent) {
        parents.add(parent);
    }

    public synchronized void removeParent(PersonEntity parent) {
        parents.remove(parent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        synchronized (this) {
            for (PersonEntity parent : parents) {
                sb.append(" [");
                sb.append(parent).append("]");
            }
            return sb.toString();
        }
    }

    @Override
    public Event update(Event event, FamilyTreeDAOJPA ftm) {
        return ftm.updateParentChildEvent(event);
    }
}
