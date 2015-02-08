/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.sequence;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class NumberWizardPanel2 implements
        WizardDescriptor.FinishablePanel<WizardDescriptor>,
        PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private NumberVisualPanel2 component;
    private WizardDescriptor wizard = null;
    private boolean isValid = false;
    private boolean isFinishPanel = false;
    private Integer firstNumber;
    private Integer secondNumber;
    private List<Integer> mynumbers;
    private static final int FINISH_LIMIT = 500;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public NumberVisualPanel2 getComponent() {
        if (component == null) {
            component = new NumberVisualPanel2();
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

    @NbBundle.Messages({
        "# {0} - current sequence string",
        "CTL_Panel2CurrentSequence= Current Sequence: {0}."
    })
    @SuppressWarnings("unchecked")
    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
        this.wizard = wiz;
        firstNumber = (Integer) wizard.getProperty(NumberVisualPanel1.PROP_FIRST_NUMBER);
        mynumbers = (List<Integer>) wizard.getProperty(NumberWizardPanel1.PROP_NUMBER_LIST);
        updatePanel();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty(NumberVisualPanel2.PROP_SECOND_NUMBER, secondNumber);
        mynumbers.clear();
        mynumbers.add(firstNumber);
        mynumbers.add(secondNumber);
        wiz.putProperty(NumberWizardPanel1.PROP_NUMBER_LIST, mynumbers);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(NumberVisualPanel2.PROP_SECOND_NUMBER)) {
            updatePanel();
        }
    }

    private void updatePanel() {
        boolean oldState = isValid;
        isValid = checkValidity();
        if (isValid) {
            getComponent().setCurrentSequence(
                    Bundle.CTL_Panel2CurrentSequence(
                    firstNumber + ", " + secondNumber));
        } else {
            getComponent().setCurrentSequence(
                    Bundle.CTL_Panel2CurrentSequence(firstNumber));
        }
        fireChangeEvent(this, oldState, isValid);
        oldState = isFinishPanel;
        if (secondNumber != null) {
            isFinishPanel = secondNumber > FINISH_LIMIT;
            fireChangeEvent(this, oldState, isFinishPanel);
        }
    }

    private void setMessage(String message) {
        wizard.getNotificationLineSupport().setErrorMessage(message);
    }

    @NbBundle.Messages({
        "# {0} - previous sequence number",
        "CTL_Panel2BadSequence= Number must be greater than {0}."
    })
    private boolean checkValidity() {
        if (getComponent().getSecondNumber().isEmpty()) {
            setMessage(Bundle.CTL_Panel1InputRequired());
            return false;
        }
        try {
            secondNumber = Integer.parseInt(getComponent().getSecondNumber());
            if (secondNumber < 0) {
                setMessage(Bundle.CTL_Panel1NegativeNumber());
                return false;
            }
            if (secondNumber <= firstNumber) {
                setMessage(Bundle.CTL_Panel2BadSequence(firstNumber));
                return false;
            }

            setMessage(null);
            return true;
        } catch (NumberFormatException e) {
            setMessage(Bundle.CTL_Panel1BadNumber());
            return false;
        }
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
    public boolean isFinishPanel() {
        return isFinishPanel;
    }
}
