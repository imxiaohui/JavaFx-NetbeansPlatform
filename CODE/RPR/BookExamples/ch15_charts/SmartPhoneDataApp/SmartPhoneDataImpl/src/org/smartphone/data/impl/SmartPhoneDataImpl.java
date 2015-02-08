/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smartphone.data.impl;

import java.util.Arrays;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;
import org.smartphone.tabledata.api.MyTableDataModel;

/**
 *
 * @author gail
 */
@ServiceProvider(service = MyTableDataModel.class)
public class SmartPhoneDataImpl extends MyTableDataModel {

    private final String[] names = {"2008", "2009", "2010", "2011"};
    private final String[] categories = {"Nokia", "RIM", "Apple", "HTC", "Samsung", "Others"};
    private final String dataDescription = "Units Sold in Millions";
    private final String nameDescription = "Year";
    private final String title = "Smart Phone Sales";
    private final Object[][] data = {
        // Sources: 
        // 2008: Gartner (March 2009)
        // 2009: Tomi Ahonen ("Final 2009 mobile phone market numbers," April 2010)
        // 2010, 2011: IDC (February 2012) 
            // Nokia
            {61.0, 68.0, 100.1, 77.3},
            
            // ResearchInMotion
            {23.1, 37.0, 48.8, 51.1},
            
            // Apple
            {11.4, 25.0, 47.5, 93.2},
            
            // HTC
            {5.9, 9.0, 21.7, 43.5},
            
            // Samsung
            {4.0, 7.0, 22.9, 94.0},
            
            // Others
            {33.9, 30.0, 63.7, 132.3}
        };
    
    private final Object[][] orig = clone(data);
    
    // provide deep copy semantics
    private static Object[][] clone(Object[][] g) {
        Object[][] newArray = new Object[g.length][];
        for (int i = 0; i < g.length; i++) {
            newArray[i] = new Object[g[i].length];
            System.arraycopy(g[i], 0, newArray[i], 0, g[i].length);
        }
        return newArray;
    }
    
    @Override
    public final Object[][] getOriginalData() {
        return orig;
    }

    @Override
    public double getTickUnit() {
        return 1000;
    }

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList(names);
    }
    
    // Add support for Category Names
    @Override
    public List<String> getCategoryNames() {
        return Arrays.asList(categories);
    }
    @Override
    public int getCategoryCount() {
        return getRowCount();
    }
    @Override
    public String getCategoryName(int row) {
        return categories[row];
    }

    @Override
    public String getDataDescription() {
        return dataDescription;
    }

    @Override
    public String getNameDescription() {
        return nameDescription;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
    

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (value instanceof Double) {
            data[row][column] = (Double) value;
        }
        fireTableCellUpdated(row, column);
    }
    
}
