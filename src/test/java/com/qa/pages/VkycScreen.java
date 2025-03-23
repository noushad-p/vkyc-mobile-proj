package com.qa.pages;

import com.api.VcipLinkDetails;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;


public class VkycScreen extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.EditText")
    public WebElement urlTxtField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Invoke SDK\"]")
    public WebElement moveToSDK_Btn;

    public void tapOnMoveToSdkBtn(){
        clickOnElement(urlTxtField,"Tap on sdk field");
        clearText(urlTxtField);
        typeTextIntoElement(urlTxtField, VcipLinkDetails.vcipId);
        clickOnElement(moveToSDK_Btn,"Tap on move to SDK Button");
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Complete\"]")
    public WebElement completeYourVKYCText;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Get Started\"]")
    public WebElement getStartedBtn;
}
