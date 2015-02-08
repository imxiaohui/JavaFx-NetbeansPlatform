/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.manipulations;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.io.File;
import java.io.IOException;
import javax.swing.Action;
import org.netbeans.api.actions.Openable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

@ActionID(
        category = "Tools",
        id = "com.asgteach.manipulations.FileManipulations"
)
@ActionRegistration(
        displayName = "#CTL_FileManipulations"
)
@ActionReference(path = "Menu/Tools", position = 0)
@Messages("CTL_FileManipulations=File Manipulations")
public final class FileManipulations implements ActionListener {

    private final InputOutput io;
    private final OutputWriter writer;
    private static final String FILENAME = "myfile.txt";

    public FileManipulations() {
        io = IOProvider.getDefault().getIO(Bundle.CTL_FileManipulations(), false);
        writer = io.getOut();
    }

    private void displayMessage(String msg) {
        writer.println(msg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        io.select(); // select this tab
        try {
            writer.reset(); // clear the window each time

            displayMessage("Exploring NetBeans Platform FileObjects/DataObjects/DataNodes:");
            String home = System.getProperty("user.home");
            File dir = new File(home + "/FamilyTreeData");
            FileObject myfolder = FileUtil.toFileObject(dir);
            if (myfolder == null) {
                displayMessage("Creating folder " + dir.getPath());

                // Create file
                myfolder = FileUtil.createFolder(dir);
            }
            if (myfolder != null) {
                displayMessage("Using folder " + myfolder.getPath());
                // Is there a file called myfile.txt in this folder?
                FileObject myfile = myfolder.getFileObject(FILENAME);

                if (myfile == null) {
                    // No, create the file
                    displayMessage("Creating file " + myfolder.getPath() + "/" + FILENAME);
                    // Create file
                    myfile = myfolder.createData(FILENAME);
                }
                if (myfile != null) {
                    // Show that the FileObject has a MIME Type
                    displayMessage(myfile.getNameExt() + " MIME Type: " + myfile.getMIMEType());
                    // Show that the FileObject has a lookup
                    Openable open_impl = myfile.getLookup().lookup(Openable.class);
                    if (open_impl != null) {
                        displayMessage("Openable found in fileobject");
                        open_impl.open();
                    }
                    // Find the DataObject associated with the file
                    // This uses static DataObject method find()
                    DataObject dob = DataObject.find(myfile);
                    if (dob != null) {
                        displayMessage("Found DataObject " + dob.getName() + " for " + myfile.getNameExt());
                        // Show that the DataObject also has a lookup
                        open_impl = dob.getLookup().lookup(Openable.class);
                        if (open_impl != null) {
                            displayMessage("Openable found in dataobject");
                            open_impl.open();
                        }

                        // Get the FileObject from the DataObject
                        FileObject myfob = dob.getPrimaryFile();
                        if (myfob != null) {
                            displayMessage("Got FileObject " + myfob.getNameExt() + " from DataObject");
                            displayMessage("Display Contents:");
                            for (String line : myfob.asLines()) {
                                displayMessage(line);
                            }
                        }
                        // Get the Node that represents the DataObject
                        Node node = dob.getNodeDelegate();
                        if (node != null) {
                            displayMessage("Node is: " + node.getDisplayName());
                            Image image = node.getIcon(BeanInfo.ICON_COLOR_16x16);
                            if (image != null) {
                                displayMessage("Got the Node's Icon " + image.toString());
                            }
                            displayMessage("-------Node Context Menu Actions-------");
                            for (Action action : node.getActions(true)) {
                                if (action == null) {
                                    // separator
                                    displayMessage("-------");
                                } else {
                                    displayMessage("Action " + action.toString());
                                }
                            }
                            // Get the DataObject from the DataNode
                            if (node instanceof DataNode) {
                                DataNode dataNode = (DataNode) node;
                                displayMessage("Node is a DataNode: " + dataNode.getDisplayName());
                                DataObject mydob = dataNode.getDataObject();
                                if (mydob != null) {
                                    displayMessage("Got the DataObject from the DataNode: " + mydob.getName());
                                }
                            }
                            // Better to use Lookup
                            DataObject dob2 = node.getLookup().lookup(DataObject.class);
                            if (dob2 != null) {
                                displayMessage("Got the DataObject from the Node's Lookup: " + dob2.getName());
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
