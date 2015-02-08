/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.trashcan;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.PersonFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Utilities;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
public class TrashNode extends AbstractNode implements NodeListener {

    private final InstanceContent instanceContent;
    private static final Logger logger = Logger.getLogger(TrashNode.class.getName());

    public TrashNode(String title) {
        this(title, new InstanceContent());
    }

    private TrashNode(String title, InstanceContent ic) {
        super(new PersonNodeContainer(), new AbstractLookup(ic));
        instanceContent = ic;
        setDisplayName(title);
        setIconBaseWithExtension("com/asgteach/familytree/trashcan/resources/EmptyTrashicon.png");
    }

    @Override
    public PasteType getDropType(Transferable t, int action, int index) {

        if (t.isDataFlavorSupported(PersonFlavor.PERSON_FLAVOR)) {
            return new PasteType() {

                @Override
                public Transferable paste() throws IOException {
                    final Node node = NodeTransfer.node(t, NodeTransfer.DND_MOVE + NodeTransfer.CLIPBOARD_CUT);
                    if (node != null && !TrashNode.this.equals(node.getParentNode())) {
                        try {
                            final Person person = ((Person) t.getTransferData(PersonFlavor.PERSON_FLAVOR));
                            Node newNode = new RemovePersonNode(person);
                            newNode.addNodeListener(TrashNode.this);
                            getChildren().add(new Node[]{newNode});
                            // we have at least one RemovePersonNode
                            enableEmptyAction();
                            if ((action & DnDConstants.ACTION_MOVE) != 0) {
                                logger.log(Level.INFO, "Get rid of old node for : {0}", person);
                                logger.log(Level.INFO, "parent node : {0}", node.getParentNode().getDisplayName());
                                node.destroy();
                            }
                        } catch (UnsupportedFlavorException ex) {
                            logger.log(Level.WARNING, null, ex);
                        }
                    }
                    return null;
                }
            };
        }
        return null;
    }

    // invoked from the NodeListener nodeDestroyed() method
    private void clearEmptyAction() {
        logger.log(Level.INFO, "clearEmptyAction: nodes={0} ", getChildren().getNodesCount());
        EmptyCapability ec = getLookup().lookup(EmptyCapability.class);
        if (ec != null) {
            instanceContent.remove(ec);
            setIconBaseWithExtension("com/asgteach/familytree/trashcan/resources/EmptyTrashicon.png");
        }
    }

    private void enableEmptyAction() {
        EmptyCapability ec = getLookup().lookup(EmptyCapability.class);
        if (ec == null) {
            setIconBaseWithExtension("com/asgteach/familytree/trashcan/resources/FullTrashicon.png");
            instanceContent.add(new EmptyCapability() {

                @Override
                public void emptyTrash() {               
                    for (Node node : getChildren().getNodes()) {
                        try {
                            node.destroy();
                        } catch (IOException ex) {
                            logger.log(Level.WARNING, null, ex);
                        }                        
                    }
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<>();
        actions.addAll(Utilities.actionsForPath("Actions/TrashNode"));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public void childrenAdded(NodeMemberEvent nme) {
    }

    @Override
    public void childrenRemoved(NodeMemberEvent nme) {
    }

    @Override
    public void childrenReordered(NodeReorderEvent nre) {
    }

    @Override
    public void nodeDestroyed(NodeEvent ne) {
        logger.log(Level.INFO, "node destroyed for {0}", ne.getNode().getDisplayName());
        ne.getNode().removeNodeListener(this);
        if (getChildren().getNodesCount() == 0) {
            clearEmptyAction();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

}
