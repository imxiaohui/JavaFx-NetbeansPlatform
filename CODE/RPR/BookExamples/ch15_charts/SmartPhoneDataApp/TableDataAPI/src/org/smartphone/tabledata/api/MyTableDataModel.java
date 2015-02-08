/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smartphone.tabledata.api;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gail
 */
public abstract class MyTableDataModel extends AbstractTableModel {
    
    public abstract Object[][] getOriginalData();
    public abstract double getTickUnit();
    public abstract List<String> getColumnNames();
    
    // Add support for Category Names
    public abstract List<String> getCategoryNames();   
    public abstract int getCategoryCount();
    public abstract String getCategoryName(int row);
    public abstract String getDataDescription();
    public abstract String getNameDescription();
    public abstract String getTitle();
       
}
