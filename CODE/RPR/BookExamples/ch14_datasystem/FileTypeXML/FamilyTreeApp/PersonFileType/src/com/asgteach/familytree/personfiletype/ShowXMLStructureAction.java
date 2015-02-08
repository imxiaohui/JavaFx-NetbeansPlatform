/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfiletype;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ActionID(
        category = "File",
        id = "com.asgteach.familytree.personfiletype.ShowXMLStructureAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytree/personfiletype/info.png",
        displayName = "#CTL_ShowXMLAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1465, separatorAfter = 1482),
    @ActionReference(path = "Loaders/text/familytree+xml/Actions", position = 150, separatorBefore = 125)
})
@Messages({
    "CTL_ShowXMLAction=Show XML Structure",
    "CTL_SHOWXMLStructureActionListener=Show XML Structure"})
public final class ShowXMLStructureAction implements ActionListener {

    private final FamilyTreeDataObject context;

    public ShowXMLStructureAction(FamilyTreeDataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // "XML Structure" tab is created in Output Window for writing the list of tags:
        InputOutput io = IOProvider.getDefault().getIO(Bundle.CTL_SHOWXMLStructureActionListener(), false);
        
        io.select(); //"XML Structure" tab is selected
        try {
            EditorCookie editorCookie = context.getLookup().lookup(EditorCookie.class);
            io.getOut().reset();           
            //Use the NetBeans org.openide.xml.XMLUtil class to create a org.w3c.dom.Document:
            //Get the InputStream from the EditorCookie:
            try (InputStream is = ((org.openide.text.CloneableEditorSupport) editorCookie).getInputStream()) {
                //Use the NetBeans org.openide.xml.XMLUtil class to create a org.w3c.dom.Document:
                Document doc = XMLUtil.parse(new InputSource(is), true, true, null, null);
                parseDoc(doc, "root", io);
                parseDoc(doc, "person", io);
            }
        } catch (SAXException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    private void parseDoc(Document doc, String element, InputOutput io) {
        NodeList nodeList = doc.getElementsByTagName(element);
        io.getOut().println("Child Nodes, Size = " + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            //For each node in the list, create a org.w3c.dom.Node:
            org.w3c.dom.Node personNode = nodeList.item(i);
            //Get the name of the node:
            String nodeName = personNode.getNodeName();
            //Print the element and its attributes to the Output window:
            io.getOut().println("Element: " + nodeName);
            //Create a map for Element's attributes 
            NamedNodeMap map = personNode.getAttributes();
            if (map != null) {
                StringBuilder attrBuilder = new StringBuilder();
                //Iterate through the map of attributes
                // and display the attribute name and its value
                for (int j = 0; j < map.getLength(); j++) {
                    org.w3c.dom.Node attrNode = map.item(j);
                    String attrName = attrNode.getNodeName();
                    attrBuilder.append("\n\t\t").append(attrName);
                    attrBuilder.append("\t\t").append(attrNode.getNodeValue());
                }
                if (!attrBuilder.toString().isEmpty()) {
                    io.getOut().println(" \tAttributes:" + attrBuilder.toString());
                }
            }
        }
    }
}
