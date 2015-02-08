/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myrectangleapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author gail
 */
public class MyRectangleApp extends Application {
    
    // start() is the main entry point for all JavaFX applications
    @Override
    public void start(Stage primaryStage) {       
      
        Rectangle rectangle = new Rectangle(200, 100, Color.CORNSILK);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        rectangle.setEffect(new DropShadow(10, 5, 5, Color.GRAY));
        
        rectangle.setStyle("-fx-fill: "
                + "linear-gradient(#ffd65b, #e68400),"
                + "linear-gradient(#ffef84, #f2ba44),"
                + "linear-gradient(#ffea6a, #efaa22),"
                + "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
                + "linear-gradient(from 0% 0% to 15% 50%, "
                + "rgba(255,255,255,0.9), rgba(255,255,255,0));");
                   
        Text text = new Text("My Rectangle");
        text.setFont(new Font("Verdana Bold", 18));
        text.setEffect(new Reflection());
        
        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(200);
        stackPane.setPrefWidth(400);
        stackPane.getChildren().addAll(rectangle, text);

        final Scene scene = new Scene(stackPane, Color.LIGHTBLUE);
        primaryStage.setTitle("My Rectangle App");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    // main() is invoked when running the JavaFX application from NetBeans
    // but is ignored when launched through JavaFX deployment (packaging)
    public static void main(String[] args) {
        launch(args);
    }
    
}
