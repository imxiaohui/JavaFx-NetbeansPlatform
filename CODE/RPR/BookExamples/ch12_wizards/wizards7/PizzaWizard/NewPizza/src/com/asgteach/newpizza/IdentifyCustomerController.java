package com.asgteach.newpizza;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.asgteach.customer.api.Customer;
import com.asgteach.customer.api.CustomerStore;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public class IdentifyCustomerController implements
        WizardDescriptor.AsynchronousValidatingPanel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private IdentifyCustomerVisual component;
    private boolean isValid = false;
    private WizardDescriptor wizard;
    private String customerName;
    private Customer customer;
    public static final String PROP_CUSTOMER = "customer";
    private boolean isNewCustomer = true;
    private PropertyChangeSupport propChangeSupport;
    public static final String PROP_IS_NEW_CUSTOMER = "isNewCustomer";

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public IdentifyCustomerVisual getComponent() {
        if (component == null) {
            component = new IdentifyCustomerVisual();
            this.component.addPropertyChangeListener(this);
        }
        return component;
    }

    public IdentifyCustomerController() {
        this.propChangeSupport = new PropertyChangeSupport(this);
    }

    public boolean isIsNewCustomer() {
        return isNewCustomer;
    }

    public void setIsNewCustomer(boolean isNewCustomer) {
        boolean oldValue = this.isNewCustomer;
        this.isNewCustomer = isNewCustomer;
        this.propChangeSupport.firePropertyChange(PROP_IS_NEW_CUSTOMER, oldValue, isNewCustomer);
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
        checkValidity();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty(IdentifyCustomerVisual.PROP_CUSTOMER_NAME, customerName);
        wiz.putProperty(PROP_CUSTOMER, customer);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(IdentifyCustomerVisual.PROP_CUSTOMER_NAME)) {
            boolean oldState = isValid;
            isValid = checkValidity();
            fireChangeEvent(this, oldState, isValid);
        }
    }

    private void setMessage(String message) {
        wizard.getNotificationLineSupport().setErrorMessage(message);
    }

    @NbBundle.Messages({
        "CTL_Panel1InputRequired= Please provide a name"
    })
    private boolean checkValidity() {
        if (getComponent().getCustomerName().isEmpty()) {
            setMessage(Bundle.CTL_Panel1InputRequired());
            return false;
        }
        setMessage(null);
        customerName = getComponent().getCustomerName();
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

    @Override
    public void prepareValidation() {
        customerName = getComponent().getCustomerName();
    }

    @Override
    public void validate() throws WizardValidationException {
        CustomerStore custStore = Lookup.getDefault().lookup(CustomerStore.class);
        if (custStore != null) {
            // if customer is null then we need to go to NewCustomer
            customer = custStore.findCustomer(customerName);
            wizard.putProperty(PROP_CUSTOMER, customer);
            setIsNewCustomer(customer == null);
        }
    }
}
