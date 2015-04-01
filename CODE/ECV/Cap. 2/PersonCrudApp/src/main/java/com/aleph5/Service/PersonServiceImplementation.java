/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aleph5.Service;

import com.conciencia.DatabaseConnection.DatabaseConnection;
import com.conciencia.DatabaseConnection.JDBCDriver;
import com.aleph5.DAO.Person;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    String PERSON_DELETED = "Deleted Person";
    String PERSON_UPDATED = "Updated Person";
    String PERSON_ADDED = "Person Added";
    
    private static PersonServiceImplementation instance = null;
    
    PropertyChangeSupport propertyChangeSupport;
    
    private PersonServiceImplementation() {
       
        DatabaseConnection.setJDBC_DRIVER(JDBC_DRIVER);
        DatabaseConnection.setHOST(HOST);
        DatabaseConnection.setPORT(PORT);
        DatabaseConnection.setDB_NAME(DB_NAME);
        props = DatabaseConnection.setInfo(USER, PASSWORD);
        
    }
    
    public void addProertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public static PersonServiceImplementation getInstance(){
        if(instance == null){
            instance = new PersonServiceImplementation();
            instance.propertyChangeSupport = new PropertyChangeSupport(instance);
        }
        
        return instance;
    }
    
    @Override
    public Person findById(Long id) {
        
        Connection conn = DatabaseConnection.getConnection(props);
        
        Person person = new Person();
        
        String query = "SELECT * FROM testJDBC.people where testJDBC.people.id = " + id + ";";

        try{
            
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            if(rs.next()){
               person.setId(rs.getLong("id"));
               person.setFirstName(rs.getString("firstName"));
               person.setMiddeName(rs.getString("middleName"));
               person.setLastName(rs.getString("lastName"));
               person.setSuffix(rs.getString("suffix"));
               if(rs.getString("gender").equalsIgnoreCase("Male")){
                      person.setGender(Person.Gender.MALE);
                  }
                  if(rs.getString("gender").equalsIgnoreCase("Female")){
                      person.setGender(Person.Gender.FEMALE);
                  }
                  else{
                      person.setGender(Person.Gender.UNKNOWN);
                  }
            }
            
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(PersonServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return person;
    }
    
    @Override
    public List<Person> findAll() {
        
        Connection conn = DatabaseConnection.getConnection(props);
        
        List<Person> people = new LinkedList<>();
        
        String query = "SELECT* FROM testJDBC.people;";
        
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
                if(rs.getString("gender").equalsIgnoreCase("Unknown")){
                    person.setGender(Person.Gender.UNKNOWN);
                }
                
                people.add(person);
            }
            
            
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(PersonServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return people;
    }

    @Override
    public Person create(Person person) {
        
        Connection conn = DatabaseConnection.getConnection(props);
        
        String query = "INSERT INTO `testJDBC`.`people` " +
                        "( `firstName`, `middleName`,`lastName`,`suffix`,`gender`,`notes`) " +
                        "VALUES ( " + person.getFirstName() + "," + person.getMiddeName() + "," +
                        person.getLastName() + ", " + person.getSuffix() + ", " + person.getGender().getGenderString() + 
                        ", "+ person.getNotes() + " ) ";
                    
        try {
            
            Statement stm = conn.createStatement();
            stm.executeQuery(query);
            
            query = "Select max(id) from testJDBC.people;";
            
            ResultSet rs = stm.executeQuery(query);
            if(rs.next()){
                Long id = rs.getLong("id");
                person = findById(id);
                
                propertyChangeSupport.firePropertyChange(PERSON_ADDED,null, person);
            }
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return person;
    }

    @Override
    public Person update(Person person) {
        
        String query = "Update table `testJDBC`.`people` " +
                "set firstName = " + person.getFirstName() + "," +
                "middleNale = " + person.getMiddeName() + "," +
                "lastName = " + person.getLastName() + "," +
                "suffix = " + person.getSuffix() + "," +
                "gender = " + person.getGender().getGenderString() + "," +
                 "notes = " + person.getNotes() + " where id = " + person.getId();
                
        Connection conn = DatabaseConnection.getConnection(props);
        
        try {
            
            Statement stm = conn.createStatement();
            stm.executeQuery(query);
            
            propertyChangeSupport.firePropertyChange(PERSON_UPDATED, null, person);
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return person;
        
    }

    @Override
    public Person delete(Long id) {
        
        Person deleted;
        
        deleted = findById(id);
        
        String query = "Delete from testJDBC.people where id = " + id + ";";
        Connection conn = DatabaseConnection.getConnection(props);
        
        try {
            
            Statement stm = conn.createStatement();
            stm.execute(query);
            
            propertyChangeSupport.firePropertyChange(PERSON_DELETED, null, deleted);
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deleted;
    }

    
    
}
