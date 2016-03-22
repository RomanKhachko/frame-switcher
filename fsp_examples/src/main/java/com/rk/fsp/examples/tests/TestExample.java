package com.rk.fsp.examples.tests;

import com.rk.fsp.examples.pages.FrameSwitcherDemoPage;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Roman Khachko on 27.02.2015.
 */
public class TestExample {

    private final String EXPECTED_TEXT = "Get the frame URL";
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private WebDriver driver;
    private FrameSwitcherDemoPage frameSwitcherDemoPage;

    @Before
    public void setUp() {
        // Firefox driver is provided here, because you don't need to do
        // any extra steps. Just run the code!
        driver = new FirefoxDriver();
        driver.get("http://frameswitcher-demo.appspot.com/");

        // instantiating a proper page:
        frameSwitcherDemoPage = PageFactory.initElements(driver, FrameSwitcherDemoPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /**
     * This test should verify 'NoSuchElementException' is thrown
     * because the frame hasn't been switched automatically
     */
    @Test
    public void testFrameWithoutSwitching() {
        exception.expect(NoSuchElementException.class);
        frameSwitcherDemoPage.getFrameUrlBtnText();
    }

    @Test
    public void testFrameWithSwitchingToDefaultFrame() {
        Assert.assertEquals(EXPECTED_TEXT, frameSwitcherDemoPage.getFrameUrlBtnTextIframe());
    }

    @Test
    public void testFrameWithSwitchingByCustomLocator() {
        String expectedText = "This is a first level sub-frame";
        Assert.assertEquals(expectedText, frameSwitcherDemoPage.getFrameNote());
    }

}
