/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.datatransfer.NewType;

@ActionID(
        category = "NewNode",
        id = "org.openide.actions.NewAction")
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/personIcon.png",
        displayName = "#CTL_NewPersonAction")

@ActionReferences({
    @ActionReference(path = "Menu/File", position = 75),
    @ActionReference(path = "Toolbars/File", position = 50)
})
@Messages("CTL_NewPersonAction=New &Person")
public final class NewPersonAction implements ActionListener {

    private final NewType context;

    public NewPersonAction(NewType context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            context.create();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
