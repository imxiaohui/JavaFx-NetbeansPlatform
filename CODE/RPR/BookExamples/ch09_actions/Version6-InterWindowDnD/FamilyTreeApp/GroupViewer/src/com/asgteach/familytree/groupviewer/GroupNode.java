/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.groupviewer;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.PersonFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.actions.PasteAction;
import org.openide.actions.ReorderAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
@NbBundle.Messages({
    "HINT_GroupNode=Group Node"
})
public class GroupNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(GroupNode.class.getName());
    private final PersonModel personModel;
    private final InstanceContent instanceContent;

    public enum PersonGroup {
        HAPPY, SAD, UNDECIDED
    }

    public GroupNode(PersonGroup group, final PersonModel personModel) {
        this(group, personModel, new InstanceContent());
    }

    private GroupNode(PersonGroup group, final PersonModel personModel, InstanceContent ic) {
        super(Children.create(new PersonChildFactory(personModel), false),
                new AbstractLookup(ic));
        this.instanceContent = ic;
        this.personModel = personModel;
        this.instanceContent.add(group);
        setGroupStuff(group);
        setShortDescription(Bundle.HINT_GroupNode());
        logger.log(Level.INFO, "list for {0} is {1}", new Object[]{group, personModel.getPersons()});
        // add Index Support 
        // A support class implementing some methods of the Index.Support
        // This class must be in the Node's Lookup to support the Reorder Action
        instanceContent.add(new Index.Support() {
            @Override
            public Node[] getNodes() {
                return getChildren().getNodes(true);
            }

            @Override
            public int getNodesCount() {
                return getNodes().length;
            }

            // Reorder by permutation.
            @Override
            public void reorder(int[] orderIndex) {
                personModel.reorder(orderIndex);
            }
        });
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            ReorderAction.get(ReorderAction.class),
            PasteAction.get(PasteAction.class),};
    }

    // This is required to support drop targets for drag and drop
    // as well as paste for cut/copy actions
    @Override
    public PasteType getDropType(Transferable t, int action, int index) {
        if (t.isDataFlavorSupported(PersonFlavor.PERSON_FLAVOR)) {
            return new PasteType() {
                @Override
                public Transferable paste() throws IOException {
                    try {
                        personModel.add((Person) t.getTransferData(PersonFlavor.PERSON_FLAVOR));
                        final Node node = NodeTransfer.node(t, NodeTransfer.DND_MOVE + NodeTransfer.CLIPBOARD_CUT);
                        // only destroy the original node if it's a DND move or a cut
                        // if it's a copy, leave it alone
                        if (node != null) {
                            node.destroy();
                        }
                    } catch (UnsupportedFlavorException ex) {
                        logger.log(Level.WARNING, null, ex);
                    }
                    return null;
                }
            };
        }
        return null;
    }

    // This is required for cut and copy actions
    // It is not required for Drag and Drop
    @Override
    protected void createPasteTypes(Transferable t, List<PasteType> s) {
        logger.log(Level.INFO, "createPasteType");
        super.createPasteTypes(t, s);
        PasteType p = getDropType(t, 0, 0);
        if (p != null) {
            s.add(p);
        }
    }

    private void setGroupStuff(PersonGroup group) {
        StringBuilder sb = new StringBuilder();
        StringBuilder iconString = new StringBuilder("com/asgteach/familytree/groupviewer/resources/");
        switch (group) {
            case HAPPY:
                sb.append("Happy");
                iconString.append("happy.png");
                break;
            case SAD:
                sb.append("Sad");
                iconString.append("sad.png");
                break;
            case UNDECIDED:
                sb.append("Undecided");
                iconString.append("undecided.png");
                break;
        }
        setName(sb.toString());
        setDisplayName(sb.toString());
        setIconBaseWithExtension(iconString.toString());
    }

}
