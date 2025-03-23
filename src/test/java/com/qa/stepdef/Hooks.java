package com.qa.stepdef;

import com.qa.pages.BasePage;
import com.qa.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

import java.io.IOException;

public class Hooks {

    @Before
    public void initialize(){
        BasePage basePage = new BasePage();
        basePage.closeApp();
        //basePage.removeApp();
        basePage.launchApp();
    }

    @After
    public void quit(Scenario scenario) throws IOException {
        if(scenario.isFailed()){
            byte[] screenshot = new DriverManager().getDriver().getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        BasePage basePage = new BasePage();
        //basePage.removeApp();
        basePage.closeApp();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}