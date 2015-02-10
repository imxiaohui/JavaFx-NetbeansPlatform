/*
 * To change this template, choose Tools | Templates
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

@ActionID(category = "OpenNode",
id = "com.asgteach.familytree.actions.OpenNodeAction")
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/open-file-icon.png",
displayName = "#CTL_OpenNodeAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1200),
    @ActionReference(path = "Toolbars/File", position = -290)
})
@Messages("CTL_OpenNodeAction=&Open")
public final class OpenNodeAction implements ActionListener {

    private final Openable context;

    public OpenNodeAction(Openable context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        context.open();
    }
}
