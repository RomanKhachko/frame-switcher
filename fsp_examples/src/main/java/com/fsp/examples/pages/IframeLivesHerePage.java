package com.fsp.examples.pages;

import com.fsp.FrameSwitcher;
import com.fsp.annotations.RequireSwitchingToFrame;
import com.fsp.enums.LocatorType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman Khachko on 27.02.2015.
 * <p/>
 * this class describes page available here:
 * http://sitemaker.umich.edu/iframe.example/the__iframe__lives_here
 */
public class IframeLivesHerePage {

    @FindBy(id = "quick-links")
    private WebElement quickLinksButton;

    @FindBy(id = "second")
    private WebElement navigationBar;

    private FrameSwitcher frameSwitcher;

    public IframeLivesHerePage(WebDriver driver) {
        frameSwitcher = new FrameSwitcher(driver);
    }

    @RequireSwitchingToFrame
    public String getQuickLinksButtonTextIframe() {
        return (String) frameSwitcher.invokeWithSwitchingToFrame(this);
    }

    public String getQuickLinksButtonText() {
        return quickLinksButton.getText();
    }

    @RequireSwitchingToFrame(locatorType = LocatorType.CSS, locatorValue = "[src*='umich.edu']")
    public String getNavigationBarText() {
        return (String) frameSwitcher.invokeWithSwitchingToFrame(this);
    }

    private String getNavigationBarTextImpl() {
        return navigationBar.getText();
    }

    private String getQuickLinksButtonTextIframeImpl() {
        return getQuickLinksButtonText();
    }

}
