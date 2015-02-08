/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.adminmodule;

import com.asgteach.rolemodel.api.User;
import com.asgteach.rolemodel.api.UserRole;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.modules.OnStart;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

@OnStart
public class RoleLogin implements Runnable {

    private final LoginPanel panel = new LoginPanel();
    private DialogDescriptor dd = null;

    @Override
    public void run() {
        // Create a dialog using the LoginPanel and LoginTitle, make it modal,
        // and specify action listener
        dd = new DialogDescriptor(panel, Bundle.LoginTitle(), true, ((ActionEvent ae) -> {
            // We need to listen for button pressed because we are using
            // notifyLater to display the dialog
            if (ae.getSource() == DialogDescriptor.CANCEL_OPTION) {
                // Adios
                System.out.println("Canceled!");
                LifecycleManager.getDefault().exit();
            } else {
                // Check login
                UserRole userRole = Lookup.getDefault().lookup(UserRole.class);
                if (userRole != null) {
                    User thisUser = userRole.findUser(panel.getUserName(), panel.getPassword());
                    WindowManager wm = WindowManager.getDefault();
                    if (thisUser != null) {
                        // switch to new role
                        wm.setRole(thisUser.getRole());
                        // Using null enables all options to close dialog
                        // (this is retroactive, so the dialog will close
                        // and the app will appear)
                        dd.setClosingOptions(null);
                    } else {
                        // Bad login, try again
                        dd.getNotificationLineSupport().setErrorMessage(
                                Bundle.LoginError());
                    }
                } else {
                    // No UserRole instance, bail!
                    // Adios
                    System.out.println("No UserRole!");
                    LifecycleManager.getDefault().exit();
                }
            }
        }));
        // Specify an empty array to prevent any option from closing the dialog
        dd.setClosingOptions(new Object[]{});
        dd.createNotificationLineSupport();
        // Define a property listener in case the dialog is closed
        dd.addPropertyChangeListener((PropertyChangeEvent pce) -> {
            // look for CLOSED_OPTION
            if (pce.getPropertyName().equals(DialogDescriptor.PROP_VALUE)
                    && pce.getNewValue() == DialogDescriptor.CLOSED_OPTION) {
                // Adios
                System.out.println("Closed!");
                LifecycleManager.getDefault().exit();
            }
        });
        // notifyLater will display the dialog in the EDT
        DialogDisplayer.getDefault().notifyLater(dd);
    }
}
