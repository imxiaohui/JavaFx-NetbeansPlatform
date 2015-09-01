package com.asgteach.familytree.genderviewer;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;

/**
 * Root node. Sus hijos usan PersonChildFactory para su construccion.
 * @author ernesto
 */
@NbBundle.Messages({
    "HINT_RootNode=Show All People",
    "LBL_RootNode=People"
})
public class RootNode extends AbstractNode {

    public RootNode() {
        super(Children.create(new PersonChildFactory(), false));
        setIconBaseWithExtension("/com/asgteach/familytree/genderviewer/resources/Icons/personIcon.png");
        setDisplayName(Bundle.LBL_RootNode());
        setShortDescription(Bundle.HINT_RootNode());
    }
    
}
