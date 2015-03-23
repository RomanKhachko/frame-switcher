package com.rk.fsp.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Roman Khachko on 01.09.2014.
 */
public class SeleniumUtils {
    public static WebElement findElementWithTimeout(WebDriver driver, By by, int timeOutInSeconds) {
        return new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
