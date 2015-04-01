package com.aleph5.Service;

import com.aleph5.DAO.Person;
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
        
    public Person create(Person person);
    
    public Person update(Person person);
    
    public Person delete(Long id);
        
}
