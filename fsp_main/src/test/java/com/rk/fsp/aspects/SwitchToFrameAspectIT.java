package com.rk.fsp.aspects;

import com.rk.fsp.CommonMockContainer;
import org.junit.Test;
import org.mockito.InOrder;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by Roman Khachko on 5/23/2016.
 * This class contains tests where aspect advice is applied
 * to the real method call
 */
public class SwitchToFrameAspectIT {

    private final CommonMockContainer mockContainer = new CommonMockContainer();
    private final WebDriver.TargetLocator mockedFrame = mockContainer.getMockedFrame();

    @Test
    public void shouldMatchPointcutAndCallAdviceSequence() {
        new SatisfyingTestPage(mockContainer.getMockedDriver()).requireSwitchingToFrameByDefault();

        InOrder inOrder = inOrder(mockedFrame);
        inOrder.verify(mockedFrame, times(1)).frame(anyInt());
        inOrder.verify(mockedFrame, times(1)).defaultContent();

    }

    @Test
    public void shouldNotCallAfterAdviceIfAnyExceptionOccurs() {
        boolean isRuntimeExceptionThrown = false;
        try {
            new InvalidTestPage().requireSwitchingToFrameByDefault();
        } catch (RuntimeException e) {
            isRuntimeExceptionThrown = true;
        }

        assertTrue("Exception hasn't been thrown", isRuntimeExceptionThrown);
        verify(mockedFrame, never()).defaultContent();
    }
}
