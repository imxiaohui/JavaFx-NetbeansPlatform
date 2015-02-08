/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import com.asgteach.customer.api.Customer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class DeliveryInfoController implements
        WizardDescriptor.Panel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private DeliveryInfoVisual component;
    private Customer customer;
    private boolean isValid = false;
    private WizardDescriptor wizard;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public DeliveryInfoVisual getComponent() {
        if (component == null) {
            component = new DeliveryInfoVisual();
            this.component.addPropertyChangeListener(this);            
        }
        return component;
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
        return isValid;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }
    private final EventListenerList listeners = new EventListenerList();

    @Override
    public void addChangeListener(ChangeListener l) {
        listeners.add(ChangeListener.class, l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(ChangeListener.class, l);
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
        this.wizard = wiz;
        customer = (Customer) wiz.getProperty(IdentifyCustomerController.PROP_CUSTOMER);
        getComponent().setCustomer(customer);
        checkValidity();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        customer = getComponent().getCustomer();
        wiz.putProperty(IdentifyCustomerController.PROP_CUSTOMER, customer);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(DeliveryInfoVisual.PROP_CUSTOMER_INFO)) {
            boolean oldState = isValid;
            isValid = checkValidity();
            fireChangeEvent(this, oldState, isValid);
        }
    }

    private void setMessage(String message) {
        wizard.getNotificationLineSupport().setErrorMessage(message);
    }

    @NbBundle.Messages({
        "CTL_Panel4AddressRequired= Please provide an address",
        "CTL_Panel4CCRequired= Please provide a credit card number"
    })
    private boolean checkValidity() {
        if (getComponent().getCustomer().getName().isEmpty()) {
            setMessage(Bundle.CTL_Panel1InputRequired());
            return false;
        } else if (getComponent().getCustomer().getAddress().isEmpty()) {
            setMessage(Bundle.CTL_Panel4AddressRequired());
            return false;
        } else if (getComponent().getCustomer().getPhone().isEmpty()) {
            setMessage(Bundle.CTL_Panel2InputRequired());
            return false;
        } else if (getComponent().getCustomer().getCreditCard().isEmpty()) {
            setMessage(Bundle.CTL_Panel4CCRequired());
            return false;
        }
        setMessage(null);
        customer = getComponent().getCustomer();
        return true;
    }

    protected final void fireChangeEvent(Object source, boolean oldState, boolean newState) {
        if (oldState != newState) {
            ChangeEvent ev = new ChangeEvent(source);
            for (ChangeListener listener : listeners.getListeners(ChangeListener.class)) {
                listener.stateChanged(ev);
            }
        }
    }
}
