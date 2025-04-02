package com.qa.pages;

import com.api.VcipLinkDetails;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;


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
        clickOnElement(uploadFile,"click on upload file");
        clickOnElement(firstImg,"select first img");
        threadSleep(3000);
        waitForVisibility(continueBtn);
        clickOnElement(continueBtn,"click on continue button");
        longWaitForVisibility(proceedBtn);
        clickOnElement(proceedBtn,"click on proceed button");
    }

    // occupation details

    @AndroidFindBy(xpath = "(//android.widget.Spinner)[1]/preceding-sibling::android.view.View")
    public WebElement sourceOfIncomeDropdown;

    @AndroidFindBy(xpath = "(//android.widget.Spinner)[2]/preceding-sibling::android.view.View")
    public WebElement salaryRangeDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Salaried\"]")
    public WebElement salariedOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"0 - 5 Lakhs\"]")
    public WebElement zeroToFiveLakhsOptions;

    public void occupationDetails(){
        waitForVisibility(sourceOfIncomeDropdown);
        threadSleep(3000);
        clickOnElement(sourceOfIncomeDropdown);
        threadSleep(2000);
        clickOnElement(sourceOfIncomeDropdown);
        threadSleep(2000);
        clickOnElement(salariedOption,"click on salaried option");
        threadSleep(2000);
        clickOnElement(salaryRangeDropdown);
        threadSleep(1000);
        clickOnElement(salaryRangeDropdown);
        threadSleep(2000);
        clickOnElement(zeroToFiveLakhsOptions,"select 0 - 5 Lakhs");
        threadSleep(2000);
        clickOnElement(proceedBtn,"click on proceed button");
    }

    public void videoVerification(){
        clickOnElement(getStartedBtn,"click on get started button");
        threadSleep(10000);
    }
}
