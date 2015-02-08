/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personviewer;

import com.asgteach.familytree.capabilities.CreatablePersonCapability;
import com.asgteach.familytree.model.Person;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
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

    private static final Logger logger = Logger.getLogger(PersonType.class.getName());
    private final PersonCapability personCapability = new PersonCapability();

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
            final CreatablePersonCapability cpc
                    = personCapability.getLookup().lookup(CreatablePersonCapability.class);
            if (cpc != null) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        ProgressHandle handle = ProgressHandleFactory.createHandle(Bundle.TITLE_NewPerson_dialog());
                        try {
                            handle.start();
//                            Thread.sleep(1000);
                            cpc.create(person);
                            logger.log(Level.INFO, "Creating person {0}", person);                           
                        } catch (IOException e) {
                            logger.log(Level.WARNING, e.getLocalizedMessage(), e);
                        } finally {
                            handle.finish();
                        }
                        return null;
                    }
                };
                worker.execute();
            }
        }
    }
}
