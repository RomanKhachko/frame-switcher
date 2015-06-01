package com.rk.fsp.aspects;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import org.aspectj.lang.JoinPoint;
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
    public void switchFrameBefore(/*JoinPoint joinPoint,*/ RequireSwitchingToFrame annotation) {
        System.out.println("Locator type: " + annotation.locatorType());
//        joinPoint.getClass().getAnnotations();
        // TODO: switch to frame
    }

    public void switchToDefaultContent(){
        // TODO: switch to default content
    }
}
