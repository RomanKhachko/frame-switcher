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

import java.lang.annotation.Annotation;

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
    public void switchFrameBefore(JoinPoint joinPoint /*RequireSwitchingToFrame annotation*/) {
//        System.out.println("Locator type: " + annotation.locatorType());
//        joinPoint.getClass().getAnnotations();
        System.out.println("join point: " + joinPoint.getSignature());
        // TODO: switch to frame
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        String methodName = signature.getMethod().getName();
//        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        signature.getMethod().getDeclaredAnnotations();
        driver = ((Driverable) joinPoint.getTarget()).getDriver();
        System.out.println("before switch driver: " + driver);
        new FrameSwitcher(driver).switchToFrameAccordingToAnnotationParams( signature.getMethod());
        System.out.println("after switch");
//        try {
//            Annotation[][] annotations = joinPoint.getTarget().getClass().getMethod(methodName,parameterTypes).getParameterAnnotations();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }

    //    @After("com.rk.fsp.aspects.SwitchToFrameAspect.whenMethodIsAnnotatedByFrameSwitcher")
    @After("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchToDefaultContent() {
        System.out.println("in after");
        driver.switchTo().defaultContent();
    }
}
