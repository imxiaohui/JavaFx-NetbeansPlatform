/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.trashcan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "TrashNode",
        id = "com.asgteach.familytree.trashcan.EmptyTrashAction"
)
@ActionRegistration(
        displayName = "#CTL_EmptyTrashAction"
)
@Messages("CTL_EmptyTrashAction=Empty Trash")
public final class EmptyTrashAction implements ActionListener {

    private final EmptyCapability context;

    public EmptyTrashAction(EmptyCapability context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.emptyTrash();
    }
}
