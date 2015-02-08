/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreesupport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "com.asgteach.familytreesupport.GetInfoAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytreesupport/info.png",
        displayName = "#CTL_GetInfoAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1450, separatorAfter = 1475),
    @ActionReference(path = "Loaders/text/x-familytree/Actions", position = 150, separatorBefore = 125)
})
@Messages("CTL_GetInfoAction=Get Info")
public final class GetInfoAction implements ActionListener {

    private final FamilyTreeDataObject context;
    private static final Logger logger = Logger.getLogger(GetInfoAction.class.getName());

    public GetInfoAction(FamilyTreeDataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        FileObject f = context.getPrimaryFile();
        String displayName = FileUtil.getFileDisplayName(f);
        String name = f.getName();
        StringBuilder sb = new StringBuilder("Name: ").append(name);
        sb.append(":\nExtention: ").append(FileUtil.getExtension(displayName));
        sb.append("\nMIME Type: ").append(FileUtil.getMIMEType(f));
        try {
            sb.append("\nFile Size: ").append(f.getSize());
            sb.append("\nLines: ").append(f.asLines().size());
            sb.append("\nLast Modified: ").append(f.lastModified());
            sb.append("\nFull Path:\n").append(f.getPath());
            NotifyDescriptor nd = new NotifyDescriptor.Message(sb.toString());
            DialogDisplayer.getDefault().notify(nd);
        } catch (IOException e) {
            logger.log(Level.WARNING, null, e);
        }
    }
}
