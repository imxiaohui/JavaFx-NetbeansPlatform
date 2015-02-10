/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import com.asgteach.familytree.capabilityinterfaces.ReloadableViewCapability;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "ReloadNode",
id = "com.asgteach.familytree.utilities.ReloadAction")
@ActionRegistration(displayName = "#CTL_ReloadAction")
//@ActionReferences({})
@Messages("CTL_ReloadAction=&Refresh")
public final class ReloadAction implements ActionListener {
    
    private final ReloadableViewCapability context;
    
    public ReloadAction(ReloadableViewCapability context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ReloadAction: actionPerformed");
        try {
            context.reloadChildren();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
