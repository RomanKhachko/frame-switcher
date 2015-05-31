package com.rk.fsp.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by roman on 5/30/15.
 */
@Aspect
public class SwitchToFrameAspect {

    @Pointcut("@annotation(com.rk.fsp.annotations.RequireSwitchingToFrame)")
    private void whenMethodIsAnnotatedByFrameSwitcher() {}

    @Before("whenMethodIsAnnotatedByFrameSwitcher")
    public void switchFrameBefore() {
        // TODO: switch to frame
    }

    public void switchToDefaultContent(){
        // TODO: switch to default content
    }
}
