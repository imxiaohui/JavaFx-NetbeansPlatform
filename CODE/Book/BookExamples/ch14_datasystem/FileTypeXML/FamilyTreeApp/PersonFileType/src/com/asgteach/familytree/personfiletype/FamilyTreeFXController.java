/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfiletype;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author gail
 */
public class FamilyTreeFXController implements Initializable {

    @FXML
    private FlowPane mypane;
    private final SequentialTransition seqTran = new SequentialTransition();
    private static final Logger logger = Logger.getLogger(FamilyTreeFXController.class.getName());
    private final Image image = new Image(FamilyTreeFXController.class.getResourceAsStream("personIcon32.png"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mypane.getStyleClass().add("ftr-pane");
        mypane.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                doFadeIn();
            }
        });
    }

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
    // Document is a parsed XML org.w3c.dom.Document
    public void refresh(Document doc) {
        clear();
        NodeList nodeList = doc.getElementsByTagName("person");
        logger.log(Level.INFO, "Making Elements for {0} <person> tags", nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            //For each node in the list, create a org.w3c.dom.Node:
            org.w3c.dom.Node personNode = nodeList.item(i);
            NamedNodeMap map = personNode.getAttributes();
            Person person;
            if (map != null) {
                person = new Person();
                person.setLastname("Unknown");
                for (int j = 0; j < map.getLength(); j++) {
                    org.w3c.dom.Node attrNode = map.item(j);
                    if (attrNode.getNodeName().equals("firstname")) {
                        person.setFirstname(attrNode.getNodeValue());
                    }
                    if (attrNode.getNodeName().equals("lastname")) {
                        person.setLastname(attrNode.getNodeValue());
                    }
                }
                StackPane stack = makeElement(person.toString());
                // Add the StackPane to the top-level Node (mypane)
                mypane.getChildren().add(stack);
                FadeTransition ft = new FadeTransition(Duration.millis(650), stack);
                ft.setFromValue(0);
                ft.setToValue(1);
                seqTran.getChildren().add(ft);
            }
        }
    }

    private StackPane makeElement(String displayName) {
        // Build the StackPane
        StackPane stack = new StackPane();

        // Build the Text
        final Text ftrText = new Text(displayName);
        ftrText.getStyleClass().add("ftr-text");
        ftrText.setEffect(new Reflection());

        // Build the ImageView, put ImageView & Text in VBox
        ImageView iv = new ImageView(image);
        VBox vbox = new VBox(3, iv, ftrText);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10, 0, 0, 0));

        // Build the Rectangle
        Rectangle ftrRectangle = new Rectangle(ftrText.getBoundsInParent().getWidth() + 40, 90);
        ftrRectangle.setArcHeight(20);
        ftrRectangle.setArcWidth(20);
        ftrRectangle.getStyleClass().add("ftr-rectangle");

        // Add the Rectangle, VBox to StackPane
        stack.getChildren().addAll(ftrRectangle, vbox);
        return stack;
    }

}
