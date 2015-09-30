package mycustombind;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ernesto
 */
public class MyCustomBind extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        DropShadow dropShadow = new DropShadow(10.0,Color.rgb(150, 50, 50, .688));
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);
        
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setEffect(dropShadow);
        
        Rectangle rectangle = new Rectangle(10,50,Color.LEMONCHIFFON);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        
        Text text = new Text();
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        
        stackPane.getChildren().addAll(rectangle,text);
        
        Scene scene = new Scene(stackPane,400,200,Color.LIGHTSKYBLUE);
        primaryStage.setTitle("Custom Binding");
        
        rectangle.widthProperty().bind(scene.widthProperty().divide(2));
        rectangle.heightProperty().bind(scene.heightProperty().divide(2));
        
        DoubleBinding opacityBinding = new DoubleBinding() {
            {
                super.bind(scene.widthProperty(),scene.heightProperty());
            }
            @Override
            protected double computeValue() {
                double opacity = (scene.getWidth() + scene.getHeight()) / 1000;
                return (opacity >1.0)? 1.0 : opacity;
            }
        };
        
        rectangle.opacityProperty().bind(opacityBinding);
        text.textProperty().bind((Bindings.format("opacity = %.2f ",opacityBinding)));
        
        
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
