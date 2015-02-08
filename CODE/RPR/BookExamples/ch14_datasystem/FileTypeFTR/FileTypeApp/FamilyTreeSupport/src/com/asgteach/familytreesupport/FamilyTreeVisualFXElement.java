/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreesupport;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
        displayName = "#LBL_FamilyTreeFX_VISUAL",
        iconBase = "com/asgteach/familytreesupport/check.png",
        mimeType = "text/x-familytree",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "FamilyTreeFXVisual",
        position = 2000
)
@Messages("LBL_FamilyTreeFX_VISUAL=VisualFX")
public final class FamilyTreeVisualFXElement extends JPanel
        implements MultiViewElement, PropertyChangeListener {

    private final FamilyTreeDataObject ftDataObject;
    private final JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private ProxyLookup proxyLookup = null;
    private final InstanceContent instanceContent = new InstanceContent();
    private EditorCookie editCookie = null;
    private StyledDocument doc = null;
    private static JFXPanel fxPanel;
    private FtrVisualFXController controller;
    private static final Logger logger = Logger.getLogger(FamilyTreeVisualFXElement.class.getName());

    public FamilyTreeVisualFXElement(Lookup lkp) {
        proxyLookup = new ProxyLookup(lkp, new AbstractLookup(instanceContent));
        ftDataObject = lkp.lookup(FamilyTreeDataObject.class);
        assert ftDataObject != null;
        initComponents();
        setLayout(new BorderLayout());
        init();
        instanceContent.add(new Droppable() {

            @Override
            public void drop(String text) {
                // add the text to the end of the document
                // then refreshLines()
                if (doc != null) {
                    try {
                        if (!(doc.getText(doc.getLength(), 1)).endsWith("\n")) {
                            doc.insertString(doc.getLength(), "\n", null);
                            logger.log(Level.INFO, "Need ending newline");
                        }
                        doc.insertString(doc.getLength(), text + "\n", null);
                        String docString = doc.getText(0, doc.getLength());
                        Platform.runLater(() -> controller.refresh(docString));
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
    }

    private void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        // need to wait for this to complete!
        // so that we can safely add ourselves as a
        // property change listener
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                createScene();
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }

    private void createScene() {
        try {
            URL location = getClass().getResource("FtrVisualFX.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = (Parent) fxmlLoader.load(location.openStream());
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            fxPanel.setScene(scene);
            controller = (FtrVisualFXController) fxmlLoader.getController();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public String getName() {
        return "FamilyTreeVisualElement";
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
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return proxyLookup;
    }

    @Override
    public void componentOpened() {
        logger.log(Level.INFO, "Component Opened");
        // requires a countdown latch to make sure
        // controller is properly initialized
        controller.addPropertyChangeListener(this);

    }

    @Override
    public void componentClosed() {
        controller.removePropertyChangeListener(this);

    }

    @Override
    public void componentShowing() {
        logger.log(Level.INFO, "Component Showing");
        editCookie = ftDataObject.getLookup().lookup(EditorCookie.class);
        if (editCookie != null) {
            doc = editCookie.getDocument();
            if (doc == null) {
                editCookie.open();      // open
                try {
                    doc = editCookie.openDocument();            // get document
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                    return;
                }
            }
            try {
                String docString = doc.getText(0, doc.getLength());
                final CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    try {
                        controller.refresh(docString);
                    } finally {
                        latch.countDown();
                    }
                });
                try {
                    latch.await();
                    // wait for refresh() to complete before 
                    // initiating the fade-in
                    Platform.runLater(() -> controller.doFadeIn());
                } catch (InterruptedException ex) {
                    logger.log(Level.WARNING, null, ex);
                }
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }

    @Override
    public void componentHidden() {
        Platform.runLater(() -> controller.clear());
    }

    @Override
    public void componentActivated() {
        logger.log(Level.INFO, "Component Activated");
        
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // the controller made changes
        // we have to update the underlying document
        // and maybe refresh the JavaFX scene
        if (evt.getPropertyName().equals(FtrVisualFXController.PROP_DOCELEMENT_REMOVED)
                || evt.getPropertyName().equals(FtrVisualFXController.PROP_DOCUMENT_UPDATED)) {
            // update the document
            if (doc != null) {
                try {
                    doc.remove(0, doc.getLength());
                    doc.insertString(0, (String) evt.getNewValue(), null);
                    Platform.runLater(() -> controller.refresh((String) evt.getNewValue()));
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }
    }

}
