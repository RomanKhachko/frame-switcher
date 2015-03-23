package com.rk.fsp.examples.tests;

import com.rk.fsp.examples.pages.IframeLivesHerePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Roman Khachko on 27.02.2015.
 */
public class TestExample {

    private WebDriver driver;
    private IframeLivesHerePage iframeLivesHerePage;
    private final String EXPECTED_TEXT = "QUICK LINKS";

    @Before
    public void setUp() {
        // Firefox driver is provided here, because you don't need to do
        // any extra steps. Just run the code!
        driver = new FirefoxDriver();
        driver.get("http://sitemaker.umich.edu/iframe.example/the__iframe__lives_here");

        // instantiating a proper page:
        iframeLivesHerePage = PageFactory.initElements(driver, IframeLivesHerePage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /**
     * This test should fail, because the frame hasn't been switched automatically
     */
    @Test
    public void testFrameWithoutSwitching() {
        Assert.assertEquals(EXPECTED_TEXT, iframeLivesHerePage.getQuickLinksButtonText());
    }

    @Test
    public void testFrameWithSwitchingToDefaultFrame() {
        Assert.assertEquals(EXPECTED_TEXT, iframeLivesHerePage.getQuickLinksButtonTextIframe());
    }

    @Test
    public void testFrameWithSwitchingByCustomLocator() {
        Assert.assertTrue(iframeLivesHerePage.getNavigationBarText().contains("MENU"));
    }
}
