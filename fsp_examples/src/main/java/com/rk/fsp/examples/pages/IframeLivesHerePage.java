package com.rk.fsp.examples.pages;

import com.rk.fsp.FrameSwitcher;
import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.enums.LocatorType;
import com.rk.fsp.interfaces.Driverable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman Khachko on 27.02.2015.
 * <p>
 * this class describes page available here:
 * http://sitemaker.umich.edu/iframe.example/the__iframe__lives_here
 */
public class IframeLivesHerePage implements Driverable {

    @FindBy(id = "quick-links")
    private WebElement quickLinksButton;

    @FindBy(id = "second")
    private WebElement navigationBar;

    private WebDriver driver;

//    private FrameSwitcher frameSwitcher;

    public IframeLivesHerePage(WebDriver driver) {
//        frameSwitcher = new FrameSwitcher(driver);
        this.driver = driver;
    }



    public String getQuickLinksButtonText() {
        return quickLinksButton.getText();
    }

//    @RequireSwitchingToFrame(locatorType = LocatorType.CSS, locatorValue = "[src*='umich.edu']")
//    public String getNavigationBarText() {
//        return (String) frameSwitcher.invokeWithSwitchingToFrame(this);
//    }

    @RequireSwitchingToFrame(locatorType = LocatorType.CSS, locatorValue = "[src*='umich.edu']")
    public String getNavigationBarText() {
        return navigationBar.getText();
    }

    @RequireSwitchingToFrame
    public String getQuickLinksButtonTextIframe() {
        return getQuickLinksButtonText();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
