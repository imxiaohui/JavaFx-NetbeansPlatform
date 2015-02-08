/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asgteach.bindings;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author gail
 */
public class MyCustomBind extends Application {
 
    @Override
    public void start(Stage stage) {
        
        DropShadow dropShadow = new DropShadow(10.0, Color.rgb(150, 50, 50, .688));
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setEffect(dropShadow);
        
        Rectangle rectangle = new Rectangle(100, 50, Color.LEMONCHIFFON);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        
        Text text = new Text();
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        
        stackPane.getChildren().addAll(rectangle, text);

        final Scene scene = new Scene(stackPane, 400, 200, Color.LIGHTSKYBLUE);
        stage.setTitle("Custom Binding"); 
        
        rectangle.widthProperty().bind(scene.widthProperty().divide(2));
        rectangle.heightProperty().bind(scene.heightProperty().divide(2));
        
        DoubleBinding opacityBinding = new DoubleBinding() {
            {
                // List the dependencies with super.bind()
                super.bind(scene.widthProperty(), scene.heightProperty());
            }
            
            @Override
            protected double computeValue() {
                // Return the computed value
                double opacity = (scene.getWidth() + scene.getHeight()) / 1000;
                return (opacity > 1.0) ? 1.0 : opacity;
            }
            
        };
        rectangle.opacityProperty().bind(opacityBinding);
        text.textProperty().bind((Bindings.format("opacity = %.2f", opacityBinding)));
        
        ObjectBinding<Color> colorBinding = new ObjectBinding<Color>() {
            {
                super.bind(scene.fillProperty());
            }

            @Override
            protected Color computeValue() {
                if (scene.getFill() instanceof Color) {
                    return ((Color)scene.getFill()).darker();
                } else {
                    return Color.GRAY;
                }
            }
        };
        
        
        text.fillProperty().bind(colorBinding);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
