/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.appconfigfile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

@ActionID(
        category = "Tools",
        id = "com.asgteach.appuserfile.ConfigFileAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/appconfigfile/Favorites.png",
        displayName = "#CTL_ConfigFile"
)
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 25, separatorAfter = 37),
    @ActionReference(path = "Toolbars/File", position = 350)
})
@Messages({
    "CTL_ConfigFile=Config File",
    "CTL_configFileTab=Config File Contents"
})
public final class ConfigFileAction implements ActionListener {

    private final InputOutput io;
    private final OutputWriter writer;
    private static final String filename = "configFile/SimpsonFamilyTree.xml";

    public ConfigFileAction() {
        io = IOProvider.getDefault().getIO(Bundle.CTL_configFileTab(), false);
        writer = io.getOut();
    }

    private void displayMessage(String msg) {
        writer.println(msg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            writer.reset();                    // clear the window each time
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return;
        }
        io.select();                            // select this tab
        File configFile = InstalledFileLocator.getDefault().locate(
                filename,
                "com.asgteach.appconfigfile",
                false);
        FileObject fo = null;
        if (configFile != null) {
            fo = FileUtil.toFileObject(configFile);
            if (fo != null) {
                displayMessage("File " + fo.getNameExt() + " found!");
                try {
                    for (String lines : fo.asLines()) {
                        displayMessage(lines);
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        if (fo == null || configFile == null) {
            displayMessage("Warning: File " + filename + " is missing!");
        }
    }
}
