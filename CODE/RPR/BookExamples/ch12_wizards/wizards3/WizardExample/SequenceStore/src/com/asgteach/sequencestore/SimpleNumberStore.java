/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.sequencestore;

import com.asgteach.sequencestore.api.NumberStore;
import java.util.HashSet;
import java.util.Set;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author gail
 */
@ServiceProvider(service = NumberStore.class)
public class SimpleNumberStore implements NumberStore {
    
    private Set<Integer> numberSet = new HashSet<Integer>();

    @Override
    public boolean isUnique(Integer num) {
        return !numberSet.contains(num);
    }

    @Override
    public boolean store(Integer num) {
        // add returns true if the collection changed
        // (if num was not already present)
        return numberSet.add(num);
    }
    
}
