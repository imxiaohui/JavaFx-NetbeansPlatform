/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.personeditor;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.windows.OnShowing;

/**
 *
 * @author gail
 */
@OnShowing
public final class ShowMeTheMoney implements Runnable {
    private final Money money = new Money();

    @Override
    public void run() {
        DialogDescriptor dd = new DialogDescriptor(money, "Here is the Money");
        DialogDisplayer.getDefault().notify(dd);
    }
    
}
