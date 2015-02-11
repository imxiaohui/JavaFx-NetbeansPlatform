/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repasopropertychangesuport;

import com.source.classes.ClaseDependiente;
import com.source.classes.ClaseIndependiente;

/**
 *
 * @author Ernesto Cantú Valle <ernesto.cantu1989@live.com>
 */
public class RepasoPropertyChangeSuport {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ClaseIndependiente ci = new ClaseIndependiente(12, "String 1");
        ClaseDependiente cd = new ClaseDependiente(ci);
        ci.setCantidad(13);
        ci.setNombre("String 2");
        System.out.println(cd.toString());
        
    }
    
}
