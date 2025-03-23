package com.qa.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;

public class DriverManager {

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    CommonUtils utils = new CommonUtils();

    public AppiumDriver getDriver(){
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2){
        driver.set(driver2);
    }

    public void initializeDriver() throws Exception {
        AppiumDriver driver = null;
        GlobalParams params = new GlobalParams();
        try{
            utils.log().info("initializing Appium driver");
            switch (params.getPlatformName()) {
                case "Android" ->
                        driver = new AndroidDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCaps());
//                case "iOS" ->
//                        driver = new IOSDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCaps());
            }
            if(driver == null){
                throw new Exception("driver is null. ABORT!!!");
            }
            utils.log().info("Driver is initialized");
            DriverManager.driver.set(driver);
        } catch (IOException e) {
            e.printStackTrace();
            utils.log().fatal("Driver initialization failure. ABORT !!!!" + e.toString());
            throw e;
        }

    }

}