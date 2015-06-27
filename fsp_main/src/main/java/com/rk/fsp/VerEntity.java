package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * Created by roman on 6/10/15.
 */
//@Configurable(autowire = Autowire.BY_TYPE/*autowire= Autowire.BY_TYPE,dependencyCheck=true*/)
public class VerEntity {

    @RequireSwitchingToFrame
    public void bar(){
        System.out.println("in a bAR");
    }
}
