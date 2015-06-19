/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxcalculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ernesto
 */
public class TaxCalculator extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Tax Calculator");
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25,25,25,25));
        Scene scene = new Scene(pane,300,275);
                
        Text sceneTitle = new Text("Tax Calculator");
        sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        pane.add(sceneTitle, 0,0,2,1);
        Label total = new Label("Income");
        pane.add(total,0,1);
        final TextField totalField = new TextField();
        pane.add(totalField,1,1);
        Label percent = new Label("% TAX:");
        pane.add(percent, 0, 2);
        final TextField percentField = new TextField();
        pane.add(percentField,1,2);
        
        Button calculateButton = new Button("Calculate");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(calculateButton);
        pane.add(hbox,1,4);
        
        final Text taxMessage = new Text();
        pane.add(taxMessage, 1, 6);
        
        calculateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Double income = Double.parseDouble(totalField.getText());
                Double tax = Double.parseDouble(percentField.getText())/100.0;
                taxMessage.setText("Tax incurred:"+income*tax);
            }
        });
        
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
