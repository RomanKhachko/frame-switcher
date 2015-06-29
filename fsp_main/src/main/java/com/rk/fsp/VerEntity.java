package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;

/**
 * Created by roman on 6/10/15.
 */
//@Configurable(autowire = Autowire.BY_TYPE/*autowire= Autowire.BY_TYPE,dependencyCheck=true*/
public class VerEntity {

    @RequireSwitchingToFrame
    public void bar(){
        System.out.println("in a bAR");
    }
}
