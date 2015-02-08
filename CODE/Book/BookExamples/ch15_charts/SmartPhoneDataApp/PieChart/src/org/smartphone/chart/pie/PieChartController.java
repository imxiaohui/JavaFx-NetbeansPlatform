/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smartphone.chart.pie;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import org.openide.LifecycleManager;
import org.openide.util.Lookup;
import org.smartphone.tabledata.api.MyTableDataModel;

/**
 *
 * @author gail
 */
public class PieChartController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private StackPane stackpane;
    @FXML
    private PieChart chart;
    @FXML
    private ComboBox<String> yearChoice;
    @FXML
    private TextField textField;
    private MyTableDataModel tableModel;
    private ObservableList<PieChart.Data> pcData;
    private Node lastNode = null;
    private static final Logger logger = Logger.getLogger(PieChartController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPieChart();
    }

    private void createPieChart() {
        // set up the pie chart
        tableModel = Lookup.getDefault().lookup(MyTableDataModel.class);
        if (tableModel == null) {
            logger.log(Level.SEVERE, "Cannot get TableModel object");
            LifecycleManager.getDefault().exit();
        }

        // layout the stackpane
        StackPane.setAlignment(yearChoice, Pos.TOP_RIGHT);
        StackPane.setMargin(yearChoice, new Insets(30, 15, 8, 8));
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        StackPane.setMargin(label, new Insets(50, 8, 8, 15));
        StackPane.setAlignment(textField, Pos.BOTTOM_LEFT);

        // set up the combobox
        yearChoice.setItems(FXCollections.observableArrayList(
                tableModel.getColumnNames()));
        yearChoice.getSelectionModel().selectFirst();
        yearChoice.setTooltip(new Tooltip("Select the sales data year"));
        yearChoice.getSelectionModel().selectedIndexProperty().addListener(o -> {
            getPieChartData(yearChoice.getSelectionModel().getSelectedIndex());
            resetTextField();
        });

        // configure the pie chart
        chart.setData(getPieChartData(yearChoice.getSelectionModel().getSelectedIndex()));
        chart.titleProperty().bind(Bindings.concat(tableModel.getTitle() + ": ",
                Bindings.stringValueAt(yearChoice.itemsProperty().get(),
                        yearChoice.getSelectionModel().selectedIndexProperty())));

        setupEventHandlers();
        tableModel.addTableModelListener((TableModelEvent e) -> {
            // In Swing EDT thread, get TabelModelEvent info
            if (e.getType() == TableModelEvent.UPDATE) {
                final int row = e.getFirstRow();
                final int column = e.getColumn();
                if (column != yearChoice.getSelectionModel().getSelectedIndex()) {
                    return;
                }
                final Double value = (Double) ((MyTableDataModel) e.getSource()).getValueAt(row, column);

                Platform.runLater(() -> {
                    // Update JavaFX scene in JavaFX Application thread
                    PieChart.Data data = chart.getData().get(row);
                    data.setPieValue(value);
                    resetTextField();
                });
            }
        });
    }

    // Return the data corresponding to the column number (year)
    private ObservableList<PieChart.Data> getPieChartData(int column) {
        if (pcData == null) {
            pcData = FXCollections.observableArrayList();
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                pcData.add(new PieChart.Data(tableModel.getCategoryName(row),
                        (Double) tableModel.getValueAt(row, column)));
            }
        } else {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                PieChart.Data data = chart.getData().get(row);
                data.setPieValue((Double) tableModel.getValueAt(row, column));
            }
        }
        return pcData;
    }

    public BufferedImage getImage() {
        // must be in JavaFX Application Thread
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.ALICEBLUE);
        Image jfximage = stackpane.snapshot(params, null);
        return SwingFXUtils.fromFXImage(jfximage, null);
    }

    private void resetTextField() {
        FadeTransition ft = new FadeTransition(
                Duration.millis(1000), textField);
        ft.setToValue(0.0);
        ft.playFromStart();
        ft.setOnFinished((event) -> {
            textField.setTranslateX(0);
            textField.setTranslateY(0);
        });

    }

    private void setupEventHandlers() {
        final DropShadow dropShadow = new DropShadow();
        final Duration TRAN_TIME = Duration.millis(1500);
        // add a MOUSE_CLICKED handler to the background chart
        // to turn off any dropshadow effects
        // and make the Label disappear
        chart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
            resetTextField();
            FadeTransition ft = new FadeTransition(
                    TRAN_TIME, label);
            ft.setToValue(0.0);
            ft.playFromStart();
            if (lastNode != null) {
                lastNode.setEffect(null);
                lastNode.setTranslateX(0);
                lastNode.setTranslateY(0);
            }
            t.consume();
        });

        // set up bindings and event handlers for piechart nodes 
        int row = 0;
        for (final PieChart.Data data : chart.getData()) {
            final int currentRow = row;
            final StringBinding percentBinding = new StringBinding() {
                {
                    pcData.stream().forEach((data) -> {
                        super.bind(data.pieValueProperty());
                    });
                }

                @Override
                protected String computeValue() {
                    double total = 0;
                    for (final PieChart.Data thisdata : pcData) {
                        total += thisdata.getPieValue();
                    }
                    return String.format("%.1f%%", data.getPieValue()
                            / total * 100);
                }
            };
            
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
                if (lastNode != null) {
                    lastNode.setEffect(null);
                }
                lastNode = data.getNode();
                logger.log(Level.INFO, "Meta down = {0}", t.isMetaDown());
                if (t.isMetaDown()) {
                    // Move the textfield to where the mouse click is
                    textField.setTranslateX(t.getSceneX() - textField.getLayoutX());
                    textField.setTranslateY(t.getSceneY() - textField.getLayoutY());
                    textField.setText(data.getPieValue() + "");
                    textField.setOpacity(1.0);
                    textField.setOnAction((event) -> {
                        try {
                            System.out.println("You entered: " + textField.getText());
                            final Double num = Double.valueOf(textField.getText());
                            data.setPieValue(num);
                            resetTextField();
                            final int currentColumn = yearChoice.getSelectionModel().getSelectedIndex();
                            SwingUtilities.invokeLater(() -> {
                                tableModel.setValueAt(num, currentRow, currentColumn);
                            });
                        } catch (NumberFormatException e) {
                            // Just use the original number if the format is bad
                            textField.setText(data.getPieValue() + "");
                        }
                    });
                } else {
                    resetTextField();
                    Bounds b1 = data.getNode().getBoundsInLocal();
                    double newX = (b1.getWidth()) / 2 + b1.getMinX();
                    double newY = (b1.getHeight()) / 2 + b1.getMinY();
                    label.setOpacity(0);
                    label.textProperty().bind(percentBinding);
                    data.getNode().setEffect(dropShadow);
                    TranslateTransition tt = new TranslateTransition(
                            TRAN_TIME, data.getNode());
                    tt.setByX(newX);
                    tt.setByY(newY);
                    tt.setAutoReverse(true);
                    tt.setCycleCount(2);
                    tt.setInterpolator(Interpolator.EASE_BOTH);

                    FadeTransition ft = new FadeTransition(
                            TRAN_TIME, label);
                    ft.setToValue(1.0);
                    ParallelTransition pt = new ParallelTransition(
                            tt, ft);
                    pt.play();
                }
                t.consume();
            });
            row++;
        }
    }
}
