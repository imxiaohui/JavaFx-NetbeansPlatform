package com.asgteach.modes2;


import org.openide.modules.OnStart;

/**
 *
 * @author ernesto
 */
@OnStart
public class Installer implements Runnable{

    @Override
    public void run() {
        System.setProperty("netbeans.winsys.hideEmptyDocArea", "true");
    }   
}
