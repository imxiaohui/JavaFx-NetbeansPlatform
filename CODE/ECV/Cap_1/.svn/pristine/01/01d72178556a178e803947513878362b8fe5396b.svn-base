/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import com.asgteach.familytree.capabilityinterfaces.AddPictureCapability;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "PersonNode",
id = "com.asgteach.familytree.actions.AddPictureAction")
@ActionRegistration(
    iconBase = "com/asgteach/familytree/actions/add-picIcon.png",
displayName = "#CTL_AddPictureAction")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = -100, separatorAfter = -50),
    @ActionReference(path = "Toolbars/File", position = 150)
})
@Messages("CTL_AddPictureAction=Add Picture")
public final class AddPictureAction implements ActionListener {

    private final List<AddPictureCapability> context;

    public AddPictureAction(List<AddPictureCapability> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        for (AddPictureCapability capability : context) {
            capability.addPic();
        }
    }
}
