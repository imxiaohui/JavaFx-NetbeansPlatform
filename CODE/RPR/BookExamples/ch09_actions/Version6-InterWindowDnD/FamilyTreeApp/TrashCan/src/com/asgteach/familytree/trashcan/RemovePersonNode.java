/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.trashcan;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.PersonFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gail
 */
@NbBundle.Messages({
    "HINT_RemoveNode=Person"
})
public class RemovePersonNode extends AbstractNode  {
    private static final Logger logger = Logger.getLogger(RemovePersonNode.class.getName());

    public RemovePersonNode(Person person) {
        super(Children.LEAF, Lookups.singleton(person));
        setIconBaseWithExtension("com/asgteach/familytree/trashcan/resources/delete.png");
        setName(String.valueOf(person.getId()));
        setDisplayName(person.toString());
        setShortDescription(Bundle.HINT_RemoveNode());
    }
    
    @Override
    public String getHtmlDisplayName() {
        Person person = getLookup().lookup(Person.class);
        StringBuilder sb = new StringBuilder();
        if (person == null) {
            return null;
        } 
        sb.append("<b>");
        switch (person.getGender()) {
            case MALE:
                sb.append("| ");
                break;
            case FEMALE:
                sb.append("* ");
                break;
            case UNKNOWN:
                sb.append("? ");
                break;
        }
        sb.append(person.toString()).append("</b>");
        return sb.toString();
    }
    
    @Override
    public boolean canCut() {
        return true;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }
    
    @Override
    public void destroy() throws IOException {
        logger.log(Level.INFO, "destroy for {0}", getLookup().lookup(Person.class));
        getParentNode().getChildren().remove(new Node[] { this } );
        fireNodeDestroyed();
    }
    
    @Override
    public Transferable clipboardCut() throws IOException {
        logger.log(Level.INFO, "clipboard Cut for {0}", getLookup().lookup(Person.class));
        ExTransferable extrans = ExTransferable.create(super.clipboardCut());
        extrans.put(new ExTransferable.Single(PersonFlavor.PERSON_FLAVOR) {
            @Override
            protected Person getData() {
                return getLookup().lookup(Person.class);
            }
        });
        return extrans;
    }
}
