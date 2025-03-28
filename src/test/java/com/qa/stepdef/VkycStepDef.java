package com.qa.stepdef;

import com.api.VcipLinkDetails;
import com.qa.pages.VkycScreen;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class VkycStepDef {

    @Given("User Launch VKyc Application")
    public void userLunch_VKYC_Application(){

    }

    @And("Trigger VCIP")
    public void triggerVCIP(){
        new VcipLinkDetails().methodTest();
    }

    @When("User enter Url and tap on Invoke SDK")
    public void userTapOnInvokeSDK(){
        new VkycScreen().tapOnMoveToSdkBtn();
    }
    @And("User clicks on Get Started button")
    public void userClicksOnGetStartedButton(){
        new VkycScreen().getStarted();
    }

    @When("User doing PAN verification")
    public void userDoingPanVerification(){
        new VkycScreen().panVerification();
    }

    @And("User added Occupation details")
    public void userAddedOccupationDetails(){
        new VkycScreen().occupationDetails();
    }

    @And("User doing Video call verification")
    public void videoCallVerification(){
        new VkycScreen().videoVerification();
    }
}
