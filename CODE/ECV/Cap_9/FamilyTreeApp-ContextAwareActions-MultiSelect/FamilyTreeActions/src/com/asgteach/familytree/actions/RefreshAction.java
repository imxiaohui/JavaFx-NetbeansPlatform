/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;


@Messages({"CTL_RefreshAction=Refresh","CTL_RefreshTitle=Refresh Action"})
public final class RefreshAction {
    
    @ActionID(
            category = "File",
            id = "com.asgteach.familytree.actions.RefreshAction"
    )
    @ActionRegistration(
            iconBase = "com/asgteach/familytree/actions/refresh.png",
            displayName = "#CTL_RefreshAction"
    )
    @ActionReferences({
        @ActionReference(path = "Menu/File", position = 1300),
        @ActionReference(path = "Toolbars/File", position = 500),
        @ActionReference(path = "Shortcuts", name = "D-R")
    })
    public static final String REFRESH_ACTION = "MyCoolRefreshAction";
}
