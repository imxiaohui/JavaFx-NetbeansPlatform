/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.customer;

import com.asgteach.customer.api.Customer;
import com.asgteach.customer.api.CustomerStore;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = CustomerStore.class)
public class SimpleCustomerStore implements CustomerStore {
    private Map<String, Customer> customerMap = new HashMap<String, Customer>();

    @Override
    public Customer findCustomer(String name) {
        return customerMap.get(name);
    }

    @Override
    public void storeCustomer(Customer c) {
        customerMap.put(c.getName(), c);
    }
    
}
