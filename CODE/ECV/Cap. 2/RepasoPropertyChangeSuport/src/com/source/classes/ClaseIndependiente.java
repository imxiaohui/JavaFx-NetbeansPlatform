package com.source.classes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

/**
 * Repaso de PropertyChangeListener.
 * @author Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 */
public class ClaseIndependiente {
    
    private Integer cantidad;
    private String nombre;
    private PropertyChangeSupport propertyChangeSupport = null;
    
    //Nombre de los atributos.
    public static final String  NOMBRE = "nombreAtribute";
    public static final String CANTIDAD = "cantidadAtribute";

    public ClaseIndependiente() {
        this(0,"");
    }

    public ClaseIndependiente(Integer cantidad, String nombre) {
        this.cantidad = cantidad;
        this.nombre = nombre;
    }
    
    
    
    public PropertyChangeSupport getPropertyChangeSupport(){
        if(propertyChangeSupport == null){
            propertyChangeSupport = new PropertyChangeSupport(this);
        }
        return propertyChangeSupport;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener){
        getPropertyChangeSupport().addPropertyChangeListener(propertyChangeListener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener){
        getPropertyChangeSupport().removePropertyChangeListener(propertyChangeListener);
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        Integer old = this.cantidad;
        this.cantidad = cantidad;
        propertyChangeSupport.firePropertyChange(CANTIDAD, old, cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        String old = this.nombre;
        this.nombre = nombre;
        propertyChangeSupport.firePropertyChange(NOMBRE, old, nombre);
    }

    @Override
    public String toString() {
        return "ClaseIndependiente{" + "cantidad=" + cantidad + ", nombre=" + nombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClaseIndependiente other = (ClaseIndependiente) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    
}
