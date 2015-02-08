/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import com.asgteach.customer.api.Customer;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gail
 */
public class PizzaOrder {
    private static int orderCount = 101;
    private Customer customer;
    private PizzaSize pizzaSize = PizzaSize.SMALL;
    private Set<String> toppings = new HashSet<String>();
    private boolean pickup = true;
    private int orderNum = orderCount++;
    
    public static final String pepperoni = "Pepperoni";
    public static final String onions = "Onions";
    public static final String sausage = "Sausage";            
    
    public enum PizzaSize {
        SMALL, MEDIUM, LARGE
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public Set<String> getToppings() {
        return toppings;
    }

    public void setToppings(Set<String> toppings) {
        this.toppings = toppings;
    }

    public Double getTotal() {
        // $1.25 per topping
        // $2.00 for delivery
        // Graduated cost per PizzaSize
        
        Double total = new Double(0.0);
        if (pizzaSize == PizzaSize.SMALL) {
            total = 10.00;
        } else if (pizzaSize == PizzaSize.MEDIUM) {
            total = 14.00;
        } else if (pizzaSize == PizzaSize.LARGE) {
            total = 18.00;
        }
        total = total + (toppings.size() * 1.25);
        if (!pickup) {
            total = total + 2.;
        }
        
        return total;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public int getOrderNum() {
        return orderNum;
    }
    
    
    
}
