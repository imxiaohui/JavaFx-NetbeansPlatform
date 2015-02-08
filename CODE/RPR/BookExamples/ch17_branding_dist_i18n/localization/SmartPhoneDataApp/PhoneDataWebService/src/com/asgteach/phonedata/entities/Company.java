/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.phonedata.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gail
 */
@Entity
@Table(name = "COMPANY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
    @NamedQuery(name = "Company.findByCompanyid", query = "SELECT c FROM Company c WHERE c.companyid = :companyid"),
    @NamedQuery(name = "Company.findByCompanyname", query = "SELECT c FROM Company c WHERE c.companyname = :companyname")})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    private final IntegerProperty companyidProperty = new SimpleIntegerProperty();
    private final StringProperty companynameProperty = new SimpleStringProperty();   
    private Collection<Salesdata> salesdataCollection = Collections.emptyList();

    public Company() {
    }

    public Company(Integer companyid) {
        companyidProperty.set(companyid);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMPANYID")
    public Integer getCompanyid() {
        return companyidProperty.get();
    }

    public void setCompanyid(Integer companyid) {
        companyidProperty.set(companyid);
    }

    public IntegerProperty companyidProperty() {
        return companyidProperty;
    }

    @Column(name = "COMPANYNAME")
    public String getCompanyname() {
        return companynameProperty.get();
    }

    public void setCompanyname(String companyname) {
        companynameProperty.set(companyname);
    }

    public StringProperty companynameProperty() {
        return companynameProperty;
    }

    @OneToMany(mappedBy = "companyid")
    @XmlTransient
    public Collection<Salesdata> getSalesdataCollection() {
        return salesdataCollection;
    }

    public void setSalesdataCollection(Collection<Salesdata> salesdataCollection) {
        this.salesdataCollection = salesdataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCompanyid() != null ? getCompanyid().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        return (getCompanyid() != null || other.getCompanyid() == null)
                && (this.getCompanyid() == null
                || this.getCompanyid().equals(other.getCompanyid()));
    }

    @Override
    public String toString() {
        return "com.server.smartphonedata.entities.Company[ companyid=" + getCompanyid() + " ]";
    }

}
