/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreesupport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "com.asgteach.familytreesupport.AddNameAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytreesupport/personIcon.png",
        displayName = "#CTL_AddNameAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1300, separatorAfter = 1350),
    @ActionReference(path = "Toolbars/File", position = 300)
})
@Messages("CTL_AddNameAction=Add Name")
public final class AddNameAction implements ActionListener {

    private final Droppable context;

    public AddNameAction(Droppable context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        NotifyDescriptor.InputLine inputLine = new NotifyDescriptor.InputLine("Text:", "Add Name");
        Object result = DialogDisplayer.getDefault().notify(inputLine);
        if (result == NotifyDescriptor.OK_OPTION) {
            String text = inputLine.getInputText();
            if (!text.isEmpty()) {
                context.drop(text);
            }
        }
    }
}
