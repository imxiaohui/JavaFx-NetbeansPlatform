/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.familytree.model;

import java.awt.datatransfer.DataFlavor;

/**
 *
 * @author gail
 */
public class PersonFlavor extends DataFlavor {

    public static final DataFlavor PERSON_FLAVOR = new PersonFlavor();

    public PersonFlavor() {
         super(Person.class, "Person");
    }
    
}
