package com.rk.fsp.aspects;

import com.rk.fsp.FrameSwitcher;
import com.rk.fsp.exceptions.DriverableNotImplementedException;
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

    private static final ThreadLocal<RuntimeException> lastThrownException = new ThreadLocal<RuntimeException>();
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    @Pointcut("execution(* *(..)) && @annotation(com.rk.fsp.annotations.RequireSwitchingToFrame)")
    private void whenMethodIsAnnotatedByFrameSwitcher() {
    }

    @Before("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchFrameBefore(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        signature.getMethod().getDeclaredAnnotations();

        checkTargetImplementsDriverable(joinPoint);

        WebDriver localDriver = ((Driverable) joinPoint.getTarget()).getDriver();
        driver.set(localDriver);
        createFrameSwitcher(localDriver).switchToFrameAccordingToAnnotationParams(signature.getMethod());
    }

    @After("whenMethodIsAnnotatedByFrameSwitcher()")
    public void switchToDefaultContent(final JoinPoint joinPoint) {
        RuntimeException exception = lastThrownException.get();
        if (exception != null) {
            throw exception;
        }
        getWebDriver().switchTo().defaultContent();
    }

    FrameSwitcher createFrameSwitcher(WebDriver driver) {
        return new FrameSwitcher(driver);
    }

    WebDriver getWebDriver() {
        return driver.get();
    }

    private void checkTargetImplementsDriverable(JoinPoint joinPoint) {
        if (!(joinPoint.getTarget() instanceof Driverable)) {
            RuntimeException exception = new DriverableNotImplementedException();
            lastThrownException.set(exception);
            throw exception;
        }
    }
}