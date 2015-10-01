/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.genderviewer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familytree.genderviewer//Gender//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "GenderTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.asgteach.familytree.genderviewer.GenderTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_GenderAction",
        preferredID = "GenderTopComponent"
)
@Messages({
    "CTL_GenderAction=Gender",
    "CTL_GenderTopComponent=Gender Window",
    "HINT_GenderTopComponent=This is a Gender window"
})
public final class GenderTopComponent extends TopComponent implements ExplorerManager.Provider {
    
    private final ExplorerManager em = new ExplorerManager();

    public GenderTopComponent() {
        initComponents();
        setName(Bundle.CTL_GenderTopComponent());
        setToolTipText(Bundle.HINT_GenderTopComponent());
        
        BeanTreeView view = new BeanTreeView();
        add(view,BorderLayout.CENTER);
        associateLookup(ExplorerUtils.createLookup(em, this.getActionMap()));
        em.setRootContext(new RootNode());
        
        for(Node node : em.getRootContext().getChildren().getNodes()){
            view.expandNode(node);
        }
        getActionMap().put("MyCoolRefreshAction", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                em.setRootContext(new RootNode());
            }
        });
        //Opening the properties window on startup.
        TopComponent tc = WindowManager.getDefault().findTopComponent("properties");
        if(tc != null)
            tc.open();

    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        ExplorerUtils.activateActions(em, true);
    }

    @Override
    public void componentClosed() {
        ExplorerUtils.activateActions(em, false);
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

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
}
