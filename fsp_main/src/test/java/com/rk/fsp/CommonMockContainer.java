package com.rk.fsp;

import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Roman_Khachko on 5/16/2016.
 */
public class CommonMockContainer {

    private final WebDriver mockedDriver = mock(WebDriver.class);
    private final WebDriver.TargetLocator mockedFrame = mock(WebDriver.TargetLocator.class);

    public CommonMockContainer() {
        when(mockedDriver.switchTo()).thenReturn(mockedFrame);
        when(mockedFrame.frame(0)).thenReturn(mockedDriver);
    }

    public WebDriver getMockedDriver() {
        return mockedDriver;
    }

    public WebDriver.TargetLocator getMockedFrame() {
        return mockedFrame;
    }
}
