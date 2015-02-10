/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.dao.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author gail
 */
@Entity
@Table(name="Picture")
public class PictureEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Version
    private int version;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "picdata")
    private byte[] picdata;
    
    private String filename;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false)
    private PersonEntity owner;
    
    public synchronized byte[] getPicData() {
        return picdata;
    }

    public synchronized void setPicData(byte[] picdata) {
        this.picdata = picdata;
    }

    public synchronized PersonEntity getOwner() {
        return owner;
    }

    public synchronized void setOwner(PersonEntity owner) {
        this.owner = owner;
    }

    public synchronized Long getId() {
        return id;
    }

    public synchronized String getFilename() {
        return filename;
    }

    public synchronized void setFilename(String filename) {
        this.filename = filename;
    }    
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        return this.getId().equals(((PictureEntity) o).getId());
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
        if (getFilename() != null) {
            sb.append(getFilename()).append(" for ").append(getOwner());
        }
        else {
            sb.append("Image for ").append(getOwner());
        }
        return sb.toString();
    }
}
