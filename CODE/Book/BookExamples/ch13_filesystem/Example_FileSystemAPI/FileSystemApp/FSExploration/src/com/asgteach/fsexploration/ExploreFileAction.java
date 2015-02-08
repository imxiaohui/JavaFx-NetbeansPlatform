/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.fsexploration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

@ActionID(
        category = "Tools",
        id = "com.asgteach.fsexploration.ExploreFileAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/fsexploration/ExploreFS.png",
        displayName = "#CTL_ExploreFileAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0, separatorAfter = 50),
    @ActionReference(path = "Toolbars/File", position = 300)
})
@Messages({
    "CTL_ExploreFileAction=Explore File Actions",
    "CTL_FileActionTab=Explore File Actions"
})
public final class ExploreFileAction implements ActionListener {

    private final InputOutput io;
    private final OutputWriter writer;

    public ExploreFileAction() {
        io = IOProvider.getDefault().getIO(Bundle.CTL_FileActionTab(), false);
        writer = io.getOut();
    }

    private void displayMessage(String msg) {
        writer.println(msg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            writer.reset(); // clear the window each time
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return;
        }
        io.select(); // select this tab
        displayMessage("Exploring NetBeans Platform FileObjects:");
        // Look in the User's home directory 
        String home = System.getProperty("user.home");
        File dir = new File(home + "/FamilyTreeData");
        FileObject myfolder = FileUtil.toFileObject(dir);
        if (myfolder == null) {
            displayMessage("Creating folder " + dir.getPath());
            try {
                // Create file
                myfolder = FileUtil.createFolder(dir);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        if (myfolder != null) {
            FileObject myfile;
            displayMessage("Using folder " + myfolder.getPath());
            // Is there a file called myfile.txt in this folder?
            myfile = myfolder.getFileObject("myfile.txt");

            if (myfile == null) {
                // No, create the file
                displayMessage("Creating file " + myfolder.getPath() + "/myfile.txt");
                try {
                    // Create file
                    myfile = myfolder.createData("myfile.txt");
                } catch (IOException ex) {
                    displayMessage("Can't create file " + myfolder.getPath() + "/myfile.txt");
                    Exceptions.printStackTrace(ex);
                }
            }
            if (myfile != null) {
                // write some text to file
                if (myfile.canWrite()) {
                    displayMessage("can write " + myfile.getPath());
                    try (PrintWriter output = new PrintWriter(myfile.getOutputStream())) {
                        output.println("This is some text");
                        for (int i = 0; i < 2; i++) {
                            output.println("Line number " + (i + 2));
                        }
                        displayMessage("Text written to file " + myfile.getNameExt());
                    } catch (IOException ex) {
                        displayMessage("Warning: problems writing file " + myfile.getNameExt());
                        Exceptions.printStackTrace(ex);
                    }
                }
                // read  file
                if (myfile.canRead()) {
                    displayMessage("can read " + myfile.getPath());
//                    try (BufferedReader input = new BufferedReader(new InputStreamReader(myfile.getInputStream()))) {
//                        String line;
//                        while ((line = input.readLine()) != null) {
//                            displayMessage(line);
//                        }                       
//                    } catch (IOException ex) {
//                        Exceptions.printStackTrace(ex);
//                    }
                    try {
                        for (String line : myfile.asLines()) {
                            displayMessage(line);
                        }
                    } catch (IOException ex) {
                        displayMessage("Warning: problems reading file " + myfile.getNameExt());
                        Exceptions.printStackTrace(ex);
                    }
                }

                // rename, requires a lock
                FileLock lock = null;
                try {
                    lock = myfile.lock();
                    myfile.rename(lock, "mynewname", myfile.getExt());
                    displayMessage("Renamed file to " + myfile.getNameExt());

                } catch (IOException ex) {
                    displayMessage("Warning: " + ex.getLocalizedMessage());
                    Exceptions.printStackTrace(ex);

                } finally {
                    if (lock != null) {
                        lock.releaseLock();
                    }
                }
                try {
                    // delete file
                    myfile.delete();
                    displayMessage("Deleted file " + myfile.getNameExt());
                } catch (IOException ex) {
                    displayMessage("Warning: " + ex.getLocalizedMessage());
                    Exceptions.printStackTrace(ex);
                }
            }
        }

    }
}
