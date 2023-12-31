package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public void assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(
                by,
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

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public List<WebElement> waitForSeveralElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, 1)
        );
    }

    public void assertElementsContainText(By by, String expectedText, String errorMessage) {
        List<WebElement> elements = waitForSeveralElementsPresent(
                by,
                "Cannot find elements",
                5
        );

        for (WebElement element : elements) {
            String elementTitle = element.getAttribute("text");
            Assert.assertTrue(errorMessage, elementTitle.contains(expectedText));
        }
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp() {
        TouchAction action = new TouchAction(driver);
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
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }

            swipeUp();
            ++alreadySwiped;
        }
    }

    public void swipeUpTillElementAppear(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (!isElementLocatedOnTheScreen(by)) {

            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, isElementLocatedOnTheScreen(by));
                return;
            }

            swipeUp();
            ++alreadySwiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(By by) {
        int elementLocationByY = waitForElementPresent(by, "Cannot find element", 1).getLocation().getY();
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;
    }

    public void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(rightX, middleY))
                .waitAction()
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();
    }

    public int getAmountOfElements(By by) {
        this.waitForElementPresent(by, "Cannot find element", 5);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        this.waitForElementNotPresent(by, errorMessage, 5);
    }

    public void assertElementPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);

        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present! \n";
            throw new AssertionError(defaultMessage + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
