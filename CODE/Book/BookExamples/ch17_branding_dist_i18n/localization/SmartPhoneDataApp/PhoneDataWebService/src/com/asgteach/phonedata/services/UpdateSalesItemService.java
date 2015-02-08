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
public class UpdateSalesItemService extends Service<Salesdata> {

    private Salesdata salesdata;
    private static final Logger logger =
                  Logger.getLogger(UpdateSalesItemService.class.getName());

    public Salesdata getSalesdata() {
        return salesdata;
    }

    public void setSalesdata(Salesdata salesdata) {
        this.salesdata = salesdata;
    }

    @Override
    protected Task<Salesdata> createTask() {
        final Salesdata _salesdata = getSalesdata();
        return new Task<Salesdata>() {
            private final SalesdataJerseyClient client1 =
                    new SalesdataJerseyClient();

            @Override
            protected Salesdata call() throws Exception {
                client1.edit_XML(_salesdata, _salesdata.getSalesid().toString());                      
                return _salesdata;
            }
        };
    }
}
