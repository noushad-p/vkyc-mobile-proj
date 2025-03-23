package com.qa.utils;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

public class CapabilitiesManager {
    CommonUtils utils = new CommonUtils();

    public DesiredCapabilities getCaps() throws IOException {
        GlobalParams params = new GlobalParams();
        try{
            utils.log().info("getting capabilities");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", params.getPlatformName());
            caps.setCapability("udid", params.getUDID());
            caps.setCapability("deviceName", params.getDeviceName());

            switch(params.getPlatformName()){
                case "Android":
                    caps.setCapability("automationName", PropertyManager.getProperty("androidAutomationName"));
                    caps.setCapability("appPackage", PropertyManager.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", PropertyManager.getProperty("androidAppActivity"));
//                    String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//                            + File.separator + "resources" + File.separator + "apps" + File.separator + "VKYC(Android).apk";
                    String androidAppUrl = PropertyManager.getProperty("androidAppUrl");
                    utils.log().info("appUrl is : " + androidAppUrl);
                    caps.setCapability("app", androidAppUrl);
                    caps.setCapability("autoGrantPermissions", true);
                    //--> Disable keyboard
                    caps.setCapability("unicodeKeyboard", true);
                    caps.setCapability("resetKeyboard", true);

//                    break;
//                case "iOS":
//                    caps.setCapability("automationName", PropertyManager.getProperty("iOSAutomationName"));
//                    //String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
//                    String iOSAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//                            + File.separator + "resources" + File.separator + "apps" + File.separator + "SwagLabsMobileApp.app";
//                    utils.log().info("appUrl is" + iOSAppUrl);
//                    caps.setCapability("bundleId", PropertyManager.getProperty("iOSBundleId"));
//                    caps.setCapability("wdaLocalPort", params.getWdaLocalPort());
//                    caps.setCapability("webkitDebugProxyPort", params.getWebkitDebugProxyPort());
//                    caps.setCapability("app", iOSAppUrl);
//                    break;
            }
            return caps;
        } catch(Exception e){
            e.printStackTrace();
            utils.log().fatal("Failed to load capabilities. ABORT!!" + e.toString());
            throw e;
        }
    }
}