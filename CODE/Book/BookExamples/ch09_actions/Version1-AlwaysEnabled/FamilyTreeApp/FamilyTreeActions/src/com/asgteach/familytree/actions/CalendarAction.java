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
        category = "Tools",
        id = "com.asgteach.familytree.actions.CalendarAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/calendar.png",
        displayName = "#CTL_CalendarAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0, separatorAfter = 50),
    @ActionReference(path = "Toolbars/File", position = 300),
    @ActionReference(path = "Shortcuts", name = "M-E")
})
@Messages({
    "CTL_CalendarAction=Calendar",
    "CTL_CalendarTitle=Calendar Action"
})
public final class CalendarAction implements ActionListener {

    private CalendarPanel panel = null;
    private DialogDescriptor dd = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (panel == null) {
            panel = new CalendarPanel();
        }
        if (dd == null) {
            dd = new DialogDescriptor(panel, Bundle.CTL_CalendarTitle());
        }
        // display & block
        if (DialogDisplayer.getDefault().notify(dd) == NotifyDescriptor.OK_OPTION) {
            // User clicked OK . . .
        }  
    }
}
