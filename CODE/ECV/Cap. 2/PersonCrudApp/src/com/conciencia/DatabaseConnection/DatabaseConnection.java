package com.conciencia.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC Connetcion Manager to comunicate to specified DataBase.
 * Helps in the management of JDBC connections. 
 * Just tell the Database used, and get the connection.
 * 
 * This class can't be instantiated.
 * 
 * Use the method setInfo to set the user and password. This method returns
 * a Properties object.
 * After this, set JDBC_DRIVER, its HOST and its PORT.
 * 
 * Now you can call the method getConnection, which will give you back a
 * JDBC Connection.
 * 
 * 
 * @author Ernesto Cantú Valle 
 * mail: ernesto.cantu1989@live.com
 * 
 * 27/03/2015
 */
public class DatabaseConnection {
    /** JDBC Driver to Load. */
    private static JDBCDriver JDBC_DRIVER;
    
    /** HOST NAME of the DB */
    private static String HOST;
    
    /** PORT NUMBER .*/
    private static String PORT;
    
    /** DB NAME */
    private static String DB_NAME;
    
    
    /**
     * Private constructor.
     */
    private DatabaseConnection(){
        //private constructor.
    }
    
    
    /**
     * Given a user and the password of the database, it collect's them in properties info,
     * @param user the user of the database
     * @param password the password
     * @return  the setted Properties
     */
    public static Properties setInfo(String user,String password) {
        
        Properties info = new Properties();
        
        info.put("user", user);
        info.putIfAbsent("password", password);
        
        return info;
    }
    

    /**
     * Method that, given a Known Database (via enum value), gets the jdbc driver,
     * to be used in connection.
     * @param JDBC_DRIVER Used JDBC Driver. 
     * @see JDBCDriver
     */
    public static void setJDBC_DRIVER(JDBCDriver JDBC_DRIVER) {
        
        DatabaseConnection.JDBC_DRIVER = JDBC_DRIVER;
    }

    /**
     * Sets the BD HOST name/IP
     * @param HOST the host
     */
    public static void setHOST(String HOST) {
        
        DatabaseConnection.HOST = HOST;
    }

    /**
     * Sets the port number.
     * @param PORT the port number
     */
    public static void setPORT(String PORT) {
        
        DatabaseConnection.PORT = PORT;
    }

    /**
     * sets the Database name.
     * @param DB_NAME the database name.
     */
    public static void setDB_NAME(String DB_NAME) {
       
        DatabaseConnection.DB_NAME = DB_NAME;
    }
    
    /**
     * Method that creates the DB URL.
     * @return the string of the DB Connection.
     */
    private static String getBDURL(){
        String url="";
        
        
        if(JDBC_DRIVER.getRDBMS().equalsIgnoreCase("mysql")){
            System.out.println("Using mysql");
            url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?zeroDateTimeBehavior=convertToNull";
        }
        
        if(JDBC_DRIVER.getRDBMS().equalsIgnoreCase("mssql")){
            System.out.println("Using mssql");
            url = "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DB_NAME;
        }
        
        return url;
    }
        
    /**
     * getConnection is a method that stabishes a connection an returns the object
     * to the user.
     * @param info the connection information
     * @return Connection to database.
     */
    public static Connection getConnection(Properties info){
        Connection conn = null;
                
        try{
            //Loads driver Class in memory
            Class.forName(JDBC_DRIVER.getJDBCDriver());
            
            //Get connection
            conn = DriverManager.getConnection(getBDURL(),info);
        }
        catch(ClassNotFoundException se){
            System.out.println(se.toString());
        } catch (SQLException se) {
            System.out.println(se.toString());
        }
        
        
        return conn;
    }
    
    /**
     * Method that close the JDBC Connection.
     * @param conn Connection to be closed.
     */
    public static void closeConnection(Connection conn){
        try{
            conn.close();
        }
        catch(SQLException se){
        }
    }
}