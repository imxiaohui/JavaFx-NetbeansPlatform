/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreesupport;

import java.io.IOException;
import java.util.List;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_FamilyTree_LOADER=Files of FamilyTree"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_FamilyTree_LOADER",
        mimeType = "text/x-familytree",
        extension = {"ftr", "FTR"}
)
@DataObject.Registration(
        mimeType = "text/x-familytree",
        iconBase = "com/asgteach/familytreesupport/check.png",
        displayName = "#LBL_FamilyTree_LOADER",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/text/x-familytree/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class FamilyTreeDataObject extends MultiDataObject {

    public FamilyTreeDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        /*
        Utility method to register editor for this DataObject. 
        Call it from constructor with appropriate mimeType. 
        The system will make sure that appropriate cookies 
        (Openable, Editable, CloseCookie, EditorCookie, SaveAsCapable, LineCookie) 
        are registered into getCookieSet().

        The selected editor is MultiView component, 
        if requested (this requires presence of the MultiView API in the system. 
        Otherwise it is plain CloneableEditor.

        Parameters:
            mimeType - mime type to associate with
            useMultiview - should the used component be multiview?
        */
        registerEditor("text/x-familytree", true);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_FamilyTree_EDITOR",
            iconBase = "com/asgteach/familytreesupport/check.png",
            mimeType = "text/x-familytree",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "FamilyTree",
            position = 1000
    )
    @Messages("LBL_FamilyTree_EDITOR=Text")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.create(new FamilyTreeChildFactory(this),
                true), getLookup());
    }

    private static class FamilyTreeChildFactory extends ChildFactory<String> {

        private final FileObject fileObject;

        public FamilyTreeChildFactory(FamilyTreeDataObject dataObject) {
            fileObject = dataObject.getPrimaryFile();
            fileObject.addFileChangeListener(new FileChangeAdapter() {
                @Override
                public void fileChanged(FileEvent fe) {
                    refresh(true);
                }
            });
        }

        @Override
        protected boolean createKeys(List<String> list) {
            // create a String for each line in the file            
            try {
                // skip blank lines
                for (String line : fileObject.asLines()) {
                    if (!line.isEmpty()) {
                        list.add(line);
                    }
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(String key) {
            AbstractNode childNode = new AbstractNode(Children.LEAF);
            childNode.setDisplayName(key);
            childNode.setIconBaseWithExtension("com/asgteach/familytreesupport/personIcon.png");
            return childNode;
        }
    }
}
