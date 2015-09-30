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
 * Ejercicio 3.1
 * @author ernesto cantu
 * 19/06/2015
 */
public class MyRectangleApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Rectangle rectangle = new Rectangle(200,100,Color.CORNSILK);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        rectangle.setEffect(new DropShadow(10,5,5,Color.GRAY));
        
        Text text = new Text("My Rectangle");
        text.setFont(new Font("Verdana Bold",18));
        text.setEffect(new Reflection());
        
        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(200);
        stackPane.setPrefWidth(400);
        stackPane.getChildren().addAll(rectangle,text);
        
        final Scene scene = new Scene(stackPane,Color.LIGHTBLUE);
        primaryStage.setTitle("My Rectangle App");
        
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
