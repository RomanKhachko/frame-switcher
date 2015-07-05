package com.rk.fsp.aspects;

import com.rk.fsp.FrameSwitcher;
import com.rk.fsp.interfaces.Driverable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.openqa.selenium.WebDriver;

/**
 * Created by roman on 5/30/15.
 */
@Aspect
public class SwitchToFrameAspect {

    private WebDriver driver;

    @Pointcut("execution(* *(..)) && @annotation(com.rk.fsp.annotations.RequireSwitchingToFrame)")
    private void whenMethodIsAnnotatedByFrameSwitcher() {
    }

    @Before("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchFrameBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        signature.getMethod().getDeclaredAnnotations();

        driver = ((Driverable) joinPoint.getTarget()).getDriver();
        new FrameSwitcher(driver).switchToFrameAccordingToAnnotationParams(signature.getMethod());
    }

    @After("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}
