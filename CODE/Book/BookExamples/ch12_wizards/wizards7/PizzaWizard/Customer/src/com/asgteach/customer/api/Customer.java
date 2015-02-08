/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.customer.api;

/**
 *
 * @author gail
 */
public class Customer {
    private String name = "";
    private String phone = "";
    private String address = "";
    private String creditCard = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Customer{" + "name=" + name + ", phone=" + phone + ", address=" 
                + address + ", creditCard=" + creditCard + '}';
    }
    
    
}
