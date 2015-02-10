/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.utilities;

import com.asgteach.familytree.capabilityinterfaces.AddPictureCapability;
import com.asgteach.familytree.capabilityinterfaces.HappyMammothCapability;
import com.asgteach.familytree.editorinterfaces.PersonEditor;
import com.asgteach.familytree.editorinterfaces.PersonEditorManager;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.SwingWorker;
import org.netbeans.api.actions.Openable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.actions.DeleteAction;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author gail
 */

/*
 *  PersonCapabilityNode implements any common 'Person' node capabilities
 *  This class is the superclass for PersonNode from module PersonViewer
 *  and PersonEventsViewerNode from module EventsViewer
 */
@Messages({
    "# {0} - person",
    "CTL_OpenImageFile=Select Image for {0}",
    "# {0} - person",
    "CTL_SaveImageMsg=Image stored for {0}",
    "# {0} - file size",
    "ERRMSG_FileTooBig=Please select a file that is less than {0} bytes"
})
public class PersonCapabilityNode extends AbstractNode {

    private final PersonType personType;
    protected final InstanceContent instanceContent;
    protected final FamilyTreeManager ftm;
    private static final long MAX_PIX_SIZE = 512000;
    private static final Logger logger = Logger.getLogger(PersonCapabilityNode.class.getName());
    
    public PersonCapabilityNode(Person person, Children children) {
        this(person, new InstanceContent(), children);
    }

    private PersonCapabilityNode(final Person person,
            InstanceContent ic, final Children children) {
        super(children, new AbstractLookup(ic));
        final PersonCapability personCapability = new PersonCapability();

        this.instanceContent = ic;
        this.setIconBaseWithExtension("com/asgteach/familytree/utilities/personIcon.png");
        this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                switch (pce.getPropertyName()) {
                    case FamilyTreeManager.PROP_PERSON_UPDATED:
                        {
                            final Person person = getLookup().lookup(Person.class);
                            if (pce.getOldValue() != null
                                    && Objects.equals(((Person) pce.getOldValue()).getId(), person.getId())) {
                                // update from OldValue
                                Person personChanged = ((Person) pce.getOldValue());
                                person.setFirstname(personChanged.getFirstname());
                                person.setGender(personChanged.getGender());
                                person.setLastname(personChanged.getLastname());
                                person.setMiddlename(personChanged.getMiddlename());
                                person.setSuffix(personChanged.getSuffix());
                                person.setNotes(personChanged.getNotes());
                                WindowManager.getDefault().invokeWhenUIReady(() -> {
                                    // update the node display name
                                    setDisplayName(person.toString());
                                });
                                
                            }       break;
                        }
                    case FamilyTreeManager.PROP_PERSON_DESTROYED:
                    {
                        Person person = getLookup().lookup(Person.class);
                        if (pce.getOldValue() != null
                                && Objects.equals(((Person) pce.getOldValue()).getId(), person.getId())) {
                            logger.log(Level.FINER, "Person destroyed from ftm for {0}", person);
                        ftm.removePropertyChangeListener(this);
                    }       break;
                        }
                }
            }
        });
        personType = new PersonType(personCapability, this, false);
        ic.add(personType);
        ic.add(person);
        ic.add(personCapability);
        // add an "Openable" capability
        ic.add((Openable) () -> {
            logger.log(Level.FINER, "Openable: Open person {0}", person);
            PersonEditorManager pem = Lookup.getDefault().lookup(PersonEditorManager.class);
            if (pem == null) {
                logger.log(Level.WARNING, "Cannot get PersonEditorManager object");
                return;
            }
            PersonEditor personEditor = pem.getEditorForPerson(person);
            if (personEditor == null) {
                logger.log(Level.WARNING, "Cannot get PersonEditor");
                return;
            }
            personEditor.setNode(PersonCapabilityNode.this);
            TopComponent tc = personEditor.getTopComponent();
            if (tc != null) {
                if (!tc.isOpened()) {
                    tc.open();
                }
                tc.requestActive();
            }
        });
        ic.add((AddPictureCapability) () -> {
            Platform.runLater(() -> {
                final Person person1 = getLookup().lookup(Person.class);
                // JavaFX control
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(Bundle.CTL_OpenImageFile(person1));
                //Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("image file", "*.gif", "*.png", "*.jpg");
                fileChooser.getExtensionFilters().add(extFilter);
                //Show open file dialog (no parent Window)
                final File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    logger.log(Level.FINER, "AddPictureCapability: file path {0}", file.getPath());
                    WindowManager.getDefault().invokeWhenUIReady(() -> {
                        if (file.length() > MAX_PIX_SIZE) {
                            NotifyDescriptor nd =
                                    new NotifyDescriptor.Message(
                                            Bundle.ERRMSG_FileTooBig(MAX_PIX_SIZE),
                                            NotifyDescriptor.ERROR_MESSAGE);
                            DialogDisplayer.getDefault().notify(nd);
                        } else {
                            // do this in the background
                            SwingWorker<Picture, Void> worker = new SwingWorker<Picture, Void>() {
                                @Override
                                public Picture doInBackground() {
                                    try {
                                        storePicFromFile(file);
                                    } catch (Exception e) {
                                        // empty
                                    }
                                    return null;
                                }
                            };
                            worker.execute();
                        }
                    }); // end invokeWhenUIReady Runnable
                }
            }); // end runLater Runnable
        }); // end add AddPictureCapability

        ic.add((HappyMammothCapability) () -> {
            HappyMammothPanel hmp = new HappyMammothPanel();
            hmp.setText("<html>Happy Mammoth applied to<p/>"
                    + person + "</html>");
            DialogDescriptor dd = new DialogDescriptor(hmp,
                    Bundle.CTL_HappyMammothTitle());
            DialogDisplayer.getDefault().notify(dd);  // display & block
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action getPreferredAction() {
        List<Action> personActions = (List<Action>) Utilities.actionsForPath("Actions/OpenNode");
        if (!personActions.isEmpty()) {
            // Make 'Open' the default action
            return personActions.get(0);
        } else {
            return super.getPreferredAction();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Action[] getActions(boolean context) {
        List<Action> personActions = (List<Action>) Utilities.actionsForPath("Actions/OpenNode");
        personActions.add(null);    // separator
        personActions.addAll((List<Action>) Utilities.actionsForPath("Actions/PersonNode"));
        personActions.add(null);    // separator
        personActions.addAll((List<Action>) Utilities.actionsForPath("Actions/ReloadNode"));
        personActions.add(null);    // separator
        personActions.addAll((List<Action>) Utilities.actionsForPath("Actions/Extra"));
        personActions.add(null);    // separator
        personActions
                .add(SystemAction.get(DeleteAction.class));
        return personActions.toArray(
                new Action[personActions.size()]);
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof PersonCapabilityNode) {
            PersonCapabilityNode personNode = (PersonCapabilityNode) obj;
            Person p1 = getLookup().lookup(Person.class);
            Person p2 = personNode.getLookup().lookup(Person.class);
            if (p1.equals(p2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        Person p = getLookup().lookup(Person.class);
        int hash = 7;
        hash = 97 * hash + (p != null ? p.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String getDisplayName() {
        Person p = getLookup().lookup(Person.class);
        return p.toString();
    }

    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{personType};
    }

    private void storePicFromFile(File file) {
        // Store the pic
        final Person person = getLookup().lookup(Person.class);
        try {
            BufferedImage image = ImageIO.read(file);
            Picture pic = new Picture();
            pic.setFilename(file.getPath());
            logger.log(Level.FINER, "storePicFromFile: File {0}", pic.getFilename());
            pic.setImage(image);
            pic.setPerson(person);
            ftm.storePicture(pic);
            StatusDisplayer.getDefault().setStatusText(Bundle.CTL_SaveImageMsg(person));
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);            
        }

    }
}
