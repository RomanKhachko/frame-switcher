package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.enums.LocatorType;
import com.rk.fsp.exceptions.InvalidLocatorTypeException;
import com.rk.fsp.exceptions.ProperAnnotationAbsenceException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by Roman_Khachko on 4/19/2016.
 */
public class FrameSwitcherTest {

    private final CommonMockContainer mockContainer = new CommonMockContainer();
    private final WebDriver mockedDriver = mockContainer.getMockedDriver();
    private final FrameSwitcher frameSwitcher = new FrameSwitcher(mockedDriver);
    private final WebDriver.TargetLocator mockedFrame = mockContainer.getMockedFrame();
    private final WebElement mockedElement = mock(WebElement.class);

    @Before
    public void setUp() {
        when(mockedDriver.findElement(any(By.class))).thenReturn(mockedElement);
        when(mockedElement.isDisplayed()).thenReturn(true);
    }

    @Test
    public void shouldSwitchToDefaultFrame() {
        switchToFrameByAnnotatedMethodName("requireSwitchingToFrameByDefault");
        verify(mockedFrame, times(1)).frame(anyInt());
    }

    @Test
    public void shouldSwitchByNameOrId() {
        switchToFrameByAnnotatedMethodName("requireSwitchingToFrameByNameOrId");
        verify(mockedFrame, times(1)).frame(anyString());
    }

    @Test
    public void shouldSwitchByLocator() {
        switchToFrameByAnnotatedMethodName("requireSwitchingToFrameByLocator");
        verify(mockedFrame, times(1)).frame(any(WebElement.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionOnNullDriver() {
        new FrameSwitcher(null);
    }

    @Test(expected = ProperAnnotationAbsenceException.class)
    public void shouldThrowExceptionOnUnannotatedMethod() {
        switchToFrameByAnnotatedMethodName("notRequiredToSwitch");
    }

    @Test(expected = InvalidLocatorTypeException.class)
    public void shouldThrowExceptionOnInvalidLocatorType() throws Throwable {
        Method switchToFrameByWebElementMethod = null;
        try {
            switchToFrameByWebElementMethod = frameSwitcher.getClass().
                    getDeclaredMethod("switchToFrameByWebElement", LocatorType.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        switchToFrameByWebElementMethod.setAccessible(true);
        try {
            switchToFrameByWebElementMethod.invoke(frameSwitcher, LocatorType.NONE, null);
        } catch (ReflectiveOperationException e) {
            throw e.getCause();
        }
    }

    private void switchToFrameByAnnotatedMethodName(String annotatedMethodName) {
        Method annotatedMethod = null;
        try {
            annotatedMethod = this.getClass().getDeclaredMethod(annotatedMethodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        frameSwitcher.switchToFrameAccordingToAnnotationParams(annotatedMethod);
    }

    @RequireSwitchingToFrame()
    private void requireSwitchingToFrameByDefault() {
    }

    @RequireSwitchingToFrame(frameNameOrId = "test_id")
    private void requireSwitchingToFrameByNameOrId() {
    }

    @RequireSwitchingToFrame(locatorType = LocatorType.CLASS_NAME, locatorValue = ".any")
    private void requireSwitchingToFrameByLocator() {
    }

    private void notRequiredToSwitch() {
    }

}
