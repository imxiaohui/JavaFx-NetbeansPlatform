package personswingappswingworkerstatus;

import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Person.Gender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 * PersonSwingApp. 
 * 
 * Seccion 2.5 libro.
 * 
 * @author ernesto
 */
public class PersonJFrame extends javax.swing.JFrame {
    
    //Variables de la Clase
    private final DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("People"); //Nodo padre para las personas.
    private final FamilyTreeManager ftm = FamilyTreeManager.getInstance();
    private Person thePerson = null;
    private static final Logger logger = Logger.getLogger(PersonJFrame.class.getName());
    private boolean changeOk = false;
   
    
    /**
     * Creates new form PersonJFrame.
     * Constructor Privado. solo puede ser invocado por el método newInstance.
     */
    private PersonJFrame() {
        
       logger.setLevel(Level.FINE);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
        
        try{
            FileHandler fileHandler = new FileHandler();
            
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
            logger.log(Level.FINE,"Created file handler");
        }catch(IOException | SecurityException ex){
            logger.log(Level.SEVERE,"Couldn't create handler");
        }
        
        buildData();
        initComponents(); //initComponents es el método que construye el frame.
        personTree.getSelectionModel().setSelectionMode(
                 TreeSelectionModel.SINGLE_TREE_SELECTION);
        createNodes(top);
    }
        
    //Asigna objetos listener a los respectivos generadores de eventos
    private void configureListeners(){
        ftm.addPropertyChangeListener(familyTreeListener);
        personTree.addTreeSelectionListener(treeSelectionListener);
        updateButton.addActionListener(updateListener);
        firstTextField.getDocument().addDocumentListener(docListener);
        middleTextField.getDocument().addDocumentListener(docListener);
        lastTextField.getDocument().addDocumentListener(docListener);
        suffixTextField.getDocument().addDocumentListener(docListener);
        notesTextArea.getDocument().addDocumentListener(docListener);
        maleButton.addActionListener(radioButtonListener);
        femaleButton.addActionListener(radioButtonListener);
        unknownButton.addActionListener(radioButtonListener);
        processAllButton.addActionListener(processAllLisener);
        logger.log(Level.FINE, "Listeners created");
    }
    
    
    /*
     * Método que arranca despues de llamar a main para obtener una instancia del frame.
     * Es importante recordar que para cualquier Swing App el uso de métodos
     * newInstance nos permite inicializar listener en forma thread-safe.
     * Una vez que el constructor regresa, se llama al método configureLiseners
    `* para que los listeners se creen en threadSafe.
     */
    public static PersonJFrame newInstance(){
        PersonJFrame pjf = new PersonJFrame();
        pjf.configureListeners();
        return pjf;
    }
    
    //Método de inicialización. Simula la carga de info. No es necesario
    private void buildData(){
        ftm.addPerson(new Person("Homer","Simpson",Person.Gender.MALE));
        ftm.addPerson(new Person("Mage","Simpson",Person.Gender.FEMALE));
        ftm.addPerson(new Person("Bart","Simpson",Person.Gender.MALE));
        ftm.addPerson(new Person("Lisa","Simpson",Person.Gender.FEMALE));
        ftm.addPerson(new Person("Maggie","Simpson",Person.Gender.FEMALE));
        logger.log(Level.FINE,ftm.getAllPeople().toString());
    }
    
    //Método que obtiene la Lista de personas del familyTreeManager y las agrega
    //al árbol en forma de nodos.
    private void createNodes(DefaultMutableTreeNode top){
        ftm.getAllPeople().forEach(p->top.add(new DefaultMutableTreeNode(p)));
    }
       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 
     * initcomponents es el método que configura el form
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderButtonGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        personTree = new javax.swing.JTree(top);
        jPanel1 = new javax.swing.JPanel();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesTextArea = new javax.swing.JTextArea();
        updateButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        processAllButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        statusTextArea = new javax.swing.JTextArea();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(personTree);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel4.setText("Suffix");

        jLabel2.setText("Middle");

        jLabel3.setText("Last");

        jLabel1.setText("First");

        genderButtonGroup.add(maleButton);
        maleButton.setText("Male");

        genderButtonGroup.add(femaleButton);
        femaleButton.setText("Female");

        genderButtonGroup.add(unknownButton);
        unknownButton.setText("Unknown");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
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
                    .addContainerGap(24, Short.MAX_VALUE)))
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

        jLabel5.setText("Notes");

        notesTextArea.setColumns(20);
        notesTextArea.setRows(5);
        jScrollPane2.setViewportView(notesTextArea);

        updateButton.setText("Update");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(updateButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateButton)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        updateButton.setEnabled(false);

        processAllButton.setText("Process All");

        statusTextArea.setColumns(20);
        statusTextArea.setRows(5);
        jScrollPane3.setViewportView(statusTextArea);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(processAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(processAllButton)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 19, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PersonJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        SwingUtilities.invokeLater(()->
                PersonJFrame.newInstance().setVisible(true));       
    }
    
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lastTextField;
    private javax.swing.JRadioButton maleButton;
    private javax.swing.JTextField middleTextField;
    private javax.swing.JTextArea notesTextArea;
    private javax.swing.JTree personTree;
    private javax.swing.JButton processAllButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextArea statusTextArea;
    private javax.swing.JTextField suffixTextField;
    private javax.swing.JRadioButton unknownButton;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    //Llamado desde treeSelectionListener
    private void editPerson(Person person){
        thePerson = person;
        updateForm();
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
        if(thePerson.getGender().equals(Gender.MALE)){
            maleButton.setSelected(true);
        }
        if(thePerson.getGender().equals(Gender.FEMALE)){
            femaleButton.setSelected(true);
        }
        if(thePerson.getGender().equals(Gender.UNKNOWN)){
            unknownButton.setSelected(true);
        }
        notesTextArea.setText(thePerson.getNotes());
        changeOk = true;
    }
    
    private void modify(){
        updateButton.setEnabled(true);
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
            thePerson.setGender(Gender.MALE);
        }
        if(femaleButton.isSelected()){
            thePerson.setGender(Gender.FEMALE);
        }
        if(unknownButton.isSelected()){
            thePerson.setGender(Gender.UNKNOWN);
        }
        thePerson.setNotes(notesTextArea.getText());
    }
    
    //Listener del boton de Update. Actualiza el objeto thePerson con el método updateModel.
    //Despues, actualiza el familyTreeManager
    private final ActionListener updateListener = (ActionEvent evt)->{
        updateModel();
        final Person person = new Person(thePerson); //Guardo una copia del objeto que modificaré
        SwingWorker<Person,Void> worker = new SwingWorker<Person,Void>(){

            @Override
            protected Person doInBackground() throws Exception {
                //simulate long process
                try{
                    Thread.sleep(5000);
                }catch(InterruptedException ex){
                    logger.log(Level.WARNING,null,ex);
                }
                
                //save in background thread
                logger.log(Level.FINE,"calling frm for person {0}",person);
                ftm.updatePerson(person);
                return person;
            }
            
            @Override
            protected void done(){
            
                try{
                    if(!isCancelled()){
                        logger.log(Level.FINE,"Done saving person {0}",get());
                    }
                }catch(InterruptedException | ExecutionException ex){
                    Logger.getLogger(PersonJFrame.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        };
        worker.execute();
    };
    
    
    private final ActionListener processAllLisener = (ActionEvent evt)->{
    
        final Collection<Person> processList = ftm.getAllPeople();
        processAllButton.setEnabled(false);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        logger.log(Level.FINE,"Process All requested for {0}", processList);
        SwingWorker<Collection<Person>,Person> worker = new SwingWorker<Collection<Person>, Person>() {
            
            final int count = processList.size();
            
            @Override
            protected Collection<Person> doInBackground() {
                int i = 0;
                for(Person person : processList){
                    try{
                        doProcess(person);
                        logger.log(Level.FINE,"Processing person {0}", person);
                        publish(person);
                        setProgress(100*(++i) / count);
                        Thread.sleep(500);
                    }catch(InterruptedException e){
                        logger.log(Level.WARNING,null,e);
                    }
                }
                return processList;
            }
            
            private void doProcess(Person p){
                p.setFirstName(p.getFirstName().toUpperCase());
                p.setMiddleName(p.getMiddleName().toUpperCase());
                p.setLastName(p.getLastName().toUpperCase());
                p.setSuffix(p.getSuffix().toUpperCase());
            }
            
            @Override
            protected void done(){
                try{
                    if(!isCancelled()){
                        logger.log(Level.FINE,"Done! processing all {0}",get());
                    }
                }catch(InterruptedException | ExecutionException e){
                    Logger.getLogger(PersonJFrame.class.getName()).log(Level.SEVERE,null,e);
                }
                
                progressBar.setValue(0);
                progressBar.setStringPainted(false);
                statusTextArea.setText("");
                processAllButton.setEnabled(true);
            }
            
            @Override
            protected void process(List<Person> chunks){
                chunks.stream().forEach((p)->{
                    statusTextArea.append(p + "\n");
                });
            }
        };
        
        worker.addPropertyChangeListener((PropertyChangeEvent e)->{
            if("progress".equals(e.getPropertyName())){
                progressBar.setValue((Integer) e.getNewValue());
            }
        });
        
        
        worker.execute();
    };
    
    
    //Listeners
    private final PropertyChangeListener familyTreeListener = (PropertyChangeEvent evt) -> {
        //Busco el nodo y lo actualizo
        if(evt.getNewValue() != null && evt.getPropertyName().equals(FamilyTreeManager.PROP_PERSON_UPDATED)){
            Person person = (Person) evt.getNewValue();
            DefaultTreeModel model = (DefaultTreeModel) personTree.getModel();
            for(int i = 0; i<model.getChildCount(top);i++){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getChild(top, i);
                if(person.equals(node.getUserObject())){
                    node.setUserObject(person);
                    model.nodeChanged(node);
                    logger.log(Level.FINE,"Node updated: {0}", node);
                    break;
                }
            }
        }
    };
    
    //Se activa cuando selecciono un nodo
    private final TreeSelectionListener treeSelectionListener = (TreeSelectionEvent e) -> {
        //Obtengo el nodo seleccionado
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) personTree.getLastSelectedPathComponent();
        if(node == null){ //Si el nodo es null
            updateButton.setEnabled(false); //desactivo los botones
            clearForm();
            return;
        }
        if(node.isLeaf()){ //si el nodo es hoja
            Person person = (Person) node.getUserObject(); //creo un objeto user
            logger.log(Level.FINE,"{0} selected", person);
            editPerson(person); //llamo al método editPerson
        }
        else{ //no es hoja (El nodo Person), desactivo botones.
            updateButton.setEnabled(false);
            clearForm();
        }
        
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
