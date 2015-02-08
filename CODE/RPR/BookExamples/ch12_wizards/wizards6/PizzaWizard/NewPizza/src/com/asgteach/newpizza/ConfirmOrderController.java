/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class ConfirmOrderController implements
        WizardDescriptor.Panel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ConfirmOrderVisual component;
    private boolean isValid;
    private PizzaOrder pizzaOrder;
    private WizardDescriptor wizard;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public ConfirmOrderVisual getComponent() {
        if (component == null) {
            component = new ConfirmOrderVisual();
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
        pizzaOrder = (PizzaOrder) wiz.getProperty(BuildPizzaController.PROP_PIZZA_ORDER);
        getComponent().setForm(pizzaOrder);
        getComponent().getConfirmCheckBox().setSelected(false);
        setMessage(Bundle.CTL_Panel5ConfirmRequired());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(ConfirmOrderVisual.PROP_CONFIRM_ORDER)) {
            boolean oldState = isValid;
            isValid = checkValidity();
            fireChangeEvent(this, oldState, isValid);
        }
    }

    private void setMessage(String message) {
        wizard.getNotificationLineSupport().setInformationMessage(message);
    }

    @NbBundle.Messages({
        "CTL_Panel5ConfirmRequired= Check Confirm and Finish to place your order."
    })
    private boolean checkValidity() {
        if (!getComponent().confirmOrder()) {
            setMessage(Bundle.CTL_Panel5ConfirmRequired());
            return false;
        }
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
