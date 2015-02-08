// MyRectangle.java - Build a Rectangle with Path Transition
package racetrack;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MyRectangle {

    public static PathTransition build(int startX,
            int startY, int pathStrokeWidth) {

        Path path = new Path(new MoveTo(startX, startY),
                new ArcTo(100, 50, 0, startX + 270, startY, true, true),
                new LineTo(startX + 270, startY + 50),
                new ArcTo(100, 50, 0, startX, startY + 50, true, true),
                new ClosePath());
        path.setEffect(new DropShadow(10, 5, 5, Color.GRAY));
        path.setStrokeWidth(pathStrokeWidth);
        path.setStroke(Color.DARKGOLDENROD);
        path.setFill(Color.ORANGE);

        Rectangle rectangle = new Rectangle(startX - pathStrokeWidth, startY, 35, 20);
        rectangle.setFill(Color.YELLOW);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setStroke(Color.BLACK);
        rectangle.setRotate(90);
        
        PathTransition pathTransition = new PathTransition(Duration.seconds(6), path, rectangle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        return pathTransition;
    }
}
