package com.rk.fsp.examples.pages;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.enums.LocatorType;
import com.rk.fsp.interfaces.Driverable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Roman Khachko on 3/21/2016.
 * <p>
 * this class describes page available here:
 * http://frameswitcher-demo.appspot.com/
 */
public class FrameSwitcherDemoPage implements Driverable {

    @FindBy(id = "frame_url_btn")
    private WebElement frameUrlBtn;

    @FindBy(id = "main-expl")
    private WebElement frameNote;

    private String alertText;

    private WebDriver driver;

    public FrameSwitcherDemoPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getFrameUrlBtnText() {
        return frameUrlBtn.getText();
    }

    @RequireSwitchingToFrame(frameNameOrId = "main-example-frame")
    public void clickFrameUrlBtn() {
        frameUrlBtn.click();
    }

    @RequireSwitchingToFrame(locatorType = LocatorType.CSS, locatorValue = "[src*='firstframe']")
    public String getFrameNote() {
        return frameNote.getText();
    }

    @RequireSwitchingToFrame()
    public String getFrameUrlBtnTextIframe() {
        return getFrameUrlBtnText();
    }

}
