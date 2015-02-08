/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.bubble;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javax.swing.event.TableModelEvent;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.smartphone.tabledata.api.MyTableDataModel;

/**
 *
 * @author gail
 */
public class BubbleChartController implements Initializable {
    /*
     Note: BubbleChart does not use a Category Axis. 
     Both X and Y axes should be of type NumberAxis.
     */

    @FXML
    private StackPane stackpane;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BubbleChart<Number, Number> chart;
    @FXML
    private Label label;
    private static final int XFACTOR = 30;
    private static ObservableList<BubbleChart.Series<Number, Number>> bcData;
    private static MyTableDataModel tableModel;
    private static ParallelTransition pt = null;
    private static Node lastNode = null;
    private static final Logger logger = Logger.getLogger(BubbleChartController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createBubbleChart();
    }

    private void createBubbleChart() {
        tableModel = Lookup.getDefault().lookup(MyTableDataModel.class);
        if (tableModel == null) {
            logger.log(Level.SEVERE, "Cannot get TableModel object");
            LifecycleManager.getDefault().exit();
        }

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound((Integer.valueOf(
                tableModel.getColumnName(0)) - 1) * XFACTOR);
        xAxis.setUpperBound(((Integer.valueOf(tableModel.getColumnName(
                tableModel.getColumnCount() - 1)) + 1) * XFACTOR) + 1);
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number t) {
                return String.valueOf(t.intValue() / XFACTOR);
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string) * XFACTOR;
            }
        });
        xAxis.setTickUnit(XFACTOR);
        xAxis.setMinorTickCount(0);
        xAxis.setLabel(tableModel.getNameDescription());

        yAxis.setTickUnit(tableModel.getTickUnit());
        yAxis.setLabel(tableModel.getDataDescription());
        chart.setTitle(tableModel.getTitle());
        chart.setData(getBubbleChartData());

        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setMargin(label, new Insets(40, 8, 8, 8));

        setEffects();

        // set up the event handler to respond to changes in the table data
        // This is a swing event, so we must use Platform.runLater()
        // to update JavaFX components
        tableModel.addTableModelListener((TableModelEvent e) -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                final int row = e.getFirstRow();
                final int column = e.getColumn();
                final Number value = (Number) ((MyTableDataModel) e.getSource()).getValueAt(row, column);
                // Get new total for column
                double total = 0;
                for (int r = 0; r < tableModel.getCategoryCount(); r++) {
                    total += (double) tableModel.getValueAt(r, column);
                }
                final double newTotal = total;

                Platform.runLater(() -> {
                    XYChart.Series<Number, Number> s = chart.getData().get(row);
                    BubbleChart.Data<Number, Number> data = s.getData().get(column);
                    data.setYValue(value);
                    // update extra value for the entire column
                    for (int r = 0; r < tableModel.getCategoryCount(); r++) {
                        XYChart.Series<Number, Number> ss = chart.getData().get(r);
                        BubbleChart.Data<Number, Number> data1 = ss.getData().get(column);
                        data1.setExtraValue(((double) tableModel.getValueAt(r, column)
                                / newTotal) * XFACTOR);
                    }
                });
            }
        });
    }

    private ObservableList<BubbleChart.Series<Number, Number>> getBubbleChartData() {
        if (bcData == null) {
            bcData = FXCollections.observableArrayList();
            // Find totals for each column
            double[] totals = new double[tableModel.getColumnCount()];
            for (int column = 0; column < tableModel.getColumnCount(); column++) {
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    totals[column] += (double) tableModel.getValueAt(row, column);
                }
            }
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(tableModel.getCategoryName(row));

                for (int column = 0; column < tableModel.getColumnCount(); column++) {
                    Integer year = Integer.valueOf(tableModel.getColumnName(column));
                    Number units = (Number) tableModel.getValueAt(row, column);
                    series.getData().add(new XYChart.Data<Number, Number>(
                            year * XFACTOR,
                            units,
                            (units.doubleValue() / totals[column]) * XFACTOR));
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

    private void setEffects() {
        final Duration TRAN_TIME = Duration.millis(3000);
        // add a MOUSE_CLICKED handler to the background chart
        // to make the Label fade out and remove effects from nodes
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            if (pt != null) {
                pt.stop();
            }
            if (lastNode != null) {
                ScaleTransition st = new ScaleTransition(
                        TRAN_TIME, lastNode);
                st.setToX(1.0f);
                st.playFromStart();
            }
            FadeTransition ft = new FadeTransition(
                    TRAN_TIME, label);
            ft.setToValue(0.0);
            ft.playFromStart();
            t.consume();
        });
        for (final XYChart.Series<Number, Number> series : chart.getData()) {
            for (final BubbleChart.Data<Number, Number> datum : series.getData()) {

                final StringBinding valueBinding = new StringBinding() {
                    {
                        super.bind(datum.YValueProperty());
                    }

                    @Override
                    protected String computeValue() {
                        return String.format("%.1f M Units", datum.getYValue());
                    }
                };

                // Get the node associated with the chart datum
                datum.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
                    if (pt != null) {
                        pt.stop();
                    }
                    if (lastNode != null) {
                        ScaleTransition st = new ScaleTransition(
                                TRAN_TIME, lastNode);
                        st.setToX(1.0f);
                        st.playFromStart();
                    }
                    lastNode = datum.getNode();
                    // ScaleTransition gives the illusion of a rotating bubble
                    ScaleTransition st = new ScaleTransition(
                            TRAN_TIME, datum.getNode());
                    st.setFromX(1.0f);
                    st.setToX(-1.0f);
                    st.setAutoReverse(true);
                    st.setCycleCount(Timeline.INDEFINITE);
                    label.textProperty().bind(valueBinding);

                    FadeTransition ft = new FadeTransition(
                            Duration.millis(4000), label);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    pt = new ParallelTransition(st, ft);
                    pt.playFromStart();
                    t.consume();
                });

            }
        }
    }
}
