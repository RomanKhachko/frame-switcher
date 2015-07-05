package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.enums.LocatorType;
import com.rk.fsp.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

/**
 * Created by Roman Khachko on 01.09.2014.
 */
public class FrameSwitcher {
    private WebDriver driver;

    private Object callerObject;

    public FrameSwitcher(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Arguments priority:
     * - nameOrId
     * - web element
     * - index
     *
     * @param callerMethod
     */
    public void switchToFrameAccordingToAnnotationParams(Method callerMethod) {
        Class annotationClass = RequireSwitchingToFrame.class;
        RequireSwitchingToFrame annotation = (RequireSwitchingToFrame) callerMethod.getAnnotation(annotationClass);

        int frameIndex = annotation.index();

        LocatorType locatorType = annotation.locatorType();
        String locatorValue = annotation.locatorValue();
        String nameOrId = annotation.frameNameOrId();

        if (nameOrId.length() > 0) {
            String xpathLocator = String.format("//*[@name='%1$s' or @id='%1$s']", nameOrId);
            SeleniumUtils.findElementWithTimeout(driver, By.xpath(xpathLocator), 20);
            driver.switchTo().frame(nameOrId);
        } else if (locatorType != LocatorType.NONE && locatorValue.length() > 0) {
            switchToFrameByWebElement(locatorType, locatorValue);
        } else {
            driver.switchTo().frame(frameIndex);
        }
    }

    private void switchToFrameByWebElement(LocatorType locatorType, String locatorValue) {
        By by;
        switch (locatorType) {
            case ID:
                by = By.id(locatorValue);
                break;
            case NAME:
                by = By.name(locatorValue);
                break;
            case CLASS_NAME:
                by = By.className(locatorValue);
                break;
            case TAG_NAME:
                by = By.tagName(locatorValue);
                break;
            case CSS:
                by = By.cssSelector(locatorValue);
                break;
            case XPATH:
                by = By.xpath(locatorValue);
                break;
            default:
                throw new RuntimeException("Invalid locatorType parameter. No exists");
        }

        WebElement frameElement = SeleniumUtils.findElementWithTimeout(driver, by, 60);
        driver.switchTo().frame(frameElement);
    }

}
