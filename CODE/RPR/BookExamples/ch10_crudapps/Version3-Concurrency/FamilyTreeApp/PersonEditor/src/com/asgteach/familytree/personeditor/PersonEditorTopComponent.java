/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personeditor;

import com.asgteach.familytree.capabilities.SavablePersonCapability;
import com.asgteach.familytree.editor.manager.EditorManager;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@TopComponent.Description(
        preferredID = "PersonEditorTopComponent",
        iconBase = "com/asgteach/familytree/personeditor/personIcon.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_PersonEditorAction"
//)
@Messages({
    "CTL_PersonEditorAction=PersonEditor",
    "CTL_PersonEditorTopComponent=PersonEditor Window",
    "CTL_PersonEditorSaveDialogTitle=Unsaved Data",
    "CTL_PersonEditorSave_Option=Save",
    "CTL_PersonEditorDiscard_Option=Discard",
    "CTL_PersonEditorCANCEL_Option=Cancel",
    "# {0} - person",
    "HINT_PersonEditorTopComponent=This is an Editor for {0}",
    "# {0} - person",
    "CTL_PersonEditorSaveDialogMsg=Person {0} has Unsaved Data. \nSave?",
    "# {0} - person",
    "CTL_PersonUpdating=Updating Person {0}"
})
public final class PersonEditorTopComponent extends TopComponent {
    /*
     TopComponents implement Lookup.Provider. 
     In order to add objects to a TopComponents lookup, instantiate
     InstanceContent and associate it with its Lookup using
     the associateLookup() method and create a Lookup with
     AbstractLookup.
     */

    private Person thePerson = null;
    private static final Logger logger = Logger.getLogger(PersonEditorTopComponent.class.getName());
    private boolean changeOK = false;
    private boolean noUpdate = true;
    private final InstanceContent instanceContent = new InstanceContent();
    private FamilyTreeManager ftm;

    public PersonEditorTopComponent() {
        initComponents();
        associateLookup(new AbstractLookup(instanceContent));
    }

    // The EditorManager must invoke the setPerson method with the Node
    // that contains the wrapped Person object in its Lookup
    public void setPerson(Node node) {
        thePerson = node.getLookup().lookup(Person.class);
        Node oldNode = getLookup().lookup(Node.class);
        if (oldNode != null) {
            instanceContent.remove(oldNode);
        }
        instanceContent.add(node);
        // Note: we don't combine the Node's Lookup with the TopComponent's
        // Lookup because then "Openable" will be enabled, which doesn't
        // make sense when the Editor TopComponent has focus.
        // If you wanted to combine the lookups, however, you would use
        // ProxyLookup as shown here:
        // associateLookup(new ProxyLookup(new AbstractLookup(instanceContent), node.getLookup()));
    }

    private void configureComponentListeners() {
        // add document listeners to textfields, textarea
        firstTextField.getDocument().addDocumentListener(docListener);
        middleTextField.getDocument().addDocumentListener(docListener);
        lastTextField.getDocument().addDocumentListener(docListener);
        suffixTextField.getDocument().addDocumentListener(docListener);
        notesTextArea.getDocument().addDocumentListener(docListener);
        // add action listeners to radiobuttons
        maleButton.addActionListener(radioButtonListener);
        femaleButton.addActionListener(radioButtonListener);
        unknownButton.addActionListener(radioButtonListener);
    }

    private void removeComponentListeners() {
        // remove document listeners from textfields, textarea
        firstTextField.getDocument().removeDocumentListener(docListener);
        middleTextField.getDocument().removeDocumentListener(docListener);
        lastTextField.getDocument().removeDocumentListener(docListener);
        suffixTextField.getDocument().removeDocumentListener(docListener);
        notesTextArea.getDocument().removeDocumentListener(docListener);
        // remove action listeners from radiobuttons
        maleButton.removeActionListener(radioButtonListener);
        femaleButton.removeActionListener(radioButtonListener);
        unknownButton.removeActionListener(radioButtonListener);
    }

    private void updateForm() {
        changeOK = false;
        noUpdate = true;
        firstTextField.setText(thePerson.getFirstname());
        middleTextField.setText(thePerson.getMiddlename());
        lastTextField.setText(thePerson.getLastname());
        suffixTextField.setText(thePerson.getSuffix());
        if (thePerson.getGender().equals(Gender.MALE)) {
            maleButton.setSelected(true);
        } else if (thePerson.getGender().equals(Gender.FEMALE)) {
            femaleButton.setSelected(true);
        } else if (thePerson.getGender().equals(Gender.UNKNOWN)) {
            unknownButton.setSelected(true);
        }
        notesTextArea.setText(thePerson.getNotes());
        changeOK = true;
        noUpdate = false;
    }

    private void updateModel() {
        if (noUpdate) {
            return;
        }
        thePerson.setFirstname(firstTextField.getText());
        thePerson.setMiddlename(middleTextField.getText());
        thePerson.setLastname(lastTextField.getText());
        thePerson.setSuffix(suffixTextField.getText());
        if (maleButton.isSelected()) {
            thePerson.setGender(Gender.MALE);
        } else if (femaleButton.isSelected()) {
            thePerson.setGender(Gender.FEMALE);
        } else if (unknownButton.isSelected()) {
            thePerson.setGender(Gender.UNKNOWN);
        }
        thePerson.setNotes(notesTextArea.getText());
        // Update the TopComponent's name and tooltip
        setName(thePerson.toString());
        setToolTipText(Bundle.HINT_PersonEditorTopComponent(thePerson.toString()));
    }

    private void modify() {
        // Add AbstractSavable to Lookup
        if (getLookup().lookup(SavableViewCapability.class) == null) {
            instanceContent.add(new SavableViewCapability());
        }
    }

    private void clearSaveCapability() {
        SavableViewCapability savable = getLookup().lookup(SavableViewCapability.class);
        while (savable != null) {
            savable.removeSavable();
            this.instanceContent.remove(savable);
            savable = this.getLookup().lookup(SavableViewCapability.class);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        firstTextField = new javax.swing.JTextField();
        middleTextField = new javax.swing.JTextField();
        lastTextField = new javax.swing.JTextField();
        suffixTextField = new javax.swing.JTextField();
        maleButton = new javax.swing.JRadioButton();
        femaleButton = new javax.swing.JRadioButton();
        unknownButton = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesTextArea = new javax.swing.JTextArea();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel5.text")); // NOI18N

        genderButtonGroup.add(maleButton);
        org.openide.awt.Mnemonics.setLocalizedText(maleButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.maleButton.text")); // NOI18N

        genderButtonGroup.add(femaleButton);
        org.openide.awt.Mnemonics.setLocalizedText(femaleButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.femaleButton.text")); // NOI18N

        genderButtonGroup.add(unknownButton);
        org.openide.awt.Mnemonics.setLocalizedText(unknownButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.unknownButton.text")); // NOI18N

        notesTextArea.setColumns(20);
        notesTextArea.setRows(5);
        jScrollPane2.setViewportView(notesTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(maleButton)
                                .addGap(18, 18, 18)
                                .addComponent(femaleButton)
                                .addGap(18, 18, 18)
                                .addComponent(unknownButton)))
                        .addGap(0, 69, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstTextField)
                            .addComponent(middleTextField)
                            .addComponent(lastTextField)
                            .addComponent(suffixTextField))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(firstTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(middleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(lastTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(suffixTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maleButton)
                    .addComponent(femaleButton)
                    .addComponent(unknownButton))
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton femaleButton;
    private javax.swing.JTextField firstTextField;
    private javax.swing.ButtonGroup genderButtonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastTextField;
    private javax.swing.JRadioButton maleButton;
    private javax.swing.JTextField middleTextField;
    private javax.swing.JTextArea notesTextArea;
    private javax.swing.JTextField suffixTextField;
    private javax.swing.JRadioButton unknownButton;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        setName(thePerson.toString());
        setToolTipText(Bundle.HINT_PersonEditorTopComponent(thePerson.toString()));
        configureComponentListeners();
        updateForm();
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        } else {
            ftm.addPropertyChangeListener(familyListener);
        }
    }

    @Override
    public void componentClosed() {
        removeComponentListeners();
        ftm.removePropertyChangeListener(familyListener);
    }

//    void writeProperties(java.util.Properties p) {
//        // better to version settings since initial version as advocated at
//        // http://wiki.apidesign.org/wiki/PropertyFiles
//        p.setProperty("version", "1.0");
//        // TODO store your settings
//    }
//
//    void readProperties(java.util.Properties p) {
//        String version = p.getProperty("version");
//        // TODO read your settings according to their version
//    }
    // Define listeners
    // DocumentListener for text fields and text area
    private final DocumentListener docListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent evt) {
            if (changeOK) {
                modify();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent evt) {
            if (changeOK) {
                modify();
            }
        }

        @Override
        public void changedUpdate(DocumentEvent evt) {
            if (changeOK) {
                modify();
            }
        }

    };

    // ActionListener for Radio Buttons
    private final ActionListener radioButtonListener = (ActionEvent e) -> {
        if (changeOK) {
            modify();
        }
    };

    // PropertyChangeListener for FamilyTreeManager
    private final PropertyChangeListener familyListener = (PropertyChangeEvent pce) -> {
        if (pce.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_DESTROYED)
                && pce.getNewValue() != null) {
            if (pce.getNewValue().equals(thePerson)) {
                // Our person has been removed from the FamilyTreeManager, so we
                // need to close!
                logger.log(Level.INFO, "detected Person destroyed for {0}", thePerson);
                clearSaveCapability();
                EditorManager edManager = Lookup.getDefault().lookup(EditorManager.class);
                if (edManager != null) {
                    edManager.unRegisterEditor(thePerson);
                    PersonEditorTopComponent.shutdown(this);
                }
            }
        }
    };

    @Override
    public boolean canClose() {
        SavableViewCapability savable = getLookup().lookup(SavableViewCapability.class);
        if (savable == null) {
            // No modified data, so just close
            return true;
        }
        // Detected modified data, so ask user what to do
        String saveAnswer = Bundle.CTL_PersonEditorSave_Option();
        String discardAnswer = Bundle.CTL_PersonEditorDiscard_Option();
        String cancelAnswer = Bundle.CTL_PersonEditorCANCEL_Option();
        String[] options = {cancelAnswer, discardAnswer, saveAnswer};
        String msg = Bundle.CTL_PersonEditorSaveDialogMsg(thePerson.toString());
        NotifyDescriptor nd = new NotifyDescriptor(msg, // the question
                Bundle.CTL_PersonEditorSaveDialogTitle(), // the title
                NotifyDescriptor.YES_NO_CANCEL_OPTION, // the buttons provided
                NotifyDescriptor.QUESTION_MESSAGE, // the type of message
                options, // the button text
                saveAnswer // the default selection
        );
        Object result = DialogDisplayer.getDefault().notify(nd);
        if (result == cancelAnswer || result == NotifyDescriptor.CLOSED_OPTION) {
            // Cancel the close
            return false;
        }
        if (result == discardAnswer) {
            // Don't save, just close!
            clearSaveCapability();
            return true;
        }
        try {
            // Yes, save the data, then close
            savable.handleSave();
            StatusDisplayer.getDefault().setStatusText(thePerson + " saved.");
            return true;
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
            return false;
        }
    }

    private static void shutdown(final TopComponent tc) {
        WindowManager.getDefault().invokeWhenUIReady(() -> tc.close());
    }

    private class SavableViewCapability extends AbstractSavable {

        SavableViewCapability() {
            register();
        }

        public void removeSavable() {
            unregister();
        }

        @Override
        protected String findDisplayName() {
            return thePerson.toString();
        }

        @Override
        protected void handleSave() throws IOException {
            final Node node = getLookup().lookup(Node.class);
            if (node != null) {
                final SavablePersonCapability savable
                        = node.getLookup().lookup(SavablePersonCapability.class);
                if (savable != null) {
                    updateModel();
                    // After updating the model, 
                    // make a copy for the background thread
                    final Person p = new Person(thePerson);
                    clearSaveCapability();
                    changeOK = true;
                    // perform the save on a background thread
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                        @Override
                        protected Void doInBackground() throws Exception {
                            ProgressHandle handle = ProgressHandleFactory.createHandle(Bundle.CTL_PersonUpdating(p));
                            try {
                                handle.start();
                                // testing only!!
//                                Thread.sleep(1000);
                                savable.save(p);                                
                            } catch (IOException ex) {
                                logger.log(Level.WARNING, "handleSave", ex);
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

        @Override
        public boolean equals(Object other) {
            if (other instanceof SavableViewCapability) {
                SavableViewCapability sv = (SavableViewCapability) other;
                return tc() == sv.tc();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }

        PersonEditorTopComponent tc() {
            return PersonEditorTopComponent.this;
        }
    }

}
