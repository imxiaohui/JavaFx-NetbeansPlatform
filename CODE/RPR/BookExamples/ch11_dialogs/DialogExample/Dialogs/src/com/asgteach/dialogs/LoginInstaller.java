/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.dialogs;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.modules.OnStart;
import org.openide.util.NbBundle.Messages;

@OnStart
public class LoginInstaller implements Runnable {

    private final LoginPanel panel = new LoginPanel();
    private DialogDescriptor dd = null;

    // stub method that says if the username and password
    // are the same then the login is good!
    private boolean login(String username, String password) {
        return username.equals(password) && !username.isEmpty();
    }

    @Messages({
        "LoginTitle=Please Login",
        "LoginError=Incorrect user name or password"
    })
    @Override
    public void run() {
        // @Onstart runs in a background thread by default
        // But use notifyLater to safely display the dialog.
        // Create a dialog using the LoginPanel and LoginTitle, make it modal,
        // and specify action listener
        dd = new DialogDescriptor(panel, Bundle.LoginTitle(), true, (ActionEvent ae) -> {
            // We need to listen for button pressed because we are using
            // notifyLater to display the dialog   
            if (ae.getSource() == DialogDescriptor.CANCEL_OPTION) {
                // Adios
                System.out.println("Canceled!");
                LifecycleManager.getDefault().exit();
            } else {
                // Check login
                if (login(panel.getUserName(), panel.getPassword())) {
                    // Using null enables all options to close dialog
                    // (this is retroactive, so the dialog will close
                    // and the app will appear)
                    dd.setClosingOptions(null);
                } else {
                    // Bad login, try again
                    dd.getNotificationLineSupport().setErrorMessage(
                            Bundle.LoginError());
                }
            }
        });
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
