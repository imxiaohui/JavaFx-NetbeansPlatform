/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import com.asgteach.familytree.capabilities.RefreshCapability;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "RootNode",
    id = "com.asgteach.familytree.actions.RefreshAction"
)
@ActionRegistration(
    iconBase = "com/asgteach/familytree/actions/refresh.png",
    displayName = "#CTL_RefreshAction"
)

@Messages("CTL_RefreshAction=Refresh")
public final class RefreshAction implements ActionListener {

    private final RefreshCapability context;
    
    public RefreshAction(RefreshCapability context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            context.refresh();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
