import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\Yaroslavtsevi\\IdeaProjects\\MobileAppAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Before
    public void skipOnboarding() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                5
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSearchInputText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "We see unexpected text!"
        );
    }

    @Test
    public void testSearchAndCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Sky",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForSeveralElementsPresent(
                By.xpath("//*[@class = 'android.view.ViewGroup']"),
                "We see less articles than expected!",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X' to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Articles is steel present on the page",
                5
        );
    }

    @Test
    public void testArticleTitlesContent() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        List<WebElement> titleElements = waitForSeveralElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find article titles",
                15
        );

        for (WebElement titleElement : titleElements) {
            String articleTitle = titleElement.getAttribute("text");
            Assert.assertTrue("Article title don't contains 'Java'", articleTitle.contains("Java"));
        }
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Appium",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Automation for Apps']"),
                "Cannot find 'Appium' article",
                5
        );

        waitForElementPresent(
                By.id("pcs-edit-section-title-description"),
                "Cannot find article title description",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View article in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Java' article",
                5
        );

        waitForElementPresent(
                By.id("pcs-edit-section-title-description"),
                "Cannot find article title description",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find button to save page",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button 'Add to list'",
                5
        );

        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'OK']"),
                "Cannot press OK button'",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close articles list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/nav_tab_reading_lists"),
                "Cannot find navigation button to my list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/negativeButton"),
                "Cannot close sync window",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text = '" + nameOfFolder + "']"),
                "Cannot find created folder",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text = 'Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text = 'Java (programming language)']"),
                "Cannot delete article",
                10
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Linking Park discography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchResultLocator = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        waitForElementPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request " + searchLine,
                15
        );

        int amountOfSearchResult = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        Assert.assertTrue(
                "We find too few results!",
                amountOfSearchResult > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "zxcvasdfqwer";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchResultLocator = "//*[@resource-id = 'org.wikipedia:id/search_results_list']//*[@resource-id = 'org.wikipedia:id/page_list_item_title']";
        String emptyResultLabel = "//*[@text = 'No results']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by the request " + searchLine,
                15
        );

        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "We've found some result by request" + searchLine
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResult() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + searchLine,
                15
        );

        String descriptionBeforeRotation = waitForElementAndGetAttribute(
                By.id("pcs-edit-section-title-description"),
                "text",
                "Cannot find article description",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String descriptionAfterRotation = waitForElementAndGetAttribute(
                By.id("pcs-edit-section-title-description"),
                "text",
                "Cannot find article description",
                15
        );

        Assert.assertEquals(
                "Article description have changed after screen rotation",
                descriptionBeforeRotation,
                descriptionAfterRotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String descriptionAfterSecondRotation = waitForElementAndGetAttribute(
                By.id("pcs-edit-section-title-description"),
                "text",
                "Cannot find article description",
                15
        );

        Assert.assertEquals(
                "Article description have changed after screen rotation",
                descriptionBeforeRotation,
                descriptionAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Java' article",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Java' article after returning from background",
                5
        );
    }

    @Test
    public void testSaveTwoArticleToMyList() {
        String searchPlaceholder = "Search Wikipedia";
        String nameOfFolder = "Learning programming";
        String articleDescriptionOnArticlePageLocator = "pcs-edit-section-title-description";
        String saveButtonLocator = "org.wikipedia:id/page_save";
        String actionButtonOnPopupLocator = "org.wikipedia:id/snackbar_action";
        String savedArticleLocator = "org.wikipedia:id/page_list_item_container";
        String articleDescriptionOnMyListLocator = "org.wikipedia:id/page_list_item_description";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, '" + searchPlaceholder + "')]"),
                "Cannot find '" + searchPlaceholder + "' input on Explore page",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find '" + searchPlaceholder + "' input on Search page",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'High-level programming language']"),
                "Cannot find 'JavaScript' article",
                5
        );

        waitForElementPresent(
                By.id(articleDescriptionOnArticlePageLocator),
                "Cannot find article description",
                15
        );

        waitForElementAndClick(
                By.id(saveButtonLocator),
                "Cannot find button to save article",
                5
        );

        waitForElementAndClick(
                By.id(actionButtonOnPopupLocator),
                "Cannot find button 'Add to list'",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'OK']"),
                "Cannot press OK button'",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@class = 'android.view.ViewGroup']//*[@text = 'Object-oriented programming language']"),
                "Cannot find 'Java' article",
                5
        );

        waitForElementPresent(
                By.id(articleDescriptionOnArticlePageLocator),
                "Cannot find article title description",
                15
        );

        waitForElementAndClick(
                By.id(saveButtonLocator),
                "Cannot find button to save page",
                5
        );

        waitForElementAndClick(
                By.id(actionButtonOnPopupLocator),
                "Cannot find button 'Add to list'",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/item_title"),
                "Cannot find created folder " + nameOfFolder,
                5
        );

        waitForElementAndClick(
                By.id(actionButtonOnPopupLocator),
                "Cannot find 'View list' button",
                5
        );

        int amountOfSavedArticleBeforeDeletion = getAmountOfElements(
                By.id(savedArticleLocator)
        );

        Assert.assertEquals(
                "We have saved not two article!",
                2,
                amountOfSavedArticleBeforeDeletion);

        swipeElementToLeft(
                By.xpath("//*[@text = 'JavaScript']"),
                "Cannot find saved article"
        );

        int amountOfSavedArticleAfterDeletion = getAmountOfElements(
                By.id(savedArticleLocator)
        );

        Assert.assertEquals(
                "Not one article present after deletion!",
                1,
                amountOfSavedArticleAfterDeletion);

        String descriptionBeforeOpen = waitForElementAndGetAttribute(
                By.id(articleDescriptionOnMyListLocator),
                "text",
                "Cannot find article description",
                15
        );

        waitForElementAndClick(
                By.id(articleDescriptionOnMyListLocator),
                "Cannot find saved article",
                10
        );

        String descriptionAfterOpen = waitForElementAndGetAttribute(
                By.id(articleDescriptionOnArticlePageLocator),
                "text",
                "Cannot find article description",
                15
        );

        Assert.assertEquals(
                "Article description have changed after article open",
                descriptionBeforeOpen,
                descriptionAfterOpen
        );
    }

    @Test
    public void testArticleTitlePresence() {
        String searchPlaceholder = "Search Wikipedia";
        String articleTitleLocator = "//*[@resource-id = 'pcs']//*[@text = 'Java (programming language)']";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, '" + searchPlaceholder + "')]"),
                "Cannot find '" + searchPlaceholder + "' input on Explore page",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find '" + searchPlaceholder + "' input on Search page",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Java (programming language)']"),
                "Cannot find 'Java' article",
                5
        );

        assertElementPresent(
                By.xpath(articleTitleLocator),
                "We've not found article title by request: " + articleTitleLocator
        );
    }


    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private void assertElementHasText(By by, String expectedText, String errorMessage) {
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

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private List<WebElement> waitForSeveralElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(by, 1)
        );
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action
                .press(x, startY)
                .waitAction(timeOfSwipe)
                .moveTo(x, endY)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    protected void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(300)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);

        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private void assertElementPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);

        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present! \n";
            throw new AssertionError(defaultMessage + errorMessage);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
