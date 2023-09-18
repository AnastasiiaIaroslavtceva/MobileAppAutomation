package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

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

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public List<WebElement> waitForSeveralElementsPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, 1)
        );
    }

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

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

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

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            javascriptExecutor.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform: " + Platform.getInstance().getPlatformVar());
        }
    }

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

    public boolean isElementLocatedOnTheScreen(String locator) {
        int elementLocationByY = waitForElementPresent(locator, "Cannot find element", 1).getLocation().getY();

        if(Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = driver;
            Object jsResult = javascriptExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

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

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

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

    public void assertElementNotPresent(String locator, String errorMessage) {
        this.waitForElementNotPresent(locator, errorMessage, 5);
    }

    public void assertElementPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);

        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be present! \n";
            throw new AssertionError(defaultMessage + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"),2);
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
