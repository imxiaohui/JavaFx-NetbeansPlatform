/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadableViewCapability;
import com.asgteach.familytree.utilities.PersonCapability;
import com.asgteach.familytree.utilities.PersonType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.datatransfer.NewType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
@NbBundle.Messages({
        "HINT_PersonRootNode=Shows all people",
        "LBL_PersonRootNode=Collection: All People"
    })
public class PersonRootNode extends AbstractNode {
    
    private final PersonCapability capabilities;
    private final InstanceContent instanceContent;
    private final PersonType personType;
    private static final Logger logger = Logger.getLogger(PersonRootNode.class.getName());
    
    public PersonRootNode() {
        this(new InstanceContent());
    }
        
    private PersonRootNode(InstanceContent ic) {
        super(Children.create(new RootNodeChildFactory(), true),
                new AbstractLookup(ic));
        this.setIconBaseWithExtension("com/asgteach/familytree/utilities/personIcon.png");
        this.setDisplayName(Bundle.LBL_PersonRootNode());
        this.setShortDescription(Bundle.HINT_PersonRootNode());
        this.instanceContent = ic;
        this.capabilities = new PersonCapability();
        this.personType = new PersonType(capabilities, this, true);
        this.instanceContent.add(personType);
        // Add a new ability for this node to be reloaded
        this.instanceContent.add((ReloadableViewCapability) () -> {
            logger.log(Level.FINER, "ReloadableViewCapability: reloadChildren");
            // To reload this node, just set a new set of children
            // using RootNodeChildFactory object, that retrieves
            // children asynchronously
            setChildren(Children.create(new RootNodeChildFactory(),
                    true));
        });
        
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> nodeActions = (List<Action>)Utilities.actionsForPath("Actions/RootNode");
        nodeActions.addAll((List<Action>)Utilities.actionsForPath("Actions/ReloadNode"));
        return nodeActions.toArray(new Action[nodeActions.size()]);        
    }

    @Override
    public NewType[] getNewTypes() {
        return new NewType[] { personType };
    } 
}
