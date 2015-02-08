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

public class NewCustomerController implements
        WizardDescriptor.Panel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private NewCustomerVisual component;
    private boolean isValid = false;
    private WizardDescriptor wizard;
    private Customer customer;
    private String customerName;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public NewCustomerVisual getComponent() {
        if (component == null) {
            component = new NewCustomerVisual();
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

        customerName = (String) wiz.getProperty(IdentifyCustomerVisual.PROP_CUSTOMER_NAME);
        getComponent().getNameLabel().setText(customerName);

        customer = (Customer) wiz.getProperty(IdentifyCustomerController.PROP_CUSTOMER);
        if (customer == null) {
            customer = new Customer();
            customer.setName(customerName);
        } else {
            getComponent().getPhoneTextField().setText(customer.getPhone());
        }
        checkValidity();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty(IdentifyCustomerController.PROP_CUSTOMER, customer);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(NewCustomerVisual.PROP_PHONE)) {
            boolean oldState = isValid;
            isValid = checkValidity();
            fireChangeEvent(this, oldState, isValid);
        }
    }

    private void setMessage(String message) {
        wizard.getNotificationLineSupport().setErrorMessage(message);
    }

    @NbBundle.Messages({
        "CTL_Panel2InputRequired= Please provide a phone number"
    })
    private boolean checkValidity() {
        if (getComponent().getPhone().isEmpty()) {
            setMessage(Bundle.CTL_Panel2InputRequired());
            return false;
        }
        setMessage(null);
        customer.setPhone(getComponent().getPhone());
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
