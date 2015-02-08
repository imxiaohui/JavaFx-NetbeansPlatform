/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.smartphonedata.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gail
 */
@Entity
@Table(name = "SALESDATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salesdata.findAll", query = "SELECT s FROM Salesdata s"),
    @NamedQuery(name = "Salesdata.findBySalesid", query = "SELECT s FROM Salesdata s WHERE s.salesid = :salesid"),
    @NamedQuery(name = "Salesdata.findBySalesyear", query = "SELECT s FROM Salesdata s WHERE s.salesyear = :salesyear"),
    @NamedQuery(name = "Salesdata.findByUnitsinmillions", query = "SELECT s FROM Salesdata s WHERE s.unitsinmillions = :unitsinmillions")})
public class Salesdata implements Serializable {

    private static final long serialVersionUID = 1L;

    private final IntegerProperty salesidProperty = new SimpleIntegerProperty();
    private final StringProperty salesyearProperty = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> unitsinmillionsProperty
            = new SimpleObjectProperty<>();
    private final ObjectProperty<Company>  companyidProperty = new SimpleObjectProperty<>();

    public Salesdata() {
    }

    public Salesdata(Integer salesid) {
        salesidProperty.set(salesid);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SALESID")
    public Integer getSalesid() {
        return salesidProperty.get();
    }

    public void setSalesid(Integer salesid) {
        salesidProperty.set(salesid);
    }

    public IntegerProperty salesidProperty() {
        return salesidProperty;
    }

    @Size(max = 10)
    @Column(name = "SALESYEAR")
    public String getSalesyear() {
        return salesyearProperty.get();
    }

    public void setSalesyear(String salesyear) {
        salesyearProperty.set(salesyear);
    }

    public StringProperty salesyearProperty() {
        return salesyearProperty;
    }

    @Column(name = "UNITSINMILLIONS")
    public BigDecimal getUnitsinmillions() {
        return unitsinmillionsProperty.get();
    }

    public void setUnitsinmillions(BigDecimal unitsinmillions) {
        unitsinmillionsProperty.set(unitsinmillions);
    }
    
    public ObjectProperty<BigDecimal> unitsinmillionsProperty() {
        return unitsinmillionsProperty;
    }

    @JoinColumn(name = "COMPANYID", referencedColumnName = "COMPANYID")
    @ManyToOne
    public Company getCompanyid() {
        return companyidProperty.get();
    }

    public void setCompanyid(Company companyid) {
        companyidProperty.set(companyid);
    }
    
    public ObjectProperty<Company> companyidProperty() {
        return companyidProperty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getSalesid() != null ? getSalesid().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salesdata)) {
            return false;
        }
        Salesdata other = (Salesdata) object;
        return (getSalesid() != null || other.getSalesid() == null) 
                && (getSalesid() == null 
                || getSalesid().equals(other.getSalesid()));
    }

    @Override
    public String toString() {
        return "com.server.smartphonedata.entities.Salesdata[ salesid=" + getSalesid() + " ]";
    }

}
