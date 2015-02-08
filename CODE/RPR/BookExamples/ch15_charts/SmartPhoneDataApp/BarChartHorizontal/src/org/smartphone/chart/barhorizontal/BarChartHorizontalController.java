/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.barhorizontal;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javax.swing.event.TableModelEvent;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.smartphone.tabledata.api.MyTableDataModel;

/**
 *
 * @author gail
 */
public class BarChartHorizontalController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private CategoryAxis catAxis;
    @FXML
    private NumberAxis numAxis;
    @FXML
    private BarChart<Number, String> chart;
    @FXML
    private Label numberLabel;
    private ObservableList<XYChart.Series<Number, String>> bcData;
    private MyTableDataModel tableModel;

    private Node lastNode = null;
    private static final double CHART_X = 45.0;
    private static final double CHART_Y = 37.0;
    private static final int MIN_NODE_SIZE = 75;
    private static final Logger logger = Logger.getLogger(BarChartHorizontalController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createBarChart();
    }

    private void createBarChart() {
        tableModel = Lookup.getDefault().lookup(MyTableDataModel.class);
        if (tableModel == null) {
            logger.log(Level.SEVERE, "Cannot get TableModel object");
            LifecycleManager.getDefault().exit();
        }
        StackPane.setAlignment(numberLabel, Pos.TOP_LEFT);
        catAxis.setCategories(FXCollections.observableArrayList(tableModel.getColumnNames()));
        catAxis.setLabel(tableModel.getNameDescription());

        double tickUnit = tableModel.getTickUnit();

        numAxis.setTickUnit(tickUnit);
        numAxis.setLabel(tableModel.getDataDescription());
        numAxis.setTickLabelRotation(90);

        chart.setTitle(tableModel.getTitle());
        chart.setData(getBarChartData());

        // only let the numberLabel be visible if 
        // the pane is a certain minimum size
        // Wonderful example of Fluent API!
        numberLabel.visibleProperty().bind(
                stackpane.heightProperty().greaterThan(MIN_NODE_SIZE * 7).
                and(stackpane.widthProperty().greaterThan(MIN_NODE_SIZE * 3)));
        setEffectsHandlers();

        // set up the event handler to respond to changes in the table data
        // This is a swing event, so we must use Platform.runLater()
        // to update JavaFX components
        tableModel.addTableModelListener((TableModelEvent e) -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                final int row = e.getFirstRow();
                final int column = e.getColumn();
                final Number value = (Number) ((MyTableDataModel) e.getSource()).getValueAt(row, column);
                Platform.runLater(() -> {
                    XYChart.Series<Number, String> s
                            = chart.getData().get(row);
                    BarChart.Data<Number, String> data
                            = s.getData().get(column);
                    data.setXValue(value);
                });
            }
        });
    }

    private ObservableList<XYChart.Series<Number, String>> getBarChartData() {
        if (bcData == null) {
            bcData = FXCollections.observableArrayList();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                XYChart.Series<Number, String> series = new XYChart.Series<>();
                series.setName(tableModel.getCategoryName(row));
                for (int column = 0;
                        column < tableModel.getColumnCount(); column++) {
                    series.getData().add(new BarChart.Data<>(
                            (Number) tableModel.getValueAt(row, column),
                            tableModel.getColumnName(column)));
                }
                bcData.add(series);
            }
        }
        return bcData;
    }

    public BufferedImage getImage() {
        // must be in JavaFX Application Thread
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.ALICEBLUE);
        Image jfximage = stackpane.snapshot(params, null);
        return SwingFXUtils.fromFXImage(jfximage, null);
    }

    private void setEffectsHandlers() {
        final Duration TRAN_TIME = Duration.millis(1500);
        final DropShadow dropShadow = new DropShadow();

        // add a MOUSE_CLICKED handler to the background chart
        // to turn off any dropshadow effects
        // and make the numberLabel disappear
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            FadeTransition ft = new FadeTransition(TRAN_TIME, numberLabel);
            ft.setInterpolator(Interpolator.EASE_OUT);
            ft.setToValue(0.0);
            ft.playFromStart();
            // we remove any effects after the label
            // fades out so that the label doesn't move first
            ft.setOnFinished((ActionEvent arg0) -> {
                if (lastNode != null) {
                    lastNode.setEffect(null);
                }
            });
            t.consume();
        });

        int rowcount = 0;
        for (final XYChart.Series<Number, String> series : chart.getData()) {
            final int row = rowcount++;
            for (final BarChart.Data<Number, String> datum : series.getData()) {

                // Setup custom binding objects
                // Sets the color value based on the size & color of the bar
                // The darker colors correspond to rows 3-6
                final ObjectBinding<Paint> colorBinding = new ObjectBinding<Paint>() {
                    {
                        super.bind(datum.getNode().boundsInParentProperty());
                    }

                    @Override
                    protected Paint computeValue() {
                        Bounds b = datum.getNode().getBoundsInParent();
                        if (b.getWidth() >= MIN_NODE_SIZE) {
                            return Color.WHITESMOKE;
                        } else {
                            return Color.DARKSLATEGRAY;
                        }
                    }
                };

                // The number display is getYValue(). We can't use
                // fluent API because XValueProperty is ObjectProperty<Number>,
                // not NumberProperty
                final StringBinding barNumber = new StringBinding() {
                    {
                        super.bind(datum.XValueProperty());
                    }

                    @Override
                    protected String computeValue() {
                        return String.format("%.1f", datum.getXValue());
                    }
                };

                // Here we compute the translateX and translateY values of
                // where the displayed label should go.
                // These depend on the numberLabel height & width, 
                // and the bar's dimensions
                final DoubleBinding labelX = new DoubleBinding() {
                    {
                        // need to use label's width here since it's rotated
                        super.bind(datum.getNode().boundsInParentProperty(),
                                numberLabel.widthProperty());
                    }

                    @Override
                    protected double computeValue() {
                        Bounds b = datum.getNode().getBoundsInParent();
                        if (b.getWidth() < MIN_NODE_SIZE) {
                            return b.getMaxX() + CHART_X + 30;
                        } else {
                            return b.getMaxX() - numberLabel.getWidth() + CHART_X;
                        }
                    }
                };

                final DoubleBinding labelY = new DoubleBinding() {
                    {
                        // using the label's boundsInLocal takes into
                        // account that it's rotated
                        super.bind(datum.getNode().boundsInParentProperty(),
                                numberLabel.boundsInLocalProperty());
                    }

                    @Override
                    protected double computeValue() {
                        Bounds b = datum.getNode().getBoundsInParent();
                        Bounds b2 = numberLabel.getBoundsInLocal();
                        return b.getMinY() + b.getHeight() / 2
                                - b2.getHeight() / 2 + CHART_Y;
                    }
                };

                // Apply the bindings in the MOUSE_CLICKED event handler
                datum.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
                    if (lastNode != null) {
                        lastNode.setEffect(null);
                    }
                    datum.getNode().setEffect(dropShadow);
                    lastNode = datum.getNode();
                    numberLabel.setOpacity(0);
                    numberLabel.textProperty().bind(barNumber);
                    numberLabel.textFillProperty().bind(
                            colorBinding);
                    numberLabel.translateYProperty().bind(labelY);
                    numberLabel.translateXProperty().bind(labelX);
                    FadeTransition ft = new FadeTransition(
                            TRAN_TIME, numberLabel);
                    ft.setInterpolator(Interpolator.EASE_IN);
                    ft.setToValue(1.0);
                    ft.playFromStart();
                    t.consume();
                });
            }
        }

    }
}
