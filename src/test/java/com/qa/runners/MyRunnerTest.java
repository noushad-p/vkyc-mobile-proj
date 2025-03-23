package com.qa.runners;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static io.cucumber.testng.CucumberOptions.SnippetType.CAMELCASE;

@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        ,features = {"src/test/resources"}
        ,glue = {"com.qa.stepdef"}
        ,snippets = CAMELCASE
        ,monochrome=true
        ,tags = "@e2e"
)
public class MyRunnerTest extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void initialize() throws Exception {
        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();
        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                + params.getDeviceName());
        new ServerManager().startServer();
        new DriverManager().initializeDriver();
    }

    @AfterClass
    public static void quit(){
        DriverManager driverManager = new DriverManager();
        if(driverManager.getDriver() != null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
        }
    }
}