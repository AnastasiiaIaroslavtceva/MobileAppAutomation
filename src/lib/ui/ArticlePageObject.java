package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
            TITLE = "//*[@resource-id='pcs']/*[@class='android.view.View'][1]/*[@class='android.widget.TextView'][1]",
            FOOTER_ELEMENT = "//*[@text='View article in browser']",
            SAVE_BUTTON = "org.wikipedia:id/page_save",
            ADD_TO_MY_LIST_BUTTON = "org.wikipedia:id/snackbar_action",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text = 'OK']",
            MY_LIST_TITLE = "org.wikipedia:id/item_title",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                By.xpath(TITLE),
                "Cannot find article title on page!",
                10
        );
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToNewMyList(String nameOfFolder) {
        this.waitForElementAndClick(
                By.id(SAVE_BUTTON),
                "Cannot find button to save page",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_BUTTON),
                "Cannot find button 'Add to list'",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button'",
                5
        );
    }

    public void addArticleToExistedMyList(String nameOfFolder) {
        this.waitForElementAndClick(
                By.id(SAVE_BUTTON),
                "Cannot find button to save page",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_BUTTON),
                "Cannot find button 'Add to list'",
                5
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_TITLE),
                "Cannot find created folder " + nameOfFolder,
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'View list' button",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article",
                10
        );
    }

    public void assertArticleHasTitle() {
        this.assertElementPresent(
                By.xpath(TITLE),
                "We've not found article title by request: " + TITLE
        );
    }
}
