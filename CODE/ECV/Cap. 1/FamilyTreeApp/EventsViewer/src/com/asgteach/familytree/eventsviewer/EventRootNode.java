/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.eventsviewer;

import com.asgteach.familytree.capabilityinterfaces.ReloadableViewCapability;
import com.asgteach.familytree.utilities.PersonCapability;
import com.asgteach.familytree.utilities.PersonType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.datatransfer.NewType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
public class EventRootNode extends AbstractNode {

    private InstanceContent instanceContent;
    private PersonType personType = null;
    private static final Logger logger = Logger.getLogger(EventRootNode.class.getName());

    public EventRootNode() {
        this(new InstanceContent());
    }

    @Messages({
        "HINT_RootNode=Shows all people and events",
        "LBL_RootNode=Events: All People"
    })
    private EventRootNode(InstanceContent ic) {
        super(Children.create(new AllPeopleFactory(), true),
                new AbstractLookup(ic));
        this.instanceContent = ic;
        this.personType = new PersonType(new PersonCapability(), this, true);
        this.instanceContent.add(personType);
        this.setIconBaseWithExtension("com/asgteach/familytree/utilities/eventIcon.png");
        this.setShortDescription(Bundle.HINT_RootNode());
        this.setDisplayName(Bundle.LBL_RootNode());
        // Add a new ability for this node to be reloaded
        this.instanceContent.add((ReloadableViewCapability) () -> {
            logger.log(Level.FINER, "ReloadableViewCapability: reloadChildren");
            // To reload this node, just set a new set of children
            // using AllPeopleFactory object, that retrieves
            // children asynchronously
            setChildren(Children.create(new AllPeopleFactory(),
                    true));
        });


    }

    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> nodeActions = (List<Action>) Utilities.actionsForPath("Actions/RootNode");
        nodeActions.addAll((List<Action>) Utilities.actionsForPath("Actions/ReloadNode"));
        // put in separators
        List<Action> actionsWithSeparators = new ArrayList<>();
        for (Action action : nodeActions) {
            actionsWithSeparators.add(action);
            if (!(nodeActions.indexOf(action) == nodeActions.size()-1)) {
                actionsWithSeparators.add(null);    // creates a separator                                                
            }
            
        }
        return actionsWithSeparators.toArray(new Action[actionsWithSeparators.size()]);
    }

    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{personType};
    }
}
