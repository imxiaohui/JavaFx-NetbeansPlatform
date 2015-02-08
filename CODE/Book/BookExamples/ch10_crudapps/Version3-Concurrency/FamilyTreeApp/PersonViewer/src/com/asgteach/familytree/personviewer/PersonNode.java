/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

import com.asgteach.familytree.capabilities.RemovablePersonCapability;
import com.asgteach.familytree.capabilities.SavablePersonCapability;
import com.asgteach.familytree.editor.manager.EditorManager;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.SwingWorker;
import org.netbeans.api.actions.Openable;
import org.openide.LifecycleManager;
import org.openide.actions.DeleteAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gail
 */
@NbBundle.Messages({
    "HINT_PersonNode=Person"
})
public class PersonNode extends AbstractNode {

    private final InstanceContent instanceContent;
    private static final Logger logger = Logger.getLogger(PersonNode.class.getName());

    public PersonNode(Person person) {
        this(person, new InstanceContent());
    }

    private PersonNode(Person person, InstanceContent ic) {
        super(Children.LEAF, new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(person);
        setIconBaseWithExtension("com/asgteach/familytree/personviewer/resources/personIcon.png");
        setName(String.valueOf(person.getId()));
        setDisplayName(person.toString());
        setShortDescription(Bundle.HINT_PersonNode());

        final FamilyTreeManager ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        } else {
            ftm.addPropertyChangeListener(WeakListeners.propertyChange(familyListener, ftm));
        }

        // Add an Openable object to this Node
        instanceContent.add(new Openable() {

            @Override
            public void open() {
                EditorManager edmanager = Lookup.getDefault().lookup(EditorManager.class);
                if (edmanager != null) {
                    edmanager.openEditor(PersonNode.this);
                }
            }

        });
        // Add a SavablePersonCapability to this Node
        instanceContent.add(new SavablePersonCapability() {

            @Override
            public void save(final Person p) throws IOException {
                if (ftm != null) {
                    ftm.updatePerson(p);
                }
            }
        });
        // Add a RemovablePersonCapability to this Node

        instanceContent.add(new RemovablePersonCapability() {

            @Override
            public void remove(final Person p) throws IOException {
                if (ftm != null) {
                    ftm.deletePerson(p);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    @Override
    public Action getPreferredAction() {
        // We want the OpenAction to be the preferred action
        List<Action> actions = new ArrayList<>(Utilities.actionsForPath("Actions/OpenNodes"));
        if (!actions.isEmpty()) {
            return actions.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> personActions = new ArrayList<>(Arrays.asList(super.getActions(context)));
        personActions.addAll(Utilities.actionsForPath("Actions/OpenNodes"));
        personActions.add(DeleteAction.get(DeleteAction.class));
        return personActions.toArray(
                new Action[personActions.size()]);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        final RemovablePersonCapability doRemove = getLookup().lookup(RemovablePersonCapability.class);
        final Person person = getLookup().lookup(Person.class);
        if (doRemove != null && person != null) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() {
                    try {
                        doRemove.remove(person);
                    } catch (IOException e) {
                        logger.log(Level.WARNING, null, e);
                    }
                    return null;
                }    
            };
            worker.execute();
            // we don't need to fireNodeDestroyed because we have
            // propertychangelisteners responding to FamilyTreeManager
            // property change events.
        }
    }
    
    @Override
    public String getHtmlDisplayName() {
        Person person = getLookup().lookup(Person.class);
        StringBuilder sb = new StringBuilder();
        if (person == null) {
            return null;
        }
        sb.append("<font color='#5588FF'><b>");
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
        sb.append(person.toString()).append("</b></font>");
        return sb.toString();
    }

    // PropertyChangeListener for FamilyTreeManager
    private final PropertyChangeListener familyListener = (PropertyChangeEvent pce) -> {
        if (pce.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_UPDATED)
                && pce.getNewValue() != null) {
            final Person personChanged = (Person) pce.getNewValue();
            final Person person = getLookup().lookup(Person.class);
            if (person.equals(personChanged)) {
                logger.log(Level.INFO, "Updating person {0}", person);
                // update from personChange
                person.setFirstname(personChanged.getFirstname());
                person.setGender(personChanged.getGender());
                person.setLastname(personChanged.getLastname());
                person.setMiddlename(personChanged.getMiddlename());
                person.setSuffix(personChanged.getSuffix());
                person.setNotes(personChanged.getNotes());
                fireDisplayNameChange(null, getDisplayName());
            }
        }
    };

}
