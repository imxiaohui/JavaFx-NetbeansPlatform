/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.filesystem.viewer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOColorLines;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

@ActionID(
        category = "Tools",
        id = "com.asgteach.filesystem.viewer.ViewSysFSAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/filesystem/viewer/fileSysIcon.png",
        displayName = "#CTL_ViewSysFSAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = -100, separatorAfter = -50),
    @ActionReference(path = "Toolbars/File", position = 375)
})
@Messages("CTL_ViewSysFSAction=View System FileSystem")
public final class ViewSysFSAction implements ActionListener {
    private final InputOutput io;
    private final OutputWriter writer;

    public ViewSysFSAction() {
        io = IOProvider.getDefault().getIO(Bundle.CTL_ViewSysFSAction(), false);
        writer = io.getOut();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileObject root = FileUtil.getConfigRoot();
        // "System FileSystem" tab is created in output window for writing the list of tags

        io.select(); //System FileSystem tab is selected

        try {
            writer.reset(); // clear the output window
            displayChildren(root);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            writer.flush();
            writer.close();
        }

    }

    private void displayChildren(FileObject fo) throws IOException {
        if (fo.isFolder()) {    // display folder (orange)            
            writeMsg(FileUtil.getFileDisplayName(fo), Color.ORANGE);
            for (FileObject childFileObject : fo.getChildren()) {
                displayChildren(childFileObject);
            }
        } else {     // display file (blue)
            writeMsg(FileUtil.getFileDisplayName(fo), Color.BLUE);
            Enumeration<String> attrNames = fo.getAttributes();
            while (attrNames.hasMoreElements()) {
                // display attributes (magenta)
                writeMsg("Attribute " + attrNames.nextElement(), Color.MAGENTA);
            }
        }
    }

    private void writeMsg(String str, Color color) {
        try {
            if (IOColorLines.isSupported(io)) {
                IOColorLines.println(io, str, color);
            } else {
                writer.println(str);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
