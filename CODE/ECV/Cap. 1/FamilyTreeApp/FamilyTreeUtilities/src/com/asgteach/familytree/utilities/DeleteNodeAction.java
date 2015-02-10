/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.utilities;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

/*
 * Specify a system DeleteAction (org.openide.actions.DeleteAction
 * Note that this action by default is already integrated into 
 * the Edit menu with Delete display name and an icon
 * 
 * We add the action to the Toolbars/File and specify an icon base
 * 
 * We do not have a fallback implementation here so we do not implement
 * ActionListener and we do not provide an actionPerformed method
 * 
 * We provide an Action Map Key (called ACTION_MAP_DELETE_KEY) which we
 * use in TopComponents to define a DeleteAction
 */

@NbBundle.Messages({
    "CTL_DeleteAction=&Delete"
})
public final class DeleteNodeAction  {

    @ActionRegistration(
            iconBase = "com/asgteach/familytree/utilities/delete.gif",
    displayName = "#CTL_DeleteAction")
    @ActionID(category = "File", id = "org.openide.actions.DeleteAction")
    @ActionReferences({
        @ActionReference(path = "Toolbars/File", position = 100),            
    })
    public static final String ACTION_MAP_DELETE_KEY = "delete";

}
