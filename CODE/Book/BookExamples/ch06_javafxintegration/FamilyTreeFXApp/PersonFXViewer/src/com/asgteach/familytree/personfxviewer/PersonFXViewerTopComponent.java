/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfxviewer;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.LifecycleManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familytree.personfxviewer//PersonFXViewer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PersonFXViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "com.asgteach.familytree.personfxviewer.PersonFXViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PersonFXViewerAction",
        preferredID = "PersonFXViewerTopComponent"
)
@Messages({
    "CTL_PersonFXViewerAction=PersonFXViewer",
    "CTL_PersonFXViewerTopComponent=PersonFXViewer Window",
    "HINT_PersonFXViewerTopComponent=This is a PersonFXViewer window"
})
public final class PersonFXViewerTopComponent extends TopComponent {

    private static JFXPanel fxPanel;
    private PersonFXViewerController controller;
    private static final Logger logger = Logger.getLogger(PersonFXViewerTopComponent.class.getName());

    public PersonFXViewerTopComponent() {
        initComponents();
        setName(Bundle.CTL_PersonFXViewerTopComponent());
        setToolTipText(Bundle.HINT_PersonFXViewerTopComponent());
        setLayout(new BorderLayout());
        init();

    }

    private void init() {
        logger.log(Level.INFO, "PersonFXViewerTopComponent init() called");
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);

        // we need to wait for createScene() to finish
        final CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                createScene();
            } finally {
                latch.countDown();
            }
        });

        try {
            latch.await(); // wait to finish
            // get the InstanceContent from the controller
            associateLookup(new AbstractLookup(controller.getInstanceContent()));
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, "JavaFX initialization interrupted");
            LifecycleManager.getDefault().exit();
        }
    }

    private void createScene() {
        logger.log(Level.INFO, "PersonFXViewerTopComponent createScene() called");
        try {
            URL location = getClass().getResource("PersonFXViewer.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            logger.log(Level.INFO, "About to invoke load for {0} ", location);
            Parent root = (Parent) fxmlLoader.load(location.openStream());
            logger.log(Level.INFO, "Finished fxmlLoader.load");
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            controller = (PersonFXViewerController) fxmlLoader.getController();
            logger.log(Level.INFO, "Finished getting controller");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
}