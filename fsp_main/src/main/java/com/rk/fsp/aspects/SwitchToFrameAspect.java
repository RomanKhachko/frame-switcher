package com.rk.fsp.aspects;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by roman on 5/30/15.
 */
@Aspect
public class SwitchToFrameAspect {

    @Pointcut("@annotation(com.rk.fsp.annotations.RequireSwitchingToFrame)")
    private void whenMethodIsAnnotatedByFrameSwitcher() {
    }

//    @Pointcut("@annotation(requireSwitchingToFrame)")
//    private void whenMethodIsAnnotatedByFrameSwitcher(RequireSwitchingToFrame requireSwitchingToFrame) {
//        System.out.println("in pointcut");
//    }

//    @Pointcut("within(com.rk.fsp.Verifier.*)")
//    private void whenMethodIsAnnotatedByFrameSwitcher() {}

    @Before("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchFrameBefore(JoinPoint joinPoint /*RequireSwitchingToFrame annotation*/) {
//        System.out.println("Locator type: " + annotation.locatorType());
//        joinPoint.getClass().getAnnotations();
        System.out.println("join point: " + joinPoint.getSignature());
        // TODO: switch to frame
    }

    //    @After("com.rk.fsp.aspects.SwitchToFrameAspect.whenMethodIsAnnotatedByFrameSwitcher")
    @After("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchToDefaultContent() {
        System.out.println("in after");
        // TODO: switch to default content
    }
}
