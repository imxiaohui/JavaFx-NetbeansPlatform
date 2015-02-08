/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.phonedata.share;

import com.asgteach.phonedata.entities.Company;
import com.asgteach.phonedata.entities.Salesdata;
import javafx.beans.property.ListProperty;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author gail
 */


public interface PhoneDataShare {
    // Salesdata
    public abstract ListProperty<Salesdata> theDataProperty();
    // Company
    public abstract ListProperty<Company> companyNamesProperty();
    // year as a String
    public abstract ListProperty<String> categoryListProperty();    
    public abstract String getDataDescription();
    public abstract String getNameDescription();
    public abstract String getTitle();
    public abstract double getTickUnit();
    public abstract void refreshData(ProgressIndicator progressIndicator); 
    public abstract void updateSales(Salesdata salesdata, ProgressIndicator progressIndicator);
}
