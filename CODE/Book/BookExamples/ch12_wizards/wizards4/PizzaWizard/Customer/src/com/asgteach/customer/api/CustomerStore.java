/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.customer.api;

/**
 *
 * @author gail
 */
public interface CustomerStore {
    
    public Customer findCustomer(String name);
    
    public void storeCustomer(Customer c);
    
}
