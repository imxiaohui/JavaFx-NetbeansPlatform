/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytreesupport;

import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author gail
 */
public class FtrVisualFXController implements Initializable {

    @FXML
    private FlowPane mypane;
    private SwingPropertyChangeSupport propChangeSupport = null;
    private final SequentialTransition seqTran = new SequentialTransition();

    public static final String PROP_DOCUMENT_UPDATED = "DocumentUpdated";
    public static final String PROP_DOCELEMENT_REMOVED = "DocElementRemoved";

    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;
    private double orgTranslateY;

    private static final Logger logger = Logger.getLogger(FtrVisualFXController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        propChangeSupport = new SwingPropertyChangeSupport(this, true);
        mypane.getStyleClass().add("ftr-pane");
    }

    EventHandler<MouseEvent> nodeOnMousePressedEventHandler = ((event) -> {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = ((Node) (event.getSource())).getTranslateX();
            orgTranslateY = ((Node) (event.getSource())).getTranslateY();
        }
    });

    EventHandler<MouseEvent> nodeOnMouseDraggedEventHandler = ((event) -> {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((Node) (event.getSource())).setTranslateX(newTranslateX);
            ((Node) (event.getSource())).setTranslateY(newTranslateY);
        }
    });

    // Invoked by Swing visual element on the JavaFX Application Thread
    public void doFadeIn() {
        seqTran.playFromStart();
    }

    // Invoked by Swing visual element on the JavaFX Application Thread
    public void clear() {
        seqTran.getChildren().clear();
        mypane.getChildren().clear();
    }

    // Invoked by Swing visual element on the JavaFX Application Thread
    public void refresh(String docString) {
        clear();
        for (String line : docString.split("\n")) {
            if (!line.isEmpty()) {
                logger.log(Level.INFO, "Making widget for {0}", line);
                // Build the StackPane
                StackPane stack = new StackPane();

                // Build the Text
                final Text ftrText = new Text(line);
                ftrText.getStyleClass().add("ftr-text");
                ftrText.setEffect(new Reflection());

                // Build the Rectangle
                Rectangle ftrRectangle = new Rectangle(ftrText.getBoundsInParent().getWidth() + 40, 55);
                ftrRectangle.setArcHeight(30);
                ftrRectangle.setArcWidth(30);
                ftrRectangle.getStyleClass().add("ftr-rectangle");

                // Build a TextField for editing
                TextField tf = new TextField();
                tf.prefWidthProperty().bind(ftrRectangle.widthProperty());
                tf.setVisible(false);
                tf.setOnAction(event -> {
                    String oldText = ftrText.getText();
                    ftrText.setText(tf.getText());
                    logger.log(Level.INFO, "new Text |{0}|", tf.getText());
                    tf.setVisible(false);
                    updateDocument(ftrText, oldText.length(), docString);
                    ftrRectangle.setWidth(ftrText.getBoundsInParent().getWidth() + 30);
                });

                // Add the Rectangle, Text to StackPane
                stack.getChildren().addAll(ftrRectangle, ftrText, tf);

                stack.setOnMouseClicked(event -> {
                    // enable editing on a double-click event
                    if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                        tf.setText(ftrText.getText());
                        tf.setVisible(true);
                    }
                });
                // Add the drag handlers
                stack.setOnMousePressed(nodeOnMousePressedEventHandler);
                stack.setOnMouseDragged(nodeOnMouseDraggedEventHandler);

                // Add the StackPane to the top-level Node (mypane)
                mypane.getChildren().add(stack);
                FadeTransition ft = new FadeTransition(Duration.millis(650), stack);
                ft.setFromValue(0);
                ft.setToValue(1);
                seqTran.getChildren().add(ft);
            }
        }
    }

    private void updateDocument(Text theText, int oldTextSize, String docString) {
        //normalize the file--get rid of extra newlines
        StringBuilder sb = new StringBuilder();
        for (String text : docString.split("\n")) {
            if (!text.isEmpty()) {
                sb.append(text);
                sb.append("\n");
            }
        }
        // find the starting position in the document for the
        // new String
        int startPosition = 0;
        for (Node node : mypane.getChildren()) {
            if (node instanceof StackPane) {
                StackPane sp = (StackPane) node;
                for (Node childNode : sp.getChildren()) {
                    if (childNode instanceof Text) {
                        Text textNode = (Text) childNode;
                        if (textNode.equals(theText)) {
                            logger.log(Level.INFO, "current document length = {0}", sb.length());
                            logger.log(Level.INFO, "startPosition={0}", startPosition);
                            logger.log(Level.INFO, "oldTextSize={0}", oldTextSize);
                            if (theText.getText().isEmpty()) {
                                sb.delete(startPosition, startPosition + oldTextSize + 1);
                                // old value = docString
                                // new value = sb.toString
                                this.propChangeSupport.firePropertyChange(PROP_DOCELEMENT_REMOVED, docString, sb.toString());
                            } else {
                                sb.replace(startPosition, startPosition + oldTextSize, theText.getText());
                                // old value = docString
                                // new value = sb.toString
                                this.propChangeSupport.firePropertyChange(PROP_DOCUMENT_UPDATED, docString, sb.toString());
                            }
                            break;
                        } else {
                            // not our Text node
                            startPosition += textNode.getText().length() + 1;
                        }
                    }
                }

            }
        }

    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(
            PropertyChangeListener listener) {
        this.propChangeSupport.removePropertyChangeListener(listener);
    }

}
