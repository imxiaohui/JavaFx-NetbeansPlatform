package com.asgteach.familytreemanagerfx.impl;

import com.asgteach.familytreefx.model.FamilyTreeManager;
import com.asgteach.familytreefx.model.Person;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import org.openide.util.lookup.ServiceProvider;

/**
 * Recordar:
 * 
 * ServiceProvider le indica a Netbeans que esta clase implementa a FamilyTreeManager.
 * En las clases clientes, se hará referencia a los métodos declarados en FamilyTreeManager.
 * Netbeans buscará en el GlobalLookup quien brinda este servicio (Para esto, habrá creado
 * una instancia de esta clase). El servicio será provisto por esta instancia.
 * 
 * Esta clase por tanto, debe tener un constructor por default, o no declarar constructores.
 * 
 * 
 * @author ernesto
 */
@ServiceProvider(service = FamilyTreeManager.class)
public class FamilyTreeManagerFXImpl implements FamilyTreeManager {
    
    //Observable Map. Tiene la capacidad de disparar cambios a change y invalidation listeners.
    private final ObservableMap<Long,Person> observableMap = 
                                FXCollections.observableHashMap();
    
    //Agrego un change listener
    @Override
    public void addListener(
                  MapChangeListener<? super Long,? super Person> ml){
        observableMap.addListener(ml);
    }
    
    //Remuevo un change listener
    @Override
    public void removeListener(
                  MapChangeListener<? super Long,? super Person> ml){
        observableMap.removeListener(ml);
    }
    
    //Agrego un invalidation listener
    @Override
    public void addListener(
                  InvalidationListener il){
        observableMap.addListener(il);
    }
    
    //Remuevo un invalidation listener
    @Override
    public void removeListener(
                  InvalidationListener il){
        observableMap.removeListener(il);
    }
    
    //Manejo de eventos del HashMap.
    
    
    @Override
    public void addPerson(Person p){
        Person person = new Person(p);
        observableMap.put(person.getId(),person);
    }
    
    /*
     *  Nota: Es importante mencionar que el objeto actualizado es una copia del objeto
     *  Original. Se sustituye el objeto original por la copia modificada, para que
     *  el evento de update pueda ser disparado.
     */
    @Override
    public void updatePerson(Person p){
        Person person = new Person(p);
        observableMap.put(person.getId(),person);
    }
    
    @Override
    public void deletePerson(Person p){
        Person person = new Person(p);
        observableMap.remove(person.getId(),person);
    }
    
    @Override
    public List<Person> getAllPeople(){
        List<Person> copyList = new ArrayList<>();
        observableMap.values().stream().forEach((p)->copyList.add(new Person(p)));
        return copyList;
    }
}
