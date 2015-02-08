// MyFluentBind.java - Fluent binding for Rectangle size, opacity
package asgteach.bindings;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyFluentBind extends Application {

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
        
        stackPane.getChildren().add(rectangle);

        Scene scene = new Scene(stackPane, 400, 200, Color.LIGHTSKYBLUE);
        stage.setTitle("Fluent Binding"); 
        
        rectangle.widthProperty().bind(scene.widthProperty().divide(2));
        rectangle.heightProperty().bind(scene.heightProperty().divide(2));
        
        rectangle.opacityProperty().bind(
              scene.widthProperty().add(scene.heightProperty())
                        .divide(1000));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
