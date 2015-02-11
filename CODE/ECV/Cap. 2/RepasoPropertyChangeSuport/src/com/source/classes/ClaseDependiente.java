/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.source.classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

/**
 *
 * @author Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 */
public class ClaseDependiente {
    
    private String nombreClaseDependiente;
    private Integer cantidadClaseDependiente;
    private ClaseIndependiente claseIndependiente;


    final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().compareTo("nombreAtribute")==0){
                setNombreClaseDependiente((String)evt.getNewValue());
            }
            else
                setCantidadClaseDependiente((Integer)evt.getNewValue());
        }
    };
    
    public ClaseDependiente(ClaseIndependiente claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
        this.nombreClaseDependiente = claseIndependiente.getNombre();
        this.cantidadClaseDependiente = claseIndependiente.getCantidad();
        System.out.println(toString());
                
        claseIndependiente.addPropertyChangeListener(this.propertyChangeListener);
        
    }
    
    

    public String getNombreClaseDependiente() {
        return nombreClaseDependiente;
    }

    public void setNombreClaseDependiente(String nombreClaseIndependiente) {
        this.nombreClaseDependiente = nombreClaseIndependiente;
    }

    public Integer getCantidadClaseDependiente() {
        return cantidadClaseDependiente;
    }

    public void setCantidadClaseDependiente(Integer cantidadClaseIndependiente) {
        this.cantidadClaseDependiente = cantidadClaseIndependiente;
    }
    
    public ClaseIndependiente getClaseIndependiente() {
        return claseIndependiente;
    }

    public void setClaseIndependiente(ClaseIndependiente claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
    }

    @Override
    public String toString() {
        return "ClaseDependiente{" + "nombreClaseDependiente=" + nombreClaseDependiente + ", cantidadClaseIndependiente=" + cantidadClaseDependiente + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.nombreClaseDependiente);
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
        final ClaseDependiente other = (ClaseDependiente) obj;
        if (!Objects.equals(this.nombreClaseDependiente, other.nombreClaseDependiente)) {
            return false;
        }
        return true;
    }

}
