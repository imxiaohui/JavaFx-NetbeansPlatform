/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.newpizza;

import com.asgteach.customer.api.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Set;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Tools",
        id = "com.asgteach.newpizza.OrderPizza")
@ActionRegistration(displayName = "#CTL_OrderPizza")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 150)
})
@Messages("CTL_OrderPizza=Order Pizza")
@SuppressWarnings("unchecked")
public final class OrderPizzaAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wiz = new WizardDescriptor(new PizzaWizardIterator());
        // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
        // {1} will be replaced by WizardDescriptor.Iterator.name()
        wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
        wiz.setTitle(Bundle.CTL_OrderPizza());
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            //...do something...
            Set<PizzaOrder> pizzaSet = wiz.getInstantiatedObjects();
            if (pizzaSet.iterator().hasNext()) {
                PizzaOrder p = pizzaSet.iterator().next();
                Customer customer = p.getCustomer();
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
