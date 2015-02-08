/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfiletype;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.netbeans.api.xml.cookies.CheckXMLCookie;
import org.netbeans.api.xml.cookies.ValidateXMLCookie;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.cookies.EditorCookie;
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
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Messages({
    "LBL_FamilyTree_LOADER=Files of FamilyTree"
})
@MIMEResolver.NamespaceRegistration(
        displayName = "#LBL_FamilyTree_LOADER",
        mimeType = "text/familytree+xml",
        elementNS = {"familytree"},
        position = 0
)
@DataObject.Registration(
        mimeType = "text/familytree+xml",
        iconBase = "com/asgteach/familytree/personfiletype/FamilyTreeIcon.png",
        displayName = "#LBL_FamilyTree_LOADER",
        position = 0
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/text/familytree+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class FamilyTreeDataObject extends MultiDataObject {

    public FamilyTreeDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/familytree+xml", true);            // true means use MultiView Editor
        InputSource inputSource = DataObjectAdapters.inputSource(this);
        CheckXMLCookie checkCookie = new CheckXMLSupport(inputSource);
        getCookieSet().add(checkCookie);
        ValidateXMLCookie validateXMLCookie = new ValidateXMLSupport(inputSource);
        getCookieSet().add(validateXMLCookie);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_FamilyTree_EDITOR",
            iconBase = "com/asgteach/familytree/personfiletype/FamilyTreeIcon.png",
            mimeType = "text/familytree+xml",
            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
            preferredID = "FamilyTree",
            position = 1000
    )
    @Messages("LBL_FamilyTree_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.create(new FamilyTreeChildFactory(this),
                true), getLookup());
    }

    private static class FamilyTreeChildFactory extends ChildFactory<Person> {

        private final FileObject fileObject;
        private final FamilyTreeDataObject dataObject;

        public FamilyTreeChildFactory(FamilyTreeDataObject dataObject) {
            this.dataObject = dataObject;
            fileObject = dataObject.getPrimaryFile();
            fileObject.addFileChangeListener(new FileChangeAdapter() {
                @Override
                public void fileChanged(FileEvent fe) {
                    refresh(true);
                }
            });
        }

        @Override
        protected boolean createKeys(List<Person> list) {
            try {
                EditorCookie editorCookie = dataObject.getLookup().lookup(EditorCookie.class);
                //Get the InputStream from the EditorCookie:
                try (InputStream is = ((org.openide.text.CloneableEditorSupport) editorCookie).getInputStream()) {
                    //Use the NetBeans org.openide.xml.XMLUtil class to create a org.w3c.dom.Document:
                    Document doc = XMLUtil.parse(new InputSource(is), true, true, null, null);
                    NodeList nodeList = doc.getElementsByTagName("person");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        //For each node in the list, create a org.w3c.dom.Node:
                        org.w3c.dom.Node personNode = nodeList.item(i);
                        NamedNodeMap map = personNode.getAttributes();
                        Person person;
                        if (map != null) {
                            person = new Person();
                            person.setLastname("Unknown");                      
                            for (int j = 0; j < map.getLength(); j++) {
                                org.w3c.dom.Node attrNode = map.item(j);
                                if (attrNode.getNodeName().equals("firstname")) {
                                    person.setFirstname(attrNode.getNodeValue());
                                }
                                if (attrNode.getNodeName().equals("lastname")) {
                                    person.setLastname(attrNode.getNodeValue());
                                }                                
                            }
                            list.add(person);
                        }
                    }
                }
            } catch (SAXException | IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(Person key) {
            AbstractNode childNode = new AbstractNode(Children.LEAF);
            childNode.setDisplayName(key.toString());
            childNode.setIconBaseWithExtension("com/asgteach/familytree/personfiletype/personIcon.png");
            return childNode;
        }
    }

}
