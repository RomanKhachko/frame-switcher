package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;

/**
 * Created by roman on 6/5/15.
 */
public class Verifier {

//    @RequireSwitchingToFrame
    public VerEntity foo(){
        System.out.printf("in foo");
        return new VerEntity();
    }
}
