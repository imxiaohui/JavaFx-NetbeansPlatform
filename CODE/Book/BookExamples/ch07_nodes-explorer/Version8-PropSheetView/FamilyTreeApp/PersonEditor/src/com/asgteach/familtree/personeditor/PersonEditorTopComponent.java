/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familtree.personeditor;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.LifecycleManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familtree.personeditor//PersonEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PersonEditorTopComponent",
        iconBase = "com/asgteach/familtree/personeditor/personIcon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.asgteach.familtree.personeditor.PersonEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PersonEditorAction",
        preferredID = "PersonEditorTopComponent"
)
@Messages({
    "CTL_PersonEditorAction=PersonEditor",
    "CTL_PersonEditorTopComponent=PersonEditor Window",
    "HINT_PersonEditorTopComponent=This is a PersonEditor window"
})
public final class PersonEditorTopComponent extends TopComponent {

    private FamilyTreeManager ftm;
    private Person thePerson = null;
    private static final Logger logger = Logger.getLogger(
            PersonEditorTopComponent.class.getName());
    private boolean changeOK = false;
    private Lookup.Result<Person> lookupResult = null;

    public PersonEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_PersonEditorTopComponent());
        setToolTipText(Bundle.HINT_PersonEditorTopComponent());

    }

    private void configureListeners() {
        // add action listener to Update button
        updateButton.addActionListener(updateListener);
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

    private void updateForm() {
        changeOK = false;
        updateButton.setEnabled(false);
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
    }

    private void clearForm() {
        updateButton.setEnabled(false);
        changeOK = false;
        firstTextField.setText("");
        middleTextField.setText("");
        lastTextField.setText("");
        suffixTextField.setText("");
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        unknownButton.setSelected(false);
        genderButtonGroup.clearSelection();
        notesTextArea.setText("");
    }

    private void updateModel() {
        if (!changeOK) {
            return;
        }
        updateButton.setEnabled(false);
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
    }

    private void modify() {
        updateButton.setEnabled(true);
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
        updateButton = new javax.swing.JButton();

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

        org.openide.awt.Mnemonics.setLocalizedText(updateButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.updateButton.text")); // NOI18N
        updateButton.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(updateButton))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateButton)
                .addContainerGap(18, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        configureListeners();
        
        // Listen for Person objects in the Global Selection lookup
        lookupResult = Utilities.actionsGlobalContext().lookupResult (Person.class);
        lookupResult.addLookupListener(lookupListener);
        checkLookup();
        
        // Listen for Person objects in the PersonViewerTopComponent lookup
//        TopComponent tc = WindowManager.getDefault().findTopComponent(
//                "PersonViewerTopComponent");
//        if (tc != null) {
//            lookupResult = tc.getLookup().lookupResult(Person.class);
//            checkLookup();
//            lookupResult.addLookupListener(lookupListener);
//        }
    }

    @Override
    public void componentClosed() {
        lookupResult.removeLookupListener(lookupListener);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

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

    // ActionListener for Update button
    private final ActionListener updateListener = (ActionEvent e) -> {
        updateModel();
        ftm.updatePerson(thePerson);
    };

    // LookupListener to detect changes in PersonViewer's Lookup
    LookupListener lookupListener = (LookupEvent le) -> checkLookup();
    
    private void checkLookup()  {
        Collection<? extends Person> allPeople = lookupResult.allInstances();
        
        // Make sure that the TopComponent with focus isn't this one
        TopComponent tc = TopComponent.getRegistry().getActivated();
        if (tc != null && tc.equals(this)) {
            logger.log(Level.FINER, "PersonEditorTopComponent has focus.");
            return;
        }
               
        if (!allPeople.isEmpty()) {
            thePerson = allPeople.iterator().next();
            logger.log(Level.FINE, "{0} selected", thePerson);
            updateForm();
        } else {
            logger.log(Level.FINE, "No selection");
            clearForm();
        }
    }
}
