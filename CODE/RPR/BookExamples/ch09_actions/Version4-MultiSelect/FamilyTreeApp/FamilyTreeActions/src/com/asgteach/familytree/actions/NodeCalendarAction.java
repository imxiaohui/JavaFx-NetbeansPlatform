/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import com.asgteach.familytree.capabilities.CalendarCapability;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Nodes",
        id = "com.asgteach.familytree.actions.NodeCalendarAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/calendar.png",
        displayName = "#CTL_NodeCalendarAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1200),
    @ActionReference(path = "Toolbars/File", position = 300),
    @ActionReference(path = "Shortcuts", name = "M-E")
})
@Messages("CTL_NodeCalendarAction=Calendar")
public final class NodeCalendarAction implements ActionListener {

    private final List<CalendarCapability> context;

    public NodeCalendarAction(List<CalendarCapability> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        for (CalendarCapability calendarCapability : context) {
            calendarCapability.doCalendar();
        }
    }
}
