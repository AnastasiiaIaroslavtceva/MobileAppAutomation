package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Step("Waiting for element present with locator: '{locator}'")
    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    @Step("Waiting for element present with locator: '{locator}'")
    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    @Step("Waiting for element present with locator: '{locator}' and click on it")
    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    @Step("Make sure that element with locator: '{locator}' has text: '{expectedText}'")
    public void assertElementHasText(String locator, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(
                locator,
                "Cannot find element",
                5
        );

        String actualText = element.getAttribute("text");

        Assert.assertEquals(
                errorMessage,
                expectedText,
                actualText
        );
    }

    @Step("Waiting for element present with locator: '{locator}' and type on it: '{value}'")
    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    @Step("Waiting for several elements present with locator: '{locator}'")
    public List<WebElement> waitForSeveralElementsPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, 1)
        );
    }

    @Step("Make sure that any elements with locator: '{locator}' have text: '{expectedText}'")
    public void assertElementsContainText(String locator, String expectedText, String errorMessage) {
        List<WebElement> elements = waitForSeveralElementsPresent(
                locator,
                "Cannot find elements",
                5
        );

        for (WebElement element : elements) {
            String elementTitle = element.getAttribute("text");
            Assert.assertTrue(errorMessage, elementTitle.contains(expectedText));
        }
    }

    @Step("Waiting for element with locator: '{locator}' does not present")
    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    @Step("Waiting for element with locator: '{locator}' and clear")
    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    @Step("Swipe page to up (Method swipeUp() does nothing for Mobile Web)")
    public void swipeUp() {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;

            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);

            action
                    .press(PointOption.point(x, startY))
                    .waitAction()
                    .moveTo(PointOption.point(x, endY))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform: " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Swipe page to up (Method swipeUp() does nothing for Android)")
    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            javascriptExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform: " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Swipe page till element with locator: '{locator}' not visible")
    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        WebElement element = this.waitForElementPresent(locator, errorMessage);

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            alreadySwiped++;

            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed());
            }
        }
    }

    @Step("Swipe page till element with locator: '{locator}' is present on the page")
    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        By by = this.getLocatorByString(locator);

        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }

            swipeUp();
            ++alreadySwiped;
        }
    }

    @Step("Swipe page till element with locator: '{locator}' is present on the page")
    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (!isElementLocatedOnTheScreen(locator)) {

            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, isElementLocatedOnTheScreen(locator));
                return;
            }

            swipeUp();
            ++alreadySwiped;
        }
    }

    @Step("Check is element with locator: '{locator}' located on the page")
    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = waitForElementPresent(locator, "Cannot find element", 1).getLocation().getY();

        if (Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            Object jsResult = javascriptExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    @Step("Swipe element with locator: '{locator}' to left (Method swipeUp() does nothing for Mobile Web)")
    public void swipeElementToLeft(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, 10);
        if (driver instanceof AppiumDriver) {
            int leftX = element.getLocation().getX();
            int rightX = leftX + element.getSize().getWidth();
            int upperY = element.getLocation().getY();
            int lowerY = upperY + element.getSize().getHeight();
            int middleY = (upperY + lowerY) / 2;


            TouchAction action = new TouchAction((AppiumDriver) driver);
            action
                    .press(PointOption.point(rightX, middleY))
                    .waitAction()
                    .moveTo(PointOption.point(leftX, middleY))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform: " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Get Amount of elements with with locator: '{locator}'")
    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    @Step("Check is element with locator: '{locator}' present on the page")
    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    @Step("Clicking on element with locator: '{locator}' several time")
    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {
        int currentAttempts = 0;
        boolean needMoreAttempts = true;

        while (needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage, 1);
                }
            }
            ++currentAttempts;
        }
    }

    @Step("Make sure that element with locator: '{locator}' does not present on the page")
    public void assertElementNotPresent(String locator, String errorMessage) {
        this.waitForElementNotPresent(locator, errorMessage, 5);
    }

    @Step("Make sure that element with locator: '{locator}' present on the page")
    public void assertElementPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);

        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be present! \n";
            throw new AssertionError(defaultMessage + errorMessage);
        }
    }

    @Step("wait for element with locator: '{locator}' and get it '{attribute}'")
    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    @Step("Make screenshot")
    public String takeScreenshot(String name) {
        TakesScreenshot ts = this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";

        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    @Step("Attach screenshot to Allure report")
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }

        return bytes;
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        switch (byType) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id(locator);
            case "css":
                return By.cssSelector(locator);
            default:
                throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
