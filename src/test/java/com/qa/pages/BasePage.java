package com.qa.pages;

import com.google.common.collect.ImmutableMap;
import com.qa.utils.CommonUtils;
import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static java.time.Duration.ofSeconds;


public class BasePage {

    public AppiumDriver driver;
    CommonUtils utils = new CommonUtils();

    public BasePage(){
        this.driver = new DriverManager().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        driver.manage().timeouts().implicitlyWait(ofSeconds(10));
    }
    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(CommonUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }
    public void threadSleep(int milliSec){
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void longWaitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(CommonUtils.LongWAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }
    public void waitForVisibility(By e) {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(CommonUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public void clearText(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void clickOnElement(WebElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void clickOnElement1(WebElement element)  {
        WebElement webElement = waitForElement(element);
        webElement.click();
    }

    public void clickOnElement(WebElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.click();
    }

    public void clickOnElement(By e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        driver.findElement(e).click();
    }

    public void typeTextIntoElement(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public void typeTextIntoElement(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.sendKeys(txt);
    }
    public void sendText(WebElement textField, String value) {
        textField.click();
        textField.clear();
        textField.sendKeys(value);
    }

    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getAttribute(By e, String attribute) {
        waitForVisibility(e);
        return driver.findElement(e).getAttribute(attribute);
    }

    public void switchFrame(WebElement ele){
        try {
            driver.switchTo().frame(0);
        }catch (Exception e){
            e.getMessage();
        }
    }
    public String getAttributeValue(WebElement ele,String attribute){
        try{
            return ele.getAttribute(attribute);
        }catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }
    public String getText(WebElement e, String msg) {
        String txt;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + new GlobalParams().getPlatformName());
        }
        utils.log().info(msg + txt);
        return txt;
    }

    public String getText(By e, String msg) {
        String txt = switch (new GlobalParams().getPlatformName()) {
            case "Android" -> getAttribute(e, "text");
            case "iOS" -> getAttribute(e, "label");
            default -> throw new IllegalStateException("Unexpected value: " + new GlobalParams().getPlatformName());
        };
        utils.log().info(msg + txt);
        return txt;
    }
    /**
     * Utility method to check if an element is displayed using explicit wait
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    public boolean isElementDisplayedFor5Sec(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    public void closeApp() {
        if ("Android".equals(new GlobalParams().getPlatformName())) {
            ((InteractsWithApps) driver).terminateApp(driver.getCapabilities().
                    getCapability("appPackage").toString());
        }
    }
    public void removeApp() {
        if ("Android".equals(new GlobalParams().getPlatformName())) {
            ((InteractsWithApps) driver).removeApp(driver.getCapabilities().
                    getCapability("appPackage").toString());
        }
    }
    public void launchApp() {
        if ("Android".equals(new GlobalParams().getPlatformName())) {
            ((InteractsWithApps) driver).activateApp(driver.getCapabilities().
                    getCapability("appPackage").toString());
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebElement andScrollToElementUsingUiScrollable(String childLocAttr, String childLocValue) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector()."+ childLocAttr +"(\"" + childLocValue + "\"));"));
    }
    public WebElement scrollToElementWithText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().text(\"" + text + "\"));"));
    }
    public void scroll() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = size.getHeight() / 2;
        int endX = startX;
        int endY = (int) (size.getHeight() * 0.25);
        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }
    public void scrollUp(){
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int endY = size.getHeight() / 2;
        int endX = startX;
        int startY = (int) (size.getHeight() * 0.25);
        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }
    public void scrollElement(WebElement element) {
        // Get the position and size of the element to determine the scroll target
        Point location = element.getLocation();
        Dimension size = element.getSize();

        int startX = location.getX() + size.getWidth() / 2;
        int startY = location.getY() + size.getHeight() / 2;
        int endX = startX;
        int endY = startY - (int) (size.getHeight() * 0.5); // Adjust this value as needed for scroll length

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(500))) // Increased pause for stability
                .addAction(finger1.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }
    public void swipe(){
        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left",100,
                "top",100,
                "width",100,
                "height",200,
                "direction", "up",
                "percent",0.75
        ));
    }

    //Direction -> up,down,left and right
    public void swipeAction(WebElement ele, String direction){
        ((JavascriptExecutor)driver).executeScript("mobile: swipeGesture",ImmutableMap.of(
                "elementId",((RemoteWebElement)ele).getId(),
                "direction",direction,
                "percent",0.77
        ));
    }
    public void swipeLeftOnElement(WebElement element) {
        int startX = element.getRect().getX() + (int) (element.getSize().getWidth() * 0.9);
        int endX = element.getRect().getX() + (int) (element.getSize().getWidth() * 0.1);
        int startY = element.getRect().getY() + (element.getSize().getHeight() / 2);

        driver.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left", startX,
                "top", startY,
                "width", startX-endX,
                "height", element.getSize().getHeight(),
                "direction", "left",
                "percent", 1
        ));
        //endx-startx;
    }
    /*    public void swipeLeftOnMenuItem(String menuItemText) {
            // Locate the menu item element by its text
            AppiumDriver menuItem = driver.findElement(By.xpath("//android.widget.TextView[@text='" + menuItemText + "']");

            // Get the location and size of the element for swipe action
            int startX = menuItem.getLocation().getX() + menuItem.getSize().getWidth();
            int endX = menuItem.getLocation().getX();
            int centerY = menuItem.getLocation().getY() + (menuItem.getSize().getHeight() / 2);

            // Create a swipe action using W3C Action API
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), startX, centerY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(750),
                    PointerInput.Origin.viewport(), endX, centerY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));
        }
    }*/
    public void fingerClick(){
        int x = 100;
        int y = 200;
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }
    public void fingerClickAndHold(long holdDurationMillis){
        int x = 100;
        int y = 200;
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger,Duration.ofMillis(holdDurationMillis)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }
    public void tap(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    public void tapAtPercentOfElement(WebElement element, double percent) {
        if (percent < 0 || percent > 1) {
            throw new IllegalArgumentException("Percentage must be between 0 and 1");
        }

        // Use JavaScript to perform the tap action
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Object> args = new HashMap<>();

        args.put("element", element);
        args.put("x", 0.25 * 100);  // representing 25% horizontally
        args.put("y", 50); // representing the middle of the element vertically

        js.executeScript("mobile: tap", args);
    }

    public WebElement waitForVisibilityOfElement(WebElement element) {

        WebElement webElement = null;

        try {
            WebDriverWait wait = new WebDriverWait(driver, ofSeconds(10000));
            webElement = wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return webElement;
    }
    public WebElement waitForElement(WebElement element) {
        WebElement webElement = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, ofSeconds(15));
            webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return webElement;
    }
    public WebElement scrollToElement(WebElement element, String direction) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int endX = (int) (size.width * 0.5);
        int startY = 0;
        int endY = 0;
        boolean isFound = false;

        switch (direction) {
            case "up":
                endY = (int) (size.height * 0.4);
                startY = (int) (size.height * 0.6);
                break;

            case "down":
                endY = (int) (size.height * 0.6);
                startY = (int) (size.height * 0.4);
                break;
        }

        for (int i = 0; i < 3; i++) {
            if (find(element, 1)) {
                isFound = true;
                break;
            } else {
                //  swipe(startX, startY, endX, endY, 1000);
            }
        }
        if(!isFound){
            try {
                throw new Exception("Element not found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return element;
    }

    public By scrollToElement(By element, String direction) throws Exception {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int endX = (int) (size.width * 0.5);
        int startY = 0;
        int endY = 0;
        boolean isFound = false;

        switch (direction) {
            case "up":
                endY = (int) (size.height * 0.4);
                startY = (int) (size.height * 0.6);
                break;

            case "down":
                endY = (int) (size.height * 0.6);
                startY = (int) (size.height * 0.4);
                break;
        }

        for (int i = 0; i < 3; i++) {
            if (find(element, 1)) {
                isFound = true;
                break;
//            } else {
//                swipe(startX, startY, endX, endY, 1000);
            }
        }
        if(!isFound){
            throw new Exception("Element not found");
        }
        return element;
    }

    public boolean find(final WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeout));
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (element.isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean find(final By element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeout));
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver.findElement(element).isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }


    /*    public void swipe(int startX, int startY, int endX, int endY, int millis)
                throws InterruptedException {
            TouchAction t = new TouchAction(driver);
            t.press(point(startX, startY)).waitAction(waitOptions(ofMillis(millis))).moveTo(point(endX, endY)).release()
                    .perform();
        }*/
//Krishan
    public void clickLeftSideOfElement(WebElement element) {
        // Get the element's location and size
        int elementLeftX = element.getLocation().getX();
        int elementY = element.getLocation().getY();
        int elementHeight = element.getSize().getHeight();

        // Calculate the coordinates for the left side
        int leftX = elementLeftX + 10; // Custom offset to click near the left edge
        int centerY = elementY + (elementHeight / 2);

        // Using the W3C Actions API to perform a tap action
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), leftX, centerY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(tap));
    }
    public void clickRightSideOfElement(WebElement element) {
        // Get the element's location and size
        int elementRightX = element.getLocation().getX() + element.getSize().getWidth();
        int elementY = element.getLocation().getY();
        int elementHeight = element.getSize().getHeight();

        // Calculate the coordinates for the right side
        int rightX = elementRightX - 10; // Custom offset to click near the right edge
        int centerY = elementY + (elementHeight / 2);

        // Using the W3C Actions API to perform a tap action
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), rightX, centerY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.RIGHT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.RIGHT.asArg()));

        driver.perform(Arrays.asList(tap));
    }

    public void swipeLeft(WebElement element) {
        // Get the element's dimensions and coordinates
        int startX = element.getLocation().getX() + (int) (element.getSize().width * 0.8); // 80% from the left
        int endX = element.getLocation().getX() + (int) (element.getSize().width * 0.2);  // 20% from the left
        int y = element.getLocation().getY() + (element.getSize().height / 2); // Center Y of the element

        // Define a PointerInput for gestures
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        // Move to start position
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, y));

        // Press down
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // Move to end position
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, y));

        // Release
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the swipe
        driver.perform(Arrays.asList(swipe));
    }

    public void swipeUp(WebElement element) {
        // Get the element's coordinates and dimensions
        int startX = element.getLocation().getX() + (element.getSize().width / 2); // Center X of the element
        int startY = element.getLocation().getY() + (int) (element.getSize().height * 0.8); // Start at 80% of the element's height
        int endY = element.getLocation().getY() + (int) (element.getSize().height * 0.2); // End at 20% of the element's height

        // Define a PointerInput for gestures
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        // Move to start position
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));

        // Press down
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // Move to end position
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY));

        // Release
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the swipe
        driver.perform(Arrays.asList(swipe));
    }
}
