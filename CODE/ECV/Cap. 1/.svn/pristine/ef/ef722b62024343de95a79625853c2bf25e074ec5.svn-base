/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.welcomewindow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author gail
 */
@Messages({
    "CTL_BannerText=Who Is In Your Family Tree?",
    "CTL_BottomText=Family Tree - A NetBeans Platform Application",
    "CTL_License=Simpson Character Images Used Under Creative Commons License"
})
public class WelcomeController implements Initializable {

    @FXML
    private HBox topHBox;
    @FXML
    private ImageView ivHomer;
    @FXML
    private ImageView ivMarge;
    @FXML
    private ImageView ivBart;
    @FXML
    private ImageView ivLisa;
    @FXML
    private ImageView ivMaggie;
    @FXML
    private GridPane gridpane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label infoLabel;
    private final List<Node> imageNodes = new ArrayList<>();
    private DropShadow dropShadow = null;
    private static final Logger logger = Logger.getLogger(WelcomeController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final Reflection reflection = new Reflection();
        reflection.setFraction(1.0);

        Text banner = new Text(Bundle.CTL_BannerText());
        banner.setFill(Color.DARKMAGENTA);
        banner.setFont(Font.font("Tahome", 35));
        banner.setEffect(reflection);
        topHBox.getChildren().add(banner);

        dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);

        titleLabel.setText(Bundle.CTL_BottomText());
        DropShadow dropShadow2 = new DropShadow();
        dropShadow2.setOffsetX(3.0);
        dropShadow2.setOffsetY(3.0);
        dropShadow2.setRadius(5.0);
        dropShadow2.setColor(Color.color(0.4, 0.5, 0.5));
        titleLabel.setEffect(dropShadow2);

        infoLabel.setText(
                new StringBuilder(Bundle.CTL_License())
                .append("\n")
                .append("(http://creativecommons.org/licenses/by-sa/3.0/)")
                .toString());

        try {
            setImage(ivHomer, new URL("nbres:/com/asgteach/familytree/model/resources/HomerSimpson.png"));
            setImage(ivMarge, new URL("nbres:/com/asgteach/familytree/model/resources/MargeSimpson.png"));
            setImage(ivBart, new URL("nbres:/com/asgteach/familytree/model/resources/BartSimpson.png"));
            setImage(ivLisa, new URL("nbres:/com/asgteach/familytree/model/resources/LisaSimpson.png"));
            setImage(ivMaggie, new URL("nbres:/com/asgteach/familytree/model/resources/MaggieSimpson.png"));
        } catch (MalformedURLException ex) {
            logger.log(Level.WARNING, null, ex);
        }

        // Fade out all pics with a parallel transition
        // Build a fadetransition for each ImageView
        ParallelTransition pt = new ParallelTransition();
        for (Node node : imageNodes) {
            FadeTransition ft = new FadeTransition(Duration.millis(750), node);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            pt.getChildren().add(ft);
        }
        // Create a sequential transition for the parallel fadeouts
        // and the sequential fadeins
        final SequentialTransition seqtran = new SequentialTransition();
        seqtran.getChildren().add(pt);
        seqtran.getChildren().add(new PauseTransition(Duration.millis(500)));
        for (Node node : imageNodes) {
            FadeTransition ft = new FadeTransition(Duration.millis(600), node);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            seqtran.getChildren().add(ft);
        }

        gridpane.setOnMouseClicked((MouseEvent t) -> {
            seqtran.playFromStart();
        });

    }

    void setImage(ImageView iv, URL url) {
        Image image;
        final ScaleTransition st = new ScaleTransition(Duration.millis(1000), iv);
        try {
            image = new Image(url.openStream());
            iv.setFitWidth(60);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            iv.setImage(image);
            iv.setEffect(dropShadow);
            iv.setOnMouseEntered((MouseEvent t) -> {
                st.stop();
                st.setToX(2.0);
                st.setToY(2.0);
                st.play();
            });
            iv.setOnMouseExited((MouseEvent t) -> {
                st.stop();
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            });

            imageNodes.add(iv);
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }
}
