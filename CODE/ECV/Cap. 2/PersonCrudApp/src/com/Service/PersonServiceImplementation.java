/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Service;

import com.conciencia.DatabaseConnection.DatabaseConnection;
import com.conciencia.DatabaseConnection.JDBCDriver;
import com.model.DAO.Person;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa el Servicio PersonService. Para este caso, como el origen de los datos
 * es de BD, se utiliza la librería JDBCConnectionManager.
 * @author ernesto
 */
public class PersonServiceImplementation implements PersonService{
    
    public static Properties props;
    String USER = "root";
    String PASSWORD = "4747819";
    JDBCDriver JDBC_DRIVER = JDBCDriver.MYSQL;
    String HOST = "localhost";
    String PORT = "3306";
    String DB_NAME = "testJDBC";
    
    private static PersonServiceImplementation instance = null;
    
    private PersonServiceImplementation() {
       
        DatabaseConnection.setJDBC_DRIVER(JDBC_DRIVER);
        DatabaseConnection.setHOST(HOST);
        DatabaseConnection.setPORT(PORT);
        DatabaseConnection.setDB_NAME(DB_NAME);
    }

    public static PersonServiceImplementation getInstance(){
        if(instance == null){
            instance = new PersonServiceImplementation();
        }
        return instance;
    }
    
    @Override
    public Person findById(Long id) {
        return null;
    }
    
    @Override
    public List<Person> findAll() {
        Properties props = DatabaseConnection.setInfo(USER, PASSWORD);
        Connection conn = DatabaseConnection.getConnection(props);
        
        List<Person> people = new LinkedList<>();
        
        String query = "SELECT* FROM testJDBC.People;";
        
        try {
            Statement stm = conn.createStatement();
            
            ResultSet rs = stm.executeQuery(query);
            
            while(rs.next()){
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setFirstName(rs.getString("firstName"));
                person.setMiddeName(rs.getString("middleName"));
                person.setLastName(rs.getString("lastName"));
                person.setSuffix(rs.getString("suffix"));
                person.setNotes(rs.getString("notes"));
                if(rs.getString("gender").equalsIgnoreCase("Male")){
                    person.setGender(Person.Gender.MALE);
                }
                if(rs.getString("gender").equalsIgnoreCase("Female")){
                    person.setGender(Person.Gender.FEMALE);
                }
                else{
                    person.setGender(Person.Gender.UNKNOWN);
                }
                
                people.add(person);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PersonServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return people;
    }

    @Override
    public Person create() {
        return null;
    }

    @Override
    public Person update(Long id) {
        return null;
    }

    @Override
    public Person delete(Long id) {
        return null;
    }

    
    
}
