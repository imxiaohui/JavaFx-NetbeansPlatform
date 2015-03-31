package com.Service;

import com.model.DAO.Person;
import java.util.List;

/**
 * PersonService es una interface de ejemplo para interacción en Aplicaciones Crud.
 * 
 * Define todas las acciones que pueden realizarse con un objeto de la clase
 * Person
 * 
 * @author ernesto
 */
public interface PersonService {
 
    public Person findById(Long id);
    
    public List<Person> findAll();
        
    public Person create();
    
    public Person update(Long id);
    
    public Person delete(Long id);
        
}
