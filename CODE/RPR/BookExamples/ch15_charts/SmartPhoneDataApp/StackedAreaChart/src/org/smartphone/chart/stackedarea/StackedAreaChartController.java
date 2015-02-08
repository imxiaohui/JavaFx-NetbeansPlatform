/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.stackedarea;

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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javax.swing.event.TableModelEvent;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.smartphone.tabledata.api.MyTableDataModel;

/**
 *
 * @author gail
 */
public class StackedAreaChartController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private StackedAreaChart<String, Number> chart;
    private ObservableList<StackedAreaChart.Series<String, Number>> saData;
    private MyTableDataModel tableModel;
    private static final Logger logger = Logger.getLogger(StackedAreaChartController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createStackedAreaChart();
    }

    private void createStackedAreaChart() {
        tableModel = Lookup.getDefault().lookup(MyTableDataModel.class);
        if (tableModel == null) {
            logger.log(Level.SEVERE, "Cannot get TableModel object");
            LifecycleManager.getDefault().exit();
        }

        xAxis.setCategories(FXCollections.observableArrayList(
                tableModel.getColumnNames()));
        xAxis.setLabel(tableModel.getNameDescription());
        xAxis.setLabel(tableModel.getNameDescription());

        double tickUnit = tableModel.getTickUnit();
        yAxis.setTickUnit(tickUnit);
        yAxis.setLabel(tableModel.getDataDescription());
        chart.setData(getStackedAreaChartData());

        // set up the event handler to respond to changes in the table data
        // This is a swing event, so we must use Platform.runLater()
        // to update JavaFX components
        tableModel.addTableModelListener((TableModelEvent e) -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                final int row = e.getFirstRow();
                final int column = e.getColumn();
                final Number value = (Number) ((MyTableDataModel) e.getSource()).getValueAt(row, column);

                Platform.runLater(() -> {
                    XYChart.Series<String, Number> s = chart.getData().get(row);
                    StackedAreaChart.Data<String, Number> data = s.getData().get(column);
                    data.setYValue(value);
                });
            }
        });
    }

    private ObservableList<StackedAreaChart.Series<String, Number>> getStackedAreaChartData() {
        if (saData == null) {
            saData = FXCollections.observableArrayList();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(tableModel.getCategoryName(row));
                for (int column = 0;
                        column < tableModel.getColumnCount(); column++) {
                    series.getData().add(new StackedAreaChart.Data<>(
                            tableModel.getColumnName(column),
                            (Number) tableModel.getValueAt(row, column)));
                }
                saData.add(series);
            }
        }
        return saData;
    }

    public BufferedImage getImage() {
        // must be in JavaFX Application Thread
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.ALICEBLUE);
        Image jfximage = stackpane.snapshot(params, null);
        return SwingFXUtils.fromFXImage(jfximage, null);
    }

}
