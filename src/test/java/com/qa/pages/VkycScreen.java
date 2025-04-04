package com.qa.pages;

import com.api.VcipLinkDetails;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;


public class VkycScreen extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.EditText")
    public WebElement urlTxtField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Invoke SDK\"]")
    public WebElement InvokeSDK_Btn;

    public void tapOnMoveToSdkBtn(){
        clickOnElement(urlTxtField,"Tap on sdk field");
        clearText(urlTxtField);
        typeTextIntoElement(urlTxtField, VcipLinkDetails.vcipId);
        clickOnElement(InvokeSDK_Btn,"Tap on Invoke SDK Button");
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Complete\"]")
    public WebElement completeYourVKYCText;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Get Started\"]")
    public WebElement getStartedBtn;

    @AndroidFindBy(xpath = "(//android.widget.ImageView)[2]")
    public WebElement acceptUserConsent;


    public void getStarted(){
        clickOnElement(getStartedBtn,"user clicks on get started button");
        clickOnElement(acceptUserConsent,"click on accept user consent");
        clickOnElement(proceedBtn,"click on proceed button");
        clickOnElement(getStartedBtn,"user clicks on get started button");
        clickOnElement(getStartedBtn,"user clicks on get started button");
    }

// pan verification

    @AndroidFindBy(xpath = "//android.view.View/android.widget.ImageView[1]")
    public WebElement uploadFile;

    @AndroidFindBy(xpath = "(//androidx.cardview.widget.CardView)[1]")
    public WebElement firstImg;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Continue\"]")
    public WebElement continueBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Proceed']")
    public WebElement proceedBtn;

    public void panVerification(){
//        typeTextIntoElement(uploadFile,System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"images"+File.separator+"PANCARD.png");
        clickOnElement(uploadFile,"click on upload file");
        clickOnElement(firstImg,"select first img");
        threadSleep(3000);
        waitForVisibility(continueBtn);
        clickOnElement(continueBtn,"click on continue button");
        longWaitForVisibility(proceedBtn);
        clickOnElement(proceedBtn,"click on proceed button");
    }

    // occupation details

    @AndroidFindBy(xpath = "(//android.widget.Spinner)[1]/preceding-sibling::android.view.View[1]")
    public WebElement sourceOfIncomeDropdown;

    @AndroidFindBy(xpath = "(//android.widget.Spinner)[2]/preceding-sibling::android.view.View[1]")
    public WebElement salaryRangeDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Salaried']")
    public WebElement salariedOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='0 - 5 Lakhs']")
    public WebElement zeroToFiveLakhsOptions;

    public void occupationDetails(){
        waitForVisibility(sourceOfIncomeDropdown);
        threadSleep(3000);
        tap(500,470);
        threadSleep(2000);
        clickOnElement(salariedOption,"click on salaried option");
        threadSleep(2000);
        tap(942,759);
        System.out.println("selected salary range");
        threadSleep(1000);
        clickOnElement(zeroToFiveLakhsOptions,"select 0 - 5 Lakhs");
        threadSleep(2000);
        clickOnElement(proceedBtn,"click on proceed button");
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='English']/preceding-sibling::android.view.View")
    public WebElement englishTxt;

    @AndroidFindBy(xpath = "//android.widget.Button")
    public WebElement iAmReadyBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Join Now']")
    public WebElement joinNowBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Waiting for the Agent']")
    public WebElement waitingForTheAgentTxt;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Video preview']")
    public WebElement videoPreviewTxt;

    public void videoVerification(){
        clickOnElement(getStartedBtn,"click on get started button");
        clickOnElement(englishTxt);
        clickOnElement(iAmReadyBtn);
        clickOnElement(joinNowBtn);
        waitForVisibility(videoPreviewTxt);
        clickOnElement(proceedBtn);
//        clickOnElement(waitingForTheAgentTxt);
        Assert.assertTrue(waitingForTheAgentTxt.isDisplayed(),"waiting for agent text displayed");

    }
}
