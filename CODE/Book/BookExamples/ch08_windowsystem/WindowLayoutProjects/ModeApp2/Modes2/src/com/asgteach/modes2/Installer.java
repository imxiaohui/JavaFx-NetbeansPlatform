/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.asgteach.modes2;

import org.openide.modules.OnStart;

/**
 *
 * @author gail
 */
@OnStart
public class Installer implements Runnable {

    @Override
    public void run() {
        System.setProperty("netbeans.winsys.hideEmptyDocArea", "true");
    }
    
}
