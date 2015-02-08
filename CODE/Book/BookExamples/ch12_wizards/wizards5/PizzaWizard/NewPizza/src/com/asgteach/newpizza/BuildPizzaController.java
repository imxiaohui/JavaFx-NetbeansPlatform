/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import com.asgteach.customer.api.Customer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class BuildPizzaController implements 
        WizardDescriptor.Panel<WizardDescriptor>, PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private BuildPizzaVisual component;
    private PizzaOrder pizzaOrder;
    private Customer customer;
    public static final String PROP_PIZZA_ORDER = "pizzaOrder";
    public static final String PROP_IS_PICKUP = "isPickup";
    private boolean isPickup;
    private PropertyChangeSupport propChangeSupport;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public BuildPizzaVisual getComponent() {
        if (component == null) {
            component = new BuildPizzaVisual();
            this.component.addPropertyChangeListener(this);
        }
        return component;
    }
    
    public BuildPizzaController() {
        this.propChangeSupport = new PropertyChangeSupport(this);
    }

    public boolean isIsPickup() {
        return isPickup;
    }

    public void setIsPickup(boolean isPickup) {
        boolean oldValue = this.isPickup;
        this.isPickup = isPickup;
        this.propChangeSupport.firePropertyChange(PROP_IS_PICKUP, oldValue, isPickup);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }


    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
        customer = (Customer)wiz.getProperty(IdentifyCustomerController.PROP_CUSTOMER);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        pizzaOrder = getComponent().getPizzaOrder();
        pizzaOrder.setCustomer(customer);
        wiz.putProperty(PROP_PIZZA_ORDER, pizzaOrder);
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(BuildPizzaVisual.PROP_IS_PICKUP)) {
            pizzaOrder = getComponent().getPizzaOrder();
            setIsPickup(pizzaOrder.isPickup());
        }
    }    
}
