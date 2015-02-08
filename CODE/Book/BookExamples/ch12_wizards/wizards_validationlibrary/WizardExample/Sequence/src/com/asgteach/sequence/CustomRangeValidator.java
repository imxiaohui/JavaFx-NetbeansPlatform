/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.sequence;

import org.netbeans.validation.api.Problems;
import org.netbeans.validation.api.Validator;
import org.netbeans.validation.api.builtin.Validators;

/**
 *
 * @author gail
 */

public class CustomRangeValidator implements Validator<String> {
    private Integer minVal = new Integer(0);
    private Integer maxVal= new Integer(0);

    public void setMaxVal(Integer maxVal) {
        this.maxVal = maxVal;
    }

    public void setMinVal(Integer minVal) {
        this.minVal = minVal;
    }

    @Override
    public boolean validate(Problems prblms, String string, String t) {
        Validator<String> rangeDelegate = Validators.numberRange(minVal, maxVal);         
        return rangeDelegate.validate(prblms, string, t);
    }        
}
