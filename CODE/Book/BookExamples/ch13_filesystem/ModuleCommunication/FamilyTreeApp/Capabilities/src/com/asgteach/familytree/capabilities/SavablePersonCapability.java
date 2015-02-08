/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.capabilities;

import com.asgteach.familytree.model.Person;
import java.io.IOException;

/**
 *
 * @author gail
 */
public interface SavablePersonCapability {
    
    public void save(Person person) throws IOException;
    
}
