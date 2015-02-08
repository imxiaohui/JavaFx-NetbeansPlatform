/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.actions.Openable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "OpenNodes",
        id = "com.asgteach.familytree.actions.OpenAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/open.png",
        displayName = "#CTL_OpenAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1100, separatorAfter = 1150),
    @ActionReference(path = "Toolbars/File", position = 200)
})
@Messages("CTL_OpenAction=Open")
public final class OpenAction implements ActionListener {

    private final Openable context;

    public OpenAction(Openable context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.open();
    }
}
