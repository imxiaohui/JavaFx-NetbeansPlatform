/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.utilities;

import com.asgteach.familytree.capabilityinterfaces.CreatablePersonCapability;
import com.asgteach.familytree.model.Person;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;
import org.openide.util.datatransfer.NewType;

/**
 *
 * @author gail
 */
@Messages({
    "LBL_NewFirst_dialog=First name:",
    "LBL_NewLast_dialog=Last name:",
    "TITLE_NewPerson_dialog=New Person"
})
public class PersonType extends NewType {

    private final PersonCapability capability;
    private final Node node;
    private final boolean root;
    private static final Logger logger = Logger.getLogger(PersonType.class.getName());

    public PersonType(PersonCapability capability, Node node,
            boolean root) {
        this.capability = capability;
        this.node = node;
        this.root = root;
    }

    @Override
    public String getName() {
        return Bundle.TITLE_NewPerson_dialog();
    }

    @Override
    public void create() throws IOException {
        NotifyDescriptor.InputLine msg = new NotifyDescriptor.InputLine(Bundle.LBL_NewFirst_dialog(),
                Bundle.TITLE_NewPerson_dialog());
        Object result = DialogDisplayer.getDefault().notify(msg);
        if (NotifyDescriptor.CANCEL_OPTION.equals(result)) {
            return;
        }
        String firstname = msg.getInputText();
        // check for a zero-length firstname
        if (firstname.equals("")) {
            return;
        }
        msg = new NotifyDescriptor.InputLine(Bundle.LBL_NewLast_dialog(),
                Bundle.TITLE_NewPerson_dialog());
        result = DialogDisplayer.getDefault().notify(msg);
        String lastname = msg.getInputText();
        if (NotifyDescriptor.YES_OPTION.equals(result)) {
            // Create a new Person object
            final Person person = new Person();
            person.setFirstname(firstname);
            person.setLastname(lastname);
            // Pass the person to the capability's implementation 
            // of the create capability 
            final CreatablePersonCapability cec = capability.getLookup().lookup(
                    CreatablePersonCapability.class);
            SwingWorker<Person, Void> worker = new SwingWorker<Person, Void>() {
                @Override
                public Person doInBackground() {
                    try {
                        cec.create(person);
                        logger.log(Level.FINER, "Person {0} created.", person);
                    } catch (Exception e) {
                        // empty
                    }
                    return null;
                } 
            };
            worker.execute();
        }
    }
}
