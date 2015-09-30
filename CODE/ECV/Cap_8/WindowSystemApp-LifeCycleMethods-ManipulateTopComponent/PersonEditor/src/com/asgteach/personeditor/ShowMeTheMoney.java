package com.asgteach.personeditor;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.windows.OnShowing;

/**
 *
 * @author ernesto
 */
@OnShowing
public class ShowMeTheMoney implements Runnable{
    private final Money money = new Money();

    @Override
    public void run() {
        DialogDescriptor dd = new DialogDescriptor(money, "Here is the Money");
        DialogDisplayer.getDefault().notify(dd);
    }
    
}
