/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import com.asgteach.customer.api.Customer;
import com.asgteach.customer.api.CustomerStore;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Tools",
id = "com.asgteach.newpizza.OrderPizza")
@ActionRegistration(displayName = "#CTL_OrderPizza")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 150)
})
@Messages("CTL_OrderPizza=Order Pizza")
public final class OrderPizzaAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        WizardDescriptor wiz = new WizardDescriptor(new PizzaWizardIterator());
        // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
        // {1} will be replaced by WizardDescriptor.Iterator.name()
        wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
        wiz.setTitle(Bundle.CTL_OrderPizza());
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            // Store the customer with module CustomerStore
            CustomerStore custStore = Lookup.getDefault().lookup(CustomerStore.class);
            if (custStore != null) {
                Customer customer = (Customer) wiz.getProperty(IdentifyCustomerController.PROP_CUSTOMER);
                custStore.storeCustomer(customer);
                PizzaOrder p = (PizzaOrder) wiz.getProperty(BuildPizzaController.PROP_PIZZA_ORDER);
                StringBuilder sb = new StringBuilder();
                sb.append("Thank you, ").append(customer.getName()).append(", for your order!\n");
                sb.append(p.getPizzaSize());
                sb.append(" Pizza\n");
                if (!p.getToppings().isEmpty()) {
                    sb.append("With ");
                    for (String topping : p.getToppings()) {
                        sb.append(topping).append(", ");
                    }
                    sb.append("\n");
                }
                if (p.isPickup()) {
                    sb.append("For Pickup");
                } else {
                    sb.append("For Delivery");
                }
                DialogDisplayer.getDefault().notify(
                    new NotifyDescriptor.Message(sb.toString()));
            }
        }
    }
}
