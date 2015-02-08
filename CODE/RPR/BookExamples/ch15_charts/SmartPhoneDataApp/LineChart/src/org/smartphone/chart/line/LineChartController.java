/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.line;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javax.swing.event.TableModelEvent;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.smartphone.tabledata.api.MyTableDataModel;

/**
 *
 * @author gail
 */
public class LineChartController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> chart;
    private ObservableList<XYChart.Series<String, Number>> lcData;
    private MyTableDataModel tableModel;
    private static final Logger logger = Logger.getLogger(LineChartController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createLineChart();
    }

    private void createLineChart() {
        tableModel = Lookup.getDefault().lookup(MyTableDataModel.class);
        if (tableModel == null) {
            logger.log(Level.SEVERE, "Cannot get TableModel object");
            LifecycleManager.getDefault().exit();
        }
        xAxis.setCategories(FXCollections.observableArrayList(
                tableModel.getColumnNames()));
        xAxis.setLabel(tableModel.getNameDescription());
        yAxis.setTickUnit(tableModel.getTickUnit());
        yAxis.setLabel(tableModel.getDataDescription());
        logger.log(Level.INFO, "yAxis label = {0}", tableModel.getDataDescription());
        logger.log(Level.INFO, "xAxis label = {0}", tableModel.getNameDescription());
        chart.setTitle(tableModel.getTitle());
        chart.setData(getLineChartData());

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
                    LineChart.Data<String, Number> data
                            = s.getData().get(column);
                    data.setYValue(value);
                });
            }
        });
    }
    
    private void eventStuff() {
//        XYChart.Data<String, Number> data = chart.getData().get(0).getData().get(0);
        XYChart.Series series = chart.getData().get(0); // get first row
        series.getNode().setEffect(new DropShadow());
        XYChart.Data<String, Number> data = chart.getData().get(0).getData().get(0);
        data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {});
    }

    private ObservableList<XYChart.Series<String, Number>> getLineChartData() {
        if (lcData == null) {
            lcData = FXCollections.observableArrayList();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(tableModel.getCategoryName(row));
                for (int column = 0;
                        column < tableModel.getColumnCount(); column++) {
                    series.getData().add(new XYChart.Data<>(
                            tableModel.getColumnName(column),
                            (Number) tableModel.getValueAt(row, column)));
                }
                lcData.add(series);
            }
        }
        return lcData;
    }

    public BufferedImage getImage() {
        // must be in JavaFX Application Thread
        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.ALICEBLUE);
        Image jfximage = stackpane.snapshot(params, null);
        return SwingFXUtils.fromFXImage(jfximage, null);
    }
}
