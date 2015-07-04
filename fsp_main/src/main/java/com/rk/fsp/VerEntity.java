package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.interfaces.Driverable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by roman on 6/10/15.
 */
public class VerEntity implements Driverable{

    String value = "val";

    @RequireSwitchingToFrame
    public void bar(){
        System.out.println("in a bAR");
    }

    public static void main(String[] args){
        new VerEntity().bar();
    }

    public WebDriver getDriver() {
        return new FirefoxDriver();
    }
}
