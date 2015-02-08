/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.linechart;

import com.asgteach.phonedata.entities.Company;
import com.asgteach.phonedata.entities.Salesdata;
import com.asgteach.phonedata.share.PhoneDataShare;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

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
    private final ObservableList<XYChart.Series<String, Number>> lcData
            = FXCollections.observableArrayList();
    private static final Logger logger
            = Logger.getLogger(LineChartController.class.getName());
    private PhoneDataShare data = null;
    SortedList<Salesdata> sortedData = null;
    Comparator<? super Salesdata> comparatorSalesdata = null;

    @Messages ({
        "BadPhoneDataShare=Cannot get PhoneDataShare object"
    })
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        data = Lookup.getDefault().lookup(PhoneDataShare.class);
        if (data == null) {
            logger.log(Level.SEVERE, Bundle.BadPhoneDataShare());
            LifecycleManager.getDefault().exit();
        }
        xAxis.setCategories(data.categoryListProperty());
        logger.log(Level.INFO, "category list = {0}", xAxis.getCategories());
//        xAxis.setLabel(data.getNameDescription());
        xAxis.setLabel(Bundle.year());
        yAxis.setTickUnit(data.getTickUnit());
//        yAxis.setLabel(data.getDataDescription());
        yAxis.setLabel(Bundle.unitsSoldDescription());
        logger.log(Level.INFO, "yAxis label = {0}", data.getDataDescription());
        logger.log(Level.INFO, "xAxis label = {0}", data.getNameDescription());
        comparatorSalesdata = (Salesdata o1, Salesdata o2) -> {
            // First compare the company name, then compare the year
            int result = o1.getCompanyid().getCompanyname().compareToIgnoreCase(
                    o2.getCompanyid().getCompanyname());
            if (result == 0) {
                return o1.getSalesyear().compareTo(o2.getSalesyear());
            }
            return result;
        };
//        chart.setTitle(data.getTitle());
        chart.setTitle(Bundle.smartphonetitle());
        chart.setData(lcData);

        data.theDataProperty().addListener(
                (ListChangeListener.Change<? extends Salesdata> c) -> {
                    logger.log(Level.INFO, "the data changed");
                    while (c.next()) {                        
                        if (c.wasAdded()) {
                            logger.log(Level.INFO, "was added");
                            getLineChartData();
                        }
                    }
                });
        data.companyNamesProperty().addListener(
                (ListChangeListener.Change<? extends Company> change) -> {
                    logger.log(Level.INFO, "the company names changed");
                    getLineChartData();
                });
    }

    private void getLineChartData() {
        logger.log(Level.INFO, "calling getLineChartData()");
        // create all the series if necessary
        if (data.companyNamesProperty().getSize() != lcData.size()) {
            logger.log(Level.FINE, "Clearing the Series (number = {0}) ", lcData.size());
            for (int i = 0; i < lcData.size(); i++) {
                lcData.clear();
            }
            for (int row = 0; row < data.companyNamesProperty().getSize(); row++) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(data.companyNamesProperty().get(row).getCompanyname());
                lcData.add(series);
            }
            logger.log(Level.FINE, "Created all Series (number = {0}) ", lcData.size());
        }

        // Sort the data so that the Chart looks nice
        sortedData = new SortedList<>(data.theDataProperty().get());
        sortedData.setComparator(comparatorSalesdata);

        // install each datum in the correct series if necessary
        for (Salesdata sales : sortedData) {
            boolean processed = false;
            for (XYChart.Series<String, Number> series : lcData) {
                if (sales.getCompanyid().getCompanyname().equals(series.getName())) {
                    logger.log(Level.FINE, "Looking at series ({0}) ", series.getName());
                    // correct series

                    for (XYChart.Data<String, Number> currentDatum : series.getData()) {
                        // correct year
                        if (currentDatum.getXValue().equals(sales.getSalesyear())) {
                            processed = true;
                            if (!currentDatum.getYValue().equals(sales.getUnitsinmillions())) {
                                // replace                               
                                logger.log(Level.INFO, "Replacing datum to series ({0}) ", series.getName() + " old: " + currentDatum.getYValue()
                                        + " with new " + sales.getUnitsinmillions());
                                currentDatum.setYValue(sales.getUnitsinmillions());
                            } else {
                                // do nothing
                                logger.log(Level.FINE, "Datum unchanged in series ({0}) ", series.getName() + " for " + currentDatum.getYValue()
                                        + " - " + currentDatum.getXValue());
                            }
                            break;
                        }

                    }
                    // need new data
                    if (!processed) {
                        XYChart.Data<String, Number> datum = new XYChart.Data<>(sales.getSalesyear(),
                                sales.getUnitsinmillions());
                        series.getData().add(datum);
                        logger.log(Level.FINE, "Adding new datum to series ({0}) ", series.getName() + " for " + datum.getYValue()
                                + " - " + datum.getXValue());
                    }
                    break;
                }
            }
        }
        logger.log(Level.INFO, "getLineChartData: category list = {0}", xAxis.getCategories());
    }

    public BufferedImage getImage() {
        // must be in JavaFX Application Thread
        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.ALICEBLUE);
        Image jfximage = stackpane.snapshot(params, null);
        return SwingFXUtils.fromFXImage(jfximage, null);
    }
}
