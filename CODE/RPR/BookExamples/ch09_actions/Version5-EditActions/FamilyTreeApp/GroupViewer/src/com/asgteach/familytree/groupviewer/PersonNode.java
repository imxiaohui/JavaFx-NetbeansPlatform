/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.groupviewer;

import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.PersonFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.MoveDownAction;
import org.openide.actions.MoveUpAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gail
 */
@NbBundle.Messages({
    "HINT_PersonNode=Person"
})
public class PersonNode extends AbstractNode  {
    private static final Logger logger = Logger.getLogger(PersonNode.class.getName());
    // This is the list that this PersonNode is part of!
    private final PersonModel personModel;

    public PersonNode(Person person, PersonModel personModel) {
        super(Children.LEAF, Lookups.singleton(person));
        this.personModel = personModel;
        setIconBaseWithExtension("com/asgteach/familytree/groupviewer/resources/personIcon.png");
        setName(String.valueOf(person.getId()));
        setDisplayName(person.toString());
        setShortDescription(Bundle.HINT_PersonNode());
    }
    
    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
                    CutAction.get(CutAction.class),
                    CopyAction.get(CopyAction.class),
                    DeleteAction.get(DeleteAction.class),
                    MoveUpAction.get(MoveUpAction.class),
                    MoveDownAction.get(MoveDownAction.class)
                };
    }

    @Override
    public boolean canCut() {
        return true;
    }

    @Override
    public boolean canCopy() {
        return true;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    
    @Override
    public void destroy() throws IOException {
        personModel.remove(getLookup().lookup(Person.class));
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

    @Override
    public Transferable clipboardCopy() throws IOException {
        logger.log(Level.INFO, "clipboard Copy for {0}", getLookup().lookup(Person.class));
        ExTransferable extrans = ExTransferable.create(super.clipboardCopy());
        extrans.put(new ExTransferable.Single(PersonFlavor.PERSON_FLAVOR) {
            @Override
            protected Person getData() {
                return getLookup().lookup(Person.class);
            }
        });
        return extrans;
    }

    @Override
    public String getHtmlDisplayName() {
        Person person = getLookup().lookup(Person.class);
        StringBuilder sb = new StringBuilder();
        if (person == null) {
            return null;
        }       
        switch (person.getGender()) {
            case MALE:
                sb.append("<font color='#5588FF'><b>| ");
                break;
            case FEMALE:
                sb.append("<font color='#FF8855'><b>* ");
                break;
            case UNKNOWN:
                sb.append("<b>? ");
                break;
        }
        sb.append(person.toString()).append("</b></font>");
        return sb.toString();
    }
}
