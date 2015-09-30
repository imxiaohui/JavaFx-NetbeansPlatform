/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import com.asgteach.familytree.capabilityinterfaces.HappyMammothCapability;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Extra",
    id = "com.asgteach.familytree.actions.HappyMammothAction")
@ActionRegistration(
    iconBase = "com/asgteach/familytree/actions/mammoth_happy.png",
    displayName = "#CTL_HappyMammoth")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 200),
    @ActionReference(path = "Toolbars/File", position = 200)
})
@Messages("CTL_HappyMammoth=Happy Mammoth")
public final class HappyMammothAction implements ActionListener {

    private final List<HappyMammothCapability> context;

    public HappyMammothAction(List<HappyMammothCapability> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        for (HappyMammothCapability happyMammothCapability : context) {
            happyMammothCapability.doHappyMammoth();
        }
    }
}
