package com.rk.fsp.aspects;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.interfaces.Driverable;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Khachko on 5/13/2016.
 */
public class SatisfyingTestPage implements Driverable {

    private WebDriver driver;

    public SatisfyingTestPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @RequireSwitchingToFrame()
    public void requireSwitchingToFrameByDefault() {
    }

    public void doWithoutSwitching(){
    }
}
