/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.adminmodule;

import com.asgteach.rolemodel.api.User;
import com.asgteach.rolemodel.api.UserRole;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(category = "Tools",
        id = "com.asgteach.adminmodule.ChangeRole")
@ActionRegistration(displayName = "#CTL_ChangeRole")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0, separatorAfter = 50)
})
@Messages("CTL_ChangeRole=Change Role")
public final class ChangeRole implements ActionListener {

    private DialogDescriptor dd = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        final LoginPanel panel = new LoginPanel();
        dd = new DialogDescriptor(panel, Bundle.CTL_ChangeRole(), true, (ActionEvent ae) -> {
            if (ae.getSource() == DialogDescriptor.OK_OPTION) {
                // Check login
                UserRole userRole = Lookup.getDefault().lookup(UserRole.class);
                if (userRole != null) {
                    User thisUser = userRole.findUser(
                            panel.getUserName(), panel.getPassword());
                    WindowManager wm = WindowManager.getDefault();
                    if (thisUser != null) {
                        // switch to new role
                        wm.setRole(thisUser.getRole());
                        // Using null enables all options to close dialog
                        dd.setClosingOptions(null);
                    } else {
                        // Bad login, try again
                        dd.getNotificationLineSupport().setErrorMessage(
                                Bundle.LoginError());
                    }
                }
            }
        });
        // Allow Cancel to close the dialog, but OK should not
        dd.setClosingOptions(new Object[]{DialogDescriptor.CANCEL_OPTION});
        dd.createNotificationLineSupport();
        DialogDisplayer.getDefault().notify(dd);
    }
}
