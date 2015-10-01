/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "com.asgteach.familytree.actions.RefreshAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/refresh.png",
        displayName = "#CTL_RefreshAction",
        key = "MyCoolRefreshAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1300),
    @ActionReference(path = "Toolbars/File", position = 500),
    @ActionReference(path = "Shortcuts", name = "D-R")
})
@Messages("CTL_RefreshAction=Refresh")
public final class RefreshAction implements ActionListener {
    
    private RefreshPanel panel = null;
    private DialogDescriptor dd = null;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(panel == null){
            panel = new RefreshPanel();
        }
        if(dd == null){
            dd = new DialogDescriptor(panel, Bundle.CTL_CalendarTitle());
        }
        
        if(DialogDisplayer.getDefault().notify(dd) == NotifyDescriptor.OK_OPTION){
            System.out.println("User clicked ok");
        }
    }
}
