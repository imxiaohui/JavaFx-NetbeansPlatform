package com.asgteach.familytree.personeditor;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
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
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familytree.personeditor//PersonEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PersonEditorTopComponent",
        iconBase = "com/asgteach/familytree/personeditor/personIcon.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.asgteach.familytree.personeditor.PersonEditorTopComponent")
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
    private static final Logger logger = Logger.getLogger(PersonEditorTopComponent.class.getName());
    private boolean changeOk = false;
    private Lookup.Result<Person> lookUpResult = null;
    
    public PersonEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_PersonEditorTopComponent());
        setToolTipText(Bundle.HINT_PersonEditorTopComponent());

    }

    
    //Asigna objetos listener a los respectivos generadores de eventos
    private void configureListeners(){
        
        updateButton.addActionListener(updateListener);
        
        firstTextField.getDocument().addDocumentListener(docListener);
        middleTextField.getDocument().addDocumentListener(docListener);
        lastTextField.getDocument().addDocumentListener(docListener);
        suffixTextField.getDocument().addDocumentListener(docListener);
        notesTextArea.getDocument().addDocumentListener(docListener);

        maleButton.addActionListener(radioButtonListener);
        femaleButton.addActionListener(radioButtonListener);
        unknownButton.addActionListener(radioButtonListener);
    }
    
    
    //del objeto thePerson al form. Cuando selecciono un objeto en el Tree, se ejecuta
    //este método para actualizar la vista.
    private void updateForm(){
        changeOk = false;
        updateButton.setEnabled(false);
        firstTextField.setText(thePerson.getFirstName());
        middleTextField.setText(thePerson.getMiddleName());
        lastTextField.setText(thePerson.getLastName());
        suffixTextField.setText(thePerson.getSuffix());
        if(thePerson.getGender().equals(Person.Gender.MALE)){
            maleButton.setSelected(true);
        }
        if(thePerson.getGender().equals(Person.Gender.FEMALE)){
            femaleButton.setSelected(true);
        }
        if(thePerson.getGender().equals(Person.Gender.UNKNOWN)){
            unknownButton.setSelected(true);
        }
        notesTextArea.setText(thePerson.getNotes());
        changeOk = true;
    }
    
    private void clearForm(){
        updateButton.setEnabled(false);
        changeOk=false;
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
    
    //Del form al objeto thePerson- Cuando se da click al boton update, se alimenta
    // el objeto thePerson, para despues mandarse actualizar al FamilyTreeManager.
    private void updateModel(){
        if(!changeOk){
            return;
        }
        updateButton.setEnabled(false);
        thePerson.setFirstName(firstTextField.getText());
        thePerson.setMiddleName(middleTextField.getText());
        thePerson.setLastName(lastTextField.getText());
        thePerson.setSuffix(suffixTextField.getText());
        if(maleButton.isSelected()){
            thePerson.setGender(Person.Gender.MALE);
        }
        if(femaleButton.isSelected()){
            thePerson.setGender(Person.Gender.FEMALE);
        }
        if(unknownButton.isSelected()){
            thePerson.setGender(Person.Gender.UNKNOWN);
        }
        thePerson.setNotes(notesTextArea.getText());
    }
    
    private void modify(){
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
        jPanel2 = new javax.swing.JPanel();
        suffixTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        firstTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lastTextField = new javax.swing.JTextField();
        middleTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        maleButton = new javax.swing.JRadioButton();
        femaleButton = new javax.swing.JRadioButton();
        unknownButton = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        notesTextArea = new javax.swing.JTextArea();
        updateButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel1.text")); // NOI18N

        genderButtonGroup.add(maleButton);
        org.openide.awt.Mnemonics.setLocalizedText(maleButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.maleButton.text")); // NOI18N

        genderButtonGroup.add(femaleButton);
        org.openide.awt.Mnemonics.setLocalizedText(femaleButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.femaleButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(unknownButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.unknownButton.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(21, 21, 21)
                                            .addComponent(jLabel3)))
                                    .addGap(39, 39, 39))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(jLabel4)
                                    .addGap(36, 36, 36)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(firstTextField)
                                .addComponent(middleTextField)
                                .addComponent(lastTextField)
                                .addComponent(suffixTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(68, 68, 68)
                            .addComponent(maleButton)
                            .addGap(30, 30, 30)
                            .addComponent(femaleButton)
                            .addGap(18, 18, 18)
                            .addComponent(unknownButton)))
                    .addContainerGap(131, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(firstTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(56, 56, 56))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(middleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGap(30, 30, 30)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(lastTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30, 30, 30)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(suffixTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGap(31, 31, 31)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(maleButton)
                        .addComponent(femaleButton)
                        .addComponent(unknownButton))
                    .addGap(55, 55, 55)))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.jLabel5.text")); // NOI18N

        notesTextArea.setColumns(20);
        notesTextArea.setRows(5);

        org.openide.awt.Mnemonics.setLocalizedText(updateButton, org.openide.util.NbBundle.getMessage(PersonEditorTopComponent.class, "PersonEditorTopComponent.updateButton.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(notesTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateButton)))
                .addContainerGap(43, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(notesTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(updateButton)
                .addContainerGap())
        );

        updateButton.setEnabled(false);
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
    private javax.swing.JPanel jPanel2;
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
        if(ftm == null){
            logger.log(Level.SEVERE, "Cannot get Family Tree Manager object");
            LifecycleManager.getDefault().exit();
        }
        configureListeners();
        lookUpResult = Utilities.actionsGlobalContext().lookupResult(Person.class);
        lookUpResult.addLookupListener(lookUpListener);
        checkLookUp();
//        TopComponent tc = WindowManager.getDefault().findTopComponent("PersonViewerTopComponent");
//        if(tc != null){
            //lookUpResult = tc.getLookup().lookupResult(Person.class);
//            lookUpResult = Utilities.actionsGlobalContext().lookupResult(Person.class);
//            checkLookUp();
//            lookUpResult.addLookupListener(lookUpListener);
//        }
    }

    @Override
    public void componentClosed() {
        lookUpResult.removeLookupListener(lookUpListener);
    }
    
    LookupListener lookUpListener = (LookupEvent le)->checkLookUp();
    
    private void checkLookUp(){
        
        Collection<? extends Person> allPeople = lookUpResult.allInstances();
        TopComponent tc =  TopComponent.getRegistry().getActivated();
        if(tc != null && tc.equals(this)){
            logger.log(Level.INFO,"PersonEditorTopComponent has focus.");
            return;
        }
        if(!allPeople.isEmpty()){
            thePerson = allPeople.iterator().next();
            logger.log(Level.FINE,"{0} selected", thePerson);
            updateForm();
        }
        else{
            logger.log(Level.FINE,"No selection");
            clearForm();
        }
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
    
    
    
    //Listener del boton de Update. Actualiza el objeto thePerson con el método updateModel.
    //Despues, actualiza el familyTreeManager
    private final ActionListener updateListener = (ActionEvent evt)->{
        updateModel();
        ftm.updatePerson(thePerson);
    };
    
    private DocumentListener docListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            if(changeOk)
                modify();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if(changeOk)
                modify();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if(changeOk)
                modify();
        }
    };
    
    private final ActionListener radioButtonListener = (ActionEvent evt)->{
        if(changeOk)
            modify();
    };
}
