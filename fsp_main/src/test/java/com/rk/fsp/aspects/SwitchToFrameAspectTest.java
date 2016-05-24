package com.rk.fsp.aspects;

import com.rk.fsp.CommonMockContainer;
import com.rk.fsp.FrameSwitcher;
import com.rk.fsp.exceptions.DriverableNotImplementedException;
import com.rk.fsp.exceptions.ProperAnnotationAbsenceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import static java.util.Collections.synchronizedSet;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Roman_Khachko on 4/19/2016.
 */
public class SwitchToFrameAspectTest {

    private static final String DEFAULT_METHOD_NAME = "requireSwitchingToFrameByDefault";
    private final CommonMockContainer mockContainer = new CommonMockContainer();
    private final WebDriver mockedDriver = mockContainer.getMockedDriver();

    @Test(expected = ProperAnnotationAbsenceException.class)
    public void shouldBreakTheExecutionIfSwitchProcessInvalid() {
        JoinPoint mockedJoinPoint = prepareJoinPointMethodContext(SatisfyingTestPage.class, "doWithoutSwitching",
                new SatisfyingTestPage(mockedDriver));

        callBeforeAdvice(mockedJoinPoint);
    }

    @Test(expected = DriverableNotImplementedException.class)
    public void shouldBreakExecutionIfDriverableIsNotImplemented() {
        JoinPoint mockedJoinPoint = prepareJoinPointMethodContext(InvalidTestPage.class, DEFAULT_METHOD_NAME,
                new InvalidTestPage());
        when(mockedJoinPoint.getTarget()).thenReturn(new InvalidTestPage());

        callBeforeAdvice(mockedJoinPoint);
    }

    @Test()
    public void webDriverShouldBeHandledProperlyInMultithreadingMode() {
        SwitchToFrameAspect aspect = prepareSpiedAspectObjectContext();

        Set driverComparisonResultSet = synchronizedSet(new HashSet<Boolean>());
        int threadCount = 10;
        ExecutorService executor = newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Runnable runnable = () -> {
                WebDriver threadDriver = mock(WebDriver.class);
                JoinPoint mockedJoinPoint = prepareJoinPointMethodContext(SatisfyingTestPage.class, DEFAULT_METHOD_NAME,
                        new SatisfyingTestPage(threadDriver));
                aspect.switchFrameBefore(mockedJoinPoint);

                driverComparisonResultSet.add(threadDriver == aspect.getWebDriver());

            };
            executor.execute(runnable);
        }
        stopThreadExecutor(executor);

        assertFalse("Driver instances are not the same in advices 'before' and 'after'",
                driverComparisonResultSet.contains(false));
    }

    @Test
    public void shouldWorkProperlyWithExceptionInMultithreadingMode() {
        SwitchToFrameAspect aspect = prepareSpiedAspectObjectContext();

        Set threadsWithNoExceptionsResult = synchronizedSet(new HashSet<>());
        Set threadsWithExceptionsResult = synchronizedSet(new HashSet<>());

        final String NO_EXCEPTION = "no exception";

        Runnable threadWithoutExceptions = prepareThreadWithoutExceptions(aspect, threadsWithNoExceptionsResult, NO_EXCEPTION);
        Runnable threadWithExceptions = prepareThreadWithExceptions(aspect, threadsWithExceptionsResult, NO_EXCEPTION);

        int threadCount = 10;
        ExecutorService executor = newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            if (i % 2 == 0) {
                executor.execute(threadWithoutExceptions);
            } else {
                executor.execute(threadWithExceptions);
            }
        }
        stopThreadExecutor(executor);

        assertTrue("There should be no exception for positive threads",
                threadsWithNoExceptionsResult.size() == 1);
        assertTrue("There should be exception for negative", threadsWithExceptionsResult.size() == 1);
        assertEquals(threadsWithNoExceptionsResult.toArray()[0], NO_EXCEPTION);
        assertEquals(threadsWithExceptionsResult.toArray()[0], DriverableNotImplementedException.class);
    }

    private Runnable prepareThreadWithExceptions(SwitchToFrameAspect aspect, Set threadsWithExceptionsResult,
                                                 String defaultString) {
        return () -> {
            Object objectToAdd = defaultString;
            JoinPoint mockedJoinPoint =
                    prepareJoinPointMethodContext(InvalidTestPage.class, DEFAULT_METHOD_NAME, new InvalidTestPage());
            try {
                aspect.switchFrameBefore(mockedJoinPoint);
            } catch (DriverableNotImplementedException e) {
                objectToAdd = e.getClass();
            }
            threadsWithExceptionsResult.add(objectToAdd);
        };
    }

    private Runnable prepareThreadWithoutExceptions(SwitchToFrameAspect aspect, Set threadsWithNoExceptionsResult,
                                                    String defaultString) {
        return () -> {
            WebDriver threadDriver = mock(WebDriver.class);
            JoinPoint mockedJoinPoint = prepareJoinPointMethodContext(SatisfyingTestPage.class, DEFAULT_METHOD_NAME,
                    new SatisfyingTestPage(threadDriver));
            try {
                aspect.switchFrameBefore(mockedJoinPoint);
            } catch (DriverableNotImplementedException e) {
                threadsWithNoExceptionsResult.add(e);
                throw e;
            }
            threadsWithNoExceptionsResult.add(defaultString);
        };
    }

    private void stopThreadExecutor(ExecutorService executor) {
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    private SwitchToFrameAspect prepareSpiedAspectObjectContext() {
        SwitchToFrameAspect aspect = spy(new SwitchToFrameAspect());
        FrameSwitcher mockedFrameSwitcher = mock(FrameSwitcher.class);
        doNothing().when(mockedFrameSwitcher)
                .switchToFrameAccordingToAnnotationParams(any(Method.class));
        doReturn(mockedFrameSwitcher).when(aspect).createFrameSwitcher(any(WebDriver.class));
        return aspect;
    }

    private void callBeforeAdvice(JoinPoint mockedJoinPoint) {
        SwitchToFrameAspect switchToFrameAspect = new SwitchToFrameAspect();
        switchToFrameAspect.switchFrameBefore(mockedJoinPoint);
    }

    private JoinPoint prepareJoinPointMethodContext(Class containingClass, String methodName, Object pageObject) {
        JoinPoint mockedJoinPoint = mock(JoinPoint.class);
        MethodSignature mockedMethodSignature = mock(MethodSignature.class);
        Method annotatedMethod = getJoinPointMethod(containingClass, methodName);

        when(mockedJoinPoint.getSignature()).thenReturn(mockedMethodSignature);
        when(mockedMethodSignature.getMethod()).thenReturn(annotatedMethod);
        when(mockedJoinPoint.getTarget()).thenReturn(pageObject);

        return mockedJoinPoint;
    }

    private Method getJoinPointMethod(Class containingClass, String methodName) {
        Method joinPointMethod;
        try {
            joinPointMethod = containingClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return joinPointMethod;
    }


}
