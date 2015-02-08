/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

import com.asgteach.familytree.capabilities.RefreshCapability;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
@Messages({
    "HINT_RootNode=Show all people",
    "LBL_RootNode=People"
})
public class RootNode extends AbstractNode {
    
    private final InstanceContent instanceContent;
    private final PersonType personType = new PersonType();
    public RootNode() {
        this(new InstanceContent());
    }

    private RootNode(InstanceContent ic) {
        super(Children.create(new PersonChildFactory(), false), new AbstractLookup(ic));
        instanceContent = ic;
        setIconBaseWithExtension("com/asgteach/familytree/personviewer/resources/personIcon.png");
        setDisplayName(Bundle.LBL_RootNode());
        setShortDescription(Bundle.HINT_RootNode());
        // Required to enable New Person in context menu and Toolbar and Menus
        instanceContent.add(personType);
        instanceContent.add(new RefreshCapability(){

            @Override
            public void refresh() throws IOException {
                System.out.println("refresh");
                setChildren(Children.create(new PersonChildFactory(), false));
            }
        });
    }

    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<>(Arrays.asList(super.getActions(context)));
        actions.addAll(Utilities.actionsForPath("Actions/RootNode"));
        actions.addAll(Utilities.actionsForPath("Actions/NewNode"));
        // This puts the NewAction in the context menu
//        actions.add(SystemAction.get(NewAction.class));
        return actions.toArray(new Action[actions.size()]);
    }
    
    // This enables only SystemAction.get(NewAction.class) in the context menu
    // But required so that NewAction can find personType
    // This is not necessary if we only use "Actions/NewNode" in the context menu
//    @Override
//    public NewType[] getNewTypes() {
//        return new NewType[] { personType };
//    } 

}
