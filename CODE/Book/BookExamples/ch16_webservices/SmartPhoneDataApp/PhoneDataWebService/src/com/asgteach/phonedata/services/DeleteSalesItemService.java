/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.phonedata.services;


import com.asgteach.phonedata.entities.Salesdata;
import com.asgteach.phonedata.webservice.SalesdataJerseyClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author gail
 */
public class DeleteSalesItemService extends Service<Void> {

    private Salesdata salesdata;
    private static final Logger logger =
                  Logger.getLogger(DeleteSalesItemService.class.getName());

    public Salesdata getSalesdata() {
        return salesdata;
    }

    public void setSalesdata(Salesdata salesdata) {
        this.salesdata = salesdata;
    }

    @Override
    protected Task<Void> createTask() {
        final Salesdata _salesdata = getSalesdata();

        return new Task<Void>() {
            private final SalesdataJerseyClient client1 =
                    new SalesdataJerseyClient();

            @Override
            protected Void call() throws Exception {

                Response response = client1.find_XML(Response.class, _salesdata.getSalesid().toString());
                GenericType<Salesdata> genericType = new GenericType<Salesdata>() {
                };

                int status = response.getStatus();
                if (status != Response.Status.OK.getStatusCode()) {
                    logger.log(Level.WARNING, "Bad status {0}", response.getStatusInfo().getReasonPhrase());
                    throw new Exception("Bad status: " + status + " for web service call");
                }

                Salesdata oldSalesdata = response.readEntity(genericType);
                logger.log(Level.INFO, "Deleting Salesdata: {0}", oldSalesdata.getCompanyid().getCompanyname()
                        + " with SalesID: " + oldSalesdata.getSalesid());

                client1.remove(oldSalesdata.getSalesid().toString());                             
                return null;
            }
        };
    }
}
