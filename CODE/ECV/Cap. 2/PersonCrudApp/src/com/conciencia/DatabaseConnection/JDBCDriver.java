package com.conciencia.DatabaseConnection;

/**
 * Enum that, given a String with the RDBMS name, it returns 
 * the driver in String format.
 * @author Ernesto Cantú Valle 
 * mail : ernesto.cantu1989@live.com
 * 
 * 03/27/2015
 */
public enum JDBCDriver {
    /*
     * Enum values
     */
    MYSQL("com.mysql.jdbc.Driver","mysql"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver","mssql");
    
    
    
    /** String that contains the driver forName. */
    String JDBC_DRIVER;
    
    /** RDBMS =  Relational DataBase Manager System*/
    String RDBMS;
   
    
    /**
     * Private constructor that asings the value of the sql driver to JDBC_DRIVER,
     * given the JDBCDriver.
     * @param JDBC_DRIVER  The String of the JDBC Driver
     * @param RDBMS the used RDBMS
     */
    private JDBCDriver(String JDBC_DRIVER, String RDBMS) {
       this.JDBC_DRIVER = JDBC_DRIVER;
       this.RDBMS = RDBMS;
    }
    
    /**
     * Gets the JDBC Driver.
     * @return the JDBC Driver in String
     */
    public String getJDBCDriver(){
       return JDBC_DRIVER;
    }

    /**
     * Gets the RDBSM used.
     * @return the used RDBMS
     * 
     * MYSQL : 'mysql'
     * SQLSERVER : 'mssql'
     * 
     */
    public String getRDBMS() {
        return RDBMS;
    }    
}
