/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.phonedatashareimpl;

import com.asgteach.phonedata.entities.Company;
import com.asgteach.phonedata.share.PhoneDataShare;
import com.asgteach.phonedata.entities.Salesdata;
import com.asgteach.phonedata.services.GetCompanyDataService;
import com.asgteach.phonedata.services.GetSmartPhoneSalesService;
import com.asgteach.phonedata.services.UpdateSalesItemService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ProgressIndicator;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = PhoneDataShare.class)
public class PhoneDataShareImpl implements PhoneDataShare {

    private final ListProperty<Salesdata> underlyingData = new 
        SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Company> companyDataNames = 
            new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<String> categoryDataNames = new 
        SimpleListProperty<>(FXCollections.observableArrayList());
    private static final String dataDescription = "Units Sold in Millions";
    private final String nameDescription = "Year";
    private final String title = "Smart Phone Sales";

    private final GetSmartPhoneSalesService salesService = new GetSmartPhoneSalesService();
    private final GetCompanyDataService companyService = new GetCompanyDataService();
    private final UpdateSalesItemService update = new UpdateSalesItemService();

    private static final Logger logger
            = Logger.getLogger(PhoneDataShareImpl.class.getName());

    public PhoneDataShareImpl() {
        getDataInBackground(null);
        getCompanyDataInBackground();
    }

    @Override
    public ListProperty<Company> companyNamesProperty() {
        return companyDataNames;
    }

    @Override
    public ListProperty<String> categoryListProperty() {
        return categoryDataNames;
    }

    @Override
    public String getDataDescription() {
        return dataDescription;
    }

    @Override
    public String getNameDescription() {
        return nameDescription;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getTickUnit() {
        return 1000;
    }

    @Override
    public void refreshData(ProgressIndicator progressIndicator) {
        getDataInBackground(progressIndicator);
    }

    @Override
    public void updateSales(Salesdata salesdata, ProgressIndicator progressIndicator) {
        updateSalesDataInBackground(salesdata, progressIndicator);
    }

    @Override
    public ListProperty<Salesdata> theDataProperty() {
        return underlyingData;
    }

    @SuppressWarnings("unchecked")
    private void getCompanyDataInBackground() {
        // This service is only invoked once
        companyService.setOnSucceeded((WorkerStateEvent t) -> {
            logger.log(Level.INFO, "Company data Done.");
            companyDataNames.set((ObservableList<Company>) t.getSource().getValue());
            for (Company company : companyDataNames) {
                logger.log(Level.FINE, "Company Data retrieved: {0}", company.getCompanyname());
            }
        });
        companyService.setOnFailed((WorkerStateEvent t) -> {
            logger.log(Level.WARNING, "Failed: Read Company data.");
        });
        companyService.start();
    }

    private void updateSalesDataInBackground(final Salesdata newSalesdata, ProgressIndicator progress) {
        // This service can be invoked multiple times
        update.setOnSucceeded((WorkerStateEvent t) -> {
            logger.log(Level.INFO, "Done: Salesdata UPDATED for  {0}",
                    t.getSource().getValue());
            if (progress != null) {
                progress.setVisible(false);
            }
            // this will cause 2 separate change events: a remove and then an add
            underlyingData.remove(newSalesdata);
            underlyingData.add(newSalesdata);
        });
        update.setOnFailed((WorkerStateEvent t) -> {
            logger.log(Level.WARNING, "Failed: Salesdata UPDATED for {0}",
                    newSalesdata.getCompanyid().getCompanyname());
            if (progress != null) {
                progress.setVisible(false);
            }
        });
        // only start the service if it's ready
        if (update.getState() == Worker.State.SUCCEEDED) {
            update.reset();
        }
        if (update.getState() == Worker.State.READY) {
            if (progress != null) {
                progress.setVisible(true);
            }
            update.setSalesdata(newSalesdata);
            update.start();
        }
    }

    @SuppressWarnings("unchecked")
    private void getDataInBackground(ProgressIndicator progressIndicator) {
        // This service can be invoked multiple times
        salesService.setOnSucceeded((WorkerStateEvent t) -> {
            logger.log(Level.INFO, "sales data Done.");
            if (progressIndicator != null) {
                progressIndicator.setVisible(false);
            }
            underlyingData.set((ObservableList<Salesdata>) t.getSource().getValue());
            for (Salesdata sales : underlyingData.get()) {
                if (!categoryDataNames.contains(sales.getSalesyear())) {
                    categoryDataNames.add(sales.getSalesyear());
                    logger.log(Level.INFO, "Adding category for {0}", sales.getSalesyear());
                }
            }
        });
        salesService.setOnFailed((WorkerStateEvent t) -> {
            if (progressIndicator != null) {
                progressIndicator.setVisible(false);
            }
            logger.log(Level.WARNING, "Failed: Read Salesdata");
        });
        // only start the service if it's ready
        if (salesService.getState() == Worker.State.SUCCEEDED) {
            salesService.reset();
        }
        if (salesService.getState() == Worker.State.READY) {
            if (progressIndicator != null) {
                progressIndicator.setVisible(true);
            }
            salesService.start();
        }
    }

}
