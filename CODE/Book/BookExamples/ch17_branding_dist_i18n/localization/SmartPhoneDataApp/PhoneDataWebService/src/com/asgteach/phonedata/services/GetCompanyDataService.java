/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.phonedata.services;


import com.asgteach.phonedata.entities.Company;
import com.asgteach.phonedata.webservice.CompanyJerseyClient;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author gail
 */
public class GetCompanyDataService extends Service<ObservableList<Company>> {
    private static final Logger logger =
                  Logger.getLogger(GetCompanyDataService.class.getName());

    @Override
    protected Task<ObservableList<Company>> createTask() {
        return new Task<ObservableList<Company>>() {
            
            private final CompanyJerseyClient client1 = new CompanyJerseyClient();
            
            @Override
            protected ObservableList<Company> call() throws Exception {                
                Response response = client1.findAll_XML(Response.class);
                GenericType<List<Company>> genericType = new GenericType<List<Company>>() {
                };
                int status = response.getStatus();
                if (status != Response.Status.OK.getStatusCode()) {
                    logger.log(Level.WARNING, "Bad status {0}", response.getStatusInfo().getReasonPhrase());
                    throw new Exception("Bad status: " + status + " for web service call");
                }

                // Returns an ArrayList of Companies from the web service
                List<Company> data = (response.readEntity(genericType));
                ObservableList<Company> companyList = FXCollections.observableArrayList(data);
                logger.log(Level.INFO, "Retreiving and Displaying Company Details");
                data.stream().forEach((company) -> {
                    logger.log(Level.INFO, "CompanyID: {0}", company.getCompanyid()
                            + ", Company: " + company.getCompanyname());
                });
                return companyList;
            }
        };
    }
}
