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
public class CreateSalesItemService extends Service<ObservableList<Salesdata>> {

    private Salesdata salesdata;
    private static final Logger logger =
                  Logger.getLogger(CreateSalesItemService.class.getName());

    public Salesdata getSalesdata() {
        return salesdata;
    }

    public void setSalesdata(Salesdata salesdata) {
        this.salesdata = salesdata;
    }

    @Override
    protected Task<ObservableList<Salesdata>> createTask() {
        final Salesdata _salesdata = getSalesdata();

        return new Task<ObservableList<Salesdata>>() {
            private final SalesdataJerseyClient client1 =
                    new SalesdataJerseyClient();

            @Override
            protected ObservableList<Salesdata> call() throws Exception {
                client1.create_XML(_salesdata);
                Response response = client1.findAll_XML(Response.class);
                GenericType<List<Salesdata>> genericType = new GenericType<List<Salesdata>>() {
                };
                int status = response.getStatus();
                if (status != Response.Status.OK.getStatusCode()) {
                    logger.log(Level.WARNING, "Bad status {0}", response.getStatusInfo().getReasonPhrase());
                    throw new Exception("Bad status: " + status + " for web service call");
                }
                logger.log(Level.INFO, "created Salesdata {0}", _salesdata.getUnitsinmillions());
                // Returns an ArrayList of SalesData from the web service
                List<Salesdata> data = response.readEntity(genericType);
                ObservableList<Salesdata> salesdata = FXCollections.observableArrayList(data);
                return salesdata;
            }
            
        };
    }
}
