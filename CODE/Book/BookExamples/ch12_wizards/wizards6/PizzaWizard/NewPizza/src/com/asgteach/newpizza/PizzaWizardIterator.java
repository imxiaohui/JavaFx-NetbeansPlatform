/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import com.asgteach.customer.api.Customer;
import com.asgteach.customer.api.CustomerStore;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

public final class PizzaWizardIterator implements
        WizardDescriptor.ProgressInstantiatingIterator<WizardDescriptor>,
        PropertyChangeListener {

    // Example of invoking this wizard:
    // @ActionID(category="...", id="...")
    // @ActionRegistration(displayName="...")
    // @ActionReference(path="Menu/...")
    // public static ActionListener run() {
    //     return new ActionListener() {
    //         @Override public void actionPerformed(ActionEvent e) {
    //             WizardDescriptor wiz = new WizardDescriptor(new PizzaWizardIterator());
    //             // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
    //             // {1} will be replaced by WizardDescriptor.Iterator.name()
    //             wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
    //             wiz.setTitle("...dialog title...");
    //             if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
    //                 ...do something...
    //             }
    //         }
    //     };
    // }
    private int index;
    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;
    private WizardDescriptor wizard = null;
    // give panels variable names so we can add/remove them as needed
    IdentifyCustomerController icc = new IdentifyCustomerController();
    NewCustomerController ncc = new NewCustomerController();
    BuildPizzaController bpc = new BuildPizzaController();
    DeliveryInfoController delic = new DeliveryInfoController();
    ConfirmOrderController confoc = new ConfirmOrderController();

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        if (panels == null) {
            System.out.println("initializing panels");
            panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
            panels.add(icc);
            panels.add(ncc);
            panels.add(bpc);
            panels.add(delic);
            panels.add(confoc);

            // Become a ChangeListener for IdentifyCustomerController and
            // BuildPizzaController
            icc.addPropertyChangeListener(this);
            bpc.addPropertyChangeListener(this);
            updateSteps();
        }
        return panels;
    }

    private void updateSteps() {
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + " out of " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }
    // If nothing unusual changes in the middle of the wizard, simply:
    private final EventListenerList listeners = new EventListenerList();

    @Override
    public void addChangeListener(ChangeListener l) {
        listeners.add(ChangeListener.class, l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(ChangeListener.class, l);
    }
    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case IdentifyCustomerController.PROP_IS_NEW_CUSTOMER:
                if (icc.isIsNewCustomer()) {
                    panels.add(1, ncc);
                } else {
                    panels.remove(ncc);
                }   updateSteps();
            fireChangeEvent(this, 0, 1);
                break;
            case BuildPizzaController.PROP_IS_PICKUP:
                if (bpc.isIsPickup()) {
                    panels.remove(delic);
                } else {
                    panels.add(panels.size() - 1, delic);
                }   updateSteps();
            fireChangeEvent(this, 0, 1);
                break;
        }
    }

    protected final void fireChangeEvent(Object source, int oldState, int newState) {
        if (oldState != newState) {
            ChangeEvent ev = new ChangeEvent(source);
            for (ChangeListener listener : listeners.getListeners(ChangeListener.class)) {
                listener.stateChanged(ev);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<PizzaOrder> instantiate(ProgressHandle ph) throws IOException {
        System.out.println("Starting instantiate");
        Set<PizzaOrder> pizzaSet = new HashSet<>();
        PizzaOrder p = (PizzaOrder) wizard.getProperty(BuildPizzaController.PROP_PIZZA_ORDER);
        pizzaSet.add(p);
        Customer customer = (Customer) this.wizard.getProperty(IdentifyCustomerController.PROP_CUSTOMER);
        ph.start(5);
        try {
            ph.progress("Storing customer  " + customer.getName(), 1);
            Thread.sleep(1500);
            if (customer.getName().contains("JoeCriminal")) {
                throw new IOException(" Cannot sell to JoeCriminal");
            }
            CustomerStore custStore = Lookup.getDefault().lookup(CustomerStore.class);
            if (custStore != null) {
                custStore.storeCustomer(customer);
            }
            ph.progress("Building " + p.getPizzaSize() + " pizza", 2);
            Thread.sleep(2000);
            ph.progress("Place toppings " + p.getToppings().toString(), 3);
            Thread.sleep(2000);
            ph.progress("Cooking pizza", 4);
            Thread.sleep(2000);
            ph.progress("Ready!", 5);
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            Exceptions.printStackTrace(e);
        }
        System.out.println("Finishing up");
        ph.finish();
        return pizzaSet;
    }

    @Override
    public Set<PizzaOrder> instantiate() throws IOException {
        assert false : "This method cannot be called if the class implements WizardDescriptor.ProgressInstantiatingWizardIterator.";
        return null;
    }

    @Override
    public void initialize(WizardDescriptor wd) {
        System.out.println("initialize");
        this.wizard = wd;
    }

    @Override
    public void uninitialize(WizardDescriptor wd) {
        System.out.println("uninitialize");
        this.panels = null;
        this.wizard = null;

    }
}
