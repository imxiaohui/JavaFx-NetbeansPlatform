/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.phonedata.services;


import com.asgteach.phonedata.entities.Salesdata;
import com.asgteach.phonedata.webservice.SalesdataJerseyClient;
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
public class GetSmartPhoneSalesService extends Service<ObservableList<Salesdata>> {
    private static final Logger logger =
                  Logger.getLogger(GetSmartPhoneSalesService.class.getName());    

    @Override
    protected Task<ObservableList<Salesdata>> createTask() {

        return new Task<ObservableList<Salesdata>>() {
            private final SalesdataJerseyClient client1 =
                    new SalesdataJerseyClient();

            @Override
            protected ObservableList<Salesdata> call() throws Exception {
                logger.log(Level.INFO, "GetSmartPhoneSalesTask called.");
                Response response = client1.findAll_XML(Response.class);
                GenericType<List<Salesdata>> genericType = new GenericType<List<Salesdata>>() {
                };
                int status = response.getStatus();
                if (status != Response.Status.OK.getStatusCode()) {
                    logger.log(Level.WARNING, "Bad status {0}", response.getStatusInfo().getReasonPhrase());
                    throw new Exception("Bad status: " + status + " for web service call");
                }
                // Returns an ArrayList of SalesData from the web service
                List<Salesdata> data = (response.readEntity(genericType));
                ObservableList<Salesdata> salesdata =  FXCollections.observableArrayList(data);
                return salesdata;
            }
        };
    }
}
