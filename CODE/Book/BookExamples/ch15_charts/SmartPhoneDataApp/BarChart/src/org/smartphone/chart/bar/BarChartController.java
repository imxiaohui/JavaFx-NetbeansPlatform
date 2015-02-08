/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.bar;

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
public class BarChartController implements Initializable {

    @FXML
    private Label numberLabel;
    @FXML
    private StackPane stackpane;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<String, Number> chart;
    private ObservableList<BarChart.Series<String, Number>> bcData;
    private MyTableDataModel tableModel;
    private Node lastNode = null;
    private static final double CHART_X = 67.0;
    private static final double CHART_Y = 23.0;
    private static final int MIN_NODE_SIZE = 75;
    private static final Logger logger = Logger.getLogger(BarChartController.class.getName());

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
        xAxis.setCategories(FXCollections.observableArrayList(
                tableModel.getColumnNames()));
        xAxis.setLabel(tableModel.getNameDescription());
        double tickUnit = tableModel.getTickUnit();

        yAxis.setTickUnit(tickUnit);
        yAxis.setLabel(tableModel.getDataDescription());
        chart.setTitle(tableModel.getTitle());
        chart.setData(getBarChartData());

        // only let the numberLabel be visible if 
        // the pane is a certain minimum size
        // Wonderful example of Fluent API!
        numberLabel.visibleProperty().bind(
                stackpane.heightProperty().greaterThan(MIN_NODE_SIZE * 3).
                and(stackpane.widthProperty().greaterThan(MIN_NODE_SIZE * 7)));
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
                    XYChart.Series<String, Number> s
                            = chart.getData().get(row);
                    BarChart.Data<String, Number> data
                            = s.getData().get(column);
                    data.setYValue(value);
                });
            }
        });
    }

    private ObservableList<BarChart.Series<String, Number>> getBarChartData() {
        if (bcData == null) {
            bcData = FXCollections.observableArrayList();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(tableModel.getCategoryName(row));
                for (int column = 0;
                        column < tableModel.getColumnCount(); column++) {
                    series.getData().add(new BarChart.Data<>(
                            tableModel.getColumnName(column),
                            (Number) tableModel.getValueAt(row, column)));
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
            FadeTransition ft = new FadeTransition(
                    TRAN_TIME, numberLabel);
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
        for (final XYChart.Series<String, Number> series : chart.getData()) {
            final int row = rowcount++;
            for (final BarChart.Data<String, Number> datum : series.getData()) {

                // Setup custom binding objects
                // Sets the color value based on the size & color of the bar
                // The darker colors correspond to rows 3-6
                final ObjectBinding<Paint> colorBinding
                        = new ObjectBinding<Paint>() {
                            {
                                super.bind(datum.getNode().boundsInParentProperty());
                            }

                            @Override
                            protected Paint computeValue() {
                                Bounds b = datum.getNode().getBoundsInParent();
                                if (b.getHeight() >= MIN_NODE_SIZE) {
                                    return Color.WHITESMOKE;
                                } else {
                                    return Color.DARKSLATEGRAY;
                                }
                            }
                        };

                // The number display is getYValue(). We can't use
                // fluent API because YValueProperty is ObjectProperty<Number>,
                // not NumberProperty
                final StringBinding barNumber = new StringBinding() {
                    {
                        super.bind(datum.YValueProperty());
                    }

                    @Override
                    protected String computeValue() {
                        return String.format("%.1f", datum.getYValue());
                    }
                };

                // Here we compute the translateX and translateY values of
                // where the displayed label should go.
                // These depend on the numberLabel height & width, 
                // and the bar's dimensions
                // Note that numberLabel is rotated, so we use numberLabel's
                // boundsInLocal which accounts for rotation's affect for labelX
                // computation
                final DoubleBinding labelY = new DoubleBinding() {
                    {
                        // need to use label's width here since it's rotated
                        super.bind(datum.getNode().boundsInParentProperty(),
                                numberLabel.widthProperty());
                    }

                    @Override
                    protected double computeValue() {
                        Bounds b = datum.getNode().getBoundsInParent();
                        if (b.getHeight() < MIN_NODE_SIZE) {
                            return b.getMinY() + 5;
                        } else {
                            return b.getMinY() + numberLabel.getWidth() + CHART_Y;
                        }
                    }
                };

                final DoubleBinding labelX = new DoubleBinding() {
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
                        return b.getMinX() + b.getWidth() / 2
                                - b2.getWidth() / 2 + CHART_X;
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
