/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreesupport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
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
        displayName = "#LBL_FamilyTree_VISUAL",
        iconBase = "com/asgteach/familytreesupport/check.png",
        mimeType = "text/x-familytree",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "FamilyTreeVisual",
        position = 2000
)
@Messages("LBL_FamilyTree_VISUAL=Visual")
public final class FamilyTreeVisualElement extends JPanel implements MultiViewElement {

    private final FamilyTreeDataObject ftDataObject;
    private final JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private ProxyLookup proxyLookup = null;
    private final InstanceContent instanceContent = new InstanceContent();
    private final LayerWidget layer;
    private EditorCookie editCookie = null;
    private StyledDocument doc = null;
    private final Scene scene = new Scene();
    private final Border border = BorderFactory.createRoundedBorder(10, 10, Color.yellow, Color.gray);
    private static final Logger logger = Logger.getLogger(FamilyTreeVisualElement.class.getName());

    public FamilyTreeVisualElement(Lookup lkp) {
        proxyLookup = new ProxyLookup(lkp, new AbstractLookup(instanceContent));
        ftDataObject = lkp.lookup(FamilyTreeDataObject.class);
        assert ftDataObject != null;
        initComponents();
        setLayout(new BorderLayout());
        layer = new LayerWidget(scene);
        scene.addChild(layer);
        add(new JScrollPane(scene.createView()));
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
                        refreshLines();
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
    }

    private void refreshLines() {
        logger.log(Level.INFO, "refreshLines");
        layer.removeChildren();
        try {
            String docString = doc.getText(0, doc.getLength());
            logger.log(Level.INFO, "Document String size = {0}", doc.getLength());
            int i = 1;
            for (String text : docString.split("\n")) {
                if (!text.isEmpty()) {
                    logger.log(Level.INFO, "Making widget for {0}", text);
                    LabelWidget widget = makeWidget(text, new Point(20, 40 * i++));
                    layer.addChild(widget);
                }
            }
            scene.validate();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private LabelWidget makeWidget(String text, Point point) {
        LabelWidget widget = new LabelWidget(scene, text);
        widget.getActions().addAction(editorAction);
        widget.getActions().addAction(ActionFactory.createMoveAction());
        widget.setPreferredLocation(point);
        widget.setBorder(border);
        return widget;
    }

    private final WidgetAction editorAction = ActionFactory.createInplaceEditorAction(
            new LabelTextFieldEditor());

    private class LabelTextFieldEditor implements TextFieldInplaceEditor {

        @Override
        public boolean isEnabled(Widget widget) {
            return true;
        }

        @Override
        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }

        @Override
        public void setText(Widget widget, String text) {
            LabelWidget lw = (LabelWidget) widget;
            String oldText = lw.getLabel();
            lw.setLabel(text);
            updateDocument(lw, oldText.length());
        }

    }

    private void updateDocument(LabelWidget labelWidget, int oldTextSize) {
        if (editCookie != null) {
            doc = editCookie.getDocument();
            if (doc != null) {
                try {                    
                    //normalize the file--get rid of extra newlines
                    String docString = doc.getText(0, doc.getLength());
                    logger.log(Level.INFO, "Document String size = {0}", doc.getLength());
                    StringBuilder sb = new StringBuilder();
                    for (String text : docString.split("\n")) {
                        if (!text.isEmpty()) {
                            sb.append(text);
                            sb.append("\n");
                        }
                    }
                    // replace with normalized text
                    doc.remove(0, doc.getLength());
                    doc.insertString(0, sb.toString(), null);

                    // find the starting position in the document for the
                    // new String
                    int startPosition = 0;
                    for (Widget widget : layer.getChildren()) {
                        if (widget instanceof LabelWidget) {
                            LabelWidget lw = ((LabelWidget) widget);
                            if (lw.equals(labelWidget)) {
                                logger.log(Level.INFO, "current document length = {0}", doc.getLength());
                                logger.log(Level.INFO, "startPosition={0}", startPosition);
                                logger.log(Level.INFO, "oldTextSize={0}", oldTextSize);

                                doc.remove(startPosition, oldTextSize + 1);
                                if (labelWidget.getLabel().isEmpty()) {
                                    // get rid of the widget if it's empty
                                    refreshLines();
                                } else {
                                    doc.insertString(startPosition, labelWidget.getLabel() + "\n", null);
                                }
                                break;
                            } else {
                                startPosition += lw.getLabel().length() + 1;
                            }
                        }
                    }
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                logger.log(Level.WARNING, "updateDocument: document is null");
            }
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
    }

    @Override
    public void componentClosed() {
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
            refreshLines();
        }
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
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

}
