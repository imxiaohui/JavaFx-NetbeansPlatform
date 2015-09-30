/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myfluentbind;


import java.util.Observable;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author ernesto
 */
public class MyFluentBind extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        DropShadow dropShadow = new DropShadow(10.0,Color.rgb(150, 50, 50, .688));
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        
        Label heigtOfScene = new Label();
        
        Label height = new Label();
        
        VBox verticalLayot = new VBox(10);
                
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setEffect(dropShadow);
        
        Rectangle rectangle = new Rectangle(10,50,Color.LEMONCHIFFON);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        height.textProperty().bind(rectangle.heightProperty().asString());
                
        stackPane.getChildren().addAll(rectangle,height);
        
        
        verticalLayot.getChildren().addAll(heigtOfScene,stackPane);
        Scene scene = new Scene(verticalLayot,400,200,Color.LIGHTSKYBLUE);
        primaryStage.setTitle("Fluent Binding");
        
        rectangle.widthProperty().bind(scene.widthProperty().divide(2));
        rectangle.heightProperty().bind(scene.heightProperty().divide(2));
        heigtOfScene.textProperty().bind(scene.heightProperty().asString());
        rectangle.opacityProperty().bind(scene.widthProperty().add(scene.heightProperty()).divide(1000));
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
