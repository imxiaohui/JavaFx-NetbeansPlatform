/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.coretableview;

import com.asgteach.phonedata.share.PhoneDataShare;
import com.asgteach.phonedata.entities.Salesdata;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;

/**
 *
 * @author gail
 */
public class TableViewController implements Initializable {

    @FXML
    private TableView<Salesdata> tableview;
    // Columns
    @FXML
    private TableColumn<Salesdata, String> colCompany;
    @FXML
    private TableColumn<Salesdata, String> colYear;
    @FXML
    private TableColumn<Salesdata, BigDecimal> colUnitsSold;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Label displayMessage;
    @FXML
    private TextField filterText;

    private static final Logger logger
            = Logger.getLogger(TableViewController.class.getName());
    private PhoneDataShare share = null;
    FilteredList<Salesdata> filteredData = null;
    SortedList<Salesdata> sortedData = null;
    Comparator<? super Salesdata> comparatorSalesdata = null;

    @FXML
    protected void refreshData(ActionEvent event) {
        logger.log(Level.INFO, "Refresh Data:");
        displayMessage.setText("");
        displayMessage.setStyle("-fx-text-fill: black;");
        share.refreshData(progress);
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resources) {
        share = Lookup.getDefault().lookup(PhoneDataShare.class);
        if (share == null) {
            logger.log(Level.SEVERE, "Cannot get PhoneDataShare object");
            LifecycleManager.getDefault().exit();
        }
//        colCompany.setText(Bundle.company());
//        colYear.setText(Bundle.year());
//        colUnitsSold.setText(Bundle.unitsSold());
        // Configure the Columns
        colCompany.setCellValueFactory((TableColumn.CellDataFeatures<Salesdata, String> p) -> {
            return p.getValue().getCompanyid().companynameProperty();
        });

        colYear.setCellValueFactory(new PropertyValueFactory<Salesdata, String>("salesyear"));

        colUnitsSold.setCellValueFactory(new PropertyValueFactory<Salesdata, BigDecimal>("unitsinmillions"));
        colUnitsSold.setCellFactory(TextFieldTableCell.
                <Salesdata, BigDecimal>forTableColumn(
                        new DecimalConverter()));

        // Configure editing for the UnitsSold column
        colUnitsSold.setOnEditCommit(
                (TableColumn.CellEditEvent<Salesdata, BigDecimal> t) -> {
                    // If the new value is null, use the old value
                    BigDecimal bd;
                    if (t.getNewValue() != null) {
                        bd = t.getNewValue();
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setUnitsinmillions(bd);
                        displayMessage.setText("");
                        displayMessage.setStyle("-fx-text-fill: black;");
                        share.updateSales(t.getTableView().getItems().get(t
                                        .getTablePosition().getRow()), progress);
                    } else {
                        // bad value, use the old one and refresh the table
                        bd = t.getOldValue();
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setUnitsinmillions(bd);
                        // force a refresh
                        t.getTableColumn().setVisible(false);
                        t.getTableColumn().setVisible(true);
                        displayMessage.setStyle("-fx-text-fill: red;");
                        displayMessage.setText(Bundle.salesAmountBadFormat());
                    }
                });

        // Define the Comparator for Sorting
        comparatorSalesdata = (Salesdata o1, Salesdata o2) -> {
            // First compare the company name, then compare the year
            int result = o1.getCompanyid().getCompanyname().compareToIgnoreCase(
                    o2.getCompanyid().getCompanyname());
            if (result == 0) {
                return o1.getSalesyear().compareTo(o2.getSalesyear());
            }
            return result;
        };

        // When the underlying data change, reset the filtering and sorting
        share.theDataProperty().addListener((ListChangeListener.Change<? extends Salesdata> change) -> {
            logger.log(Level.INFO, "master list changed");
            filteredData = new FilteredList<>(share.theDataProperty().get(), salesdata -> {
                // Reapply filter when data changes
                String newValue = filterText.getText();
                // If filter text is empty, display all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare company name and year with filter text
                String lowerCaseFilter = newValue.toLowerCase();

                if (salesdata.getCompanyid().getCompanyname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches company
                } else if (salesdata.getSalesyear().contains(lowerCaseFilter)) {
                    return true; // Filter matches sales year
                }
                return false; // No match            
            });
            sortedData = new SortedList<>(filteredData);
            sortedData.setComparator(comparatorSalesdata);
            tableview.setItems(sortedData);
        });

        // Reapply the filter when the user supplies a new filter text
        filterText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(salesdata -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare company name and year with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (salesdata.getCompanyid().getCompanyname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches company
                } else if (salesdata.getSalesyear().contains(lowerCaseFilter)) {
                    return true; // Filter matches sales year
                }
                return false; // No match            
            });
        });
    }

    private class DecimalConverter extends StringConverter<BigDecimal> {

        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());

        public DecimalConverter() {
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(1);
        }

        @Override
        public String toString(BigDecimal t) {
            try {
                return nf.format(t);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public BigDecimal fromString(String string) {
            try {
                Number newValue = nf.parse(string);
                BigDecimal result = new BigDecimal(newValue.doubleValue());
                return result.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            } catch (ParseException e) {
                return null;
            }
        }
    }
}
