package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            SAVE_BUTTON,
            ADD_TO_MY_LIST_BUTTON,
            REMOVE_FROM_MY_LIST_BUTTON,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            MY_LIST_TITLE,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Waiting for title on the article page")
    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page!",
                10
        );
    }

    @Step("Get article title")
    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        screenshot(this.takeScreenshot("articleTitle"));
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else {
            return titleElement.getText();
        }
    }

    @Step("Swiping to footer on article page")
    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
    }

    @Step("Adding the article to new My list with name '{nameOfFolder}'")
    public void addArticleToNewMyList(String nameOfFolder) {
        this.waitForElementAndClick(
                SAVE_BUTTON,
                "Cannot find button to save page",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "Cannot find button 'Add to list'",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button'",
                5
        );
    }

    @Step("Adding the article to existed My list with name '{nameOfFolder}'")
    public void addArticleToExistedMyList(String nameOfFolder) {
        this.waitForElementAndClick(
                SAVE_BUTTON,
                "Cannot find button to save page",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "Cannot find button 'Add to list'",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_TITLE,
                "Cannot find created folder " + nameOfFolder,
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_BUTTON,
                "Cannot find 'View list' button",
                5
        );
    }

    @Step("Closing the article")
    public void closeArticle() {
        if (Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article",
                    10
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform: " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Make sure the article has title")
    public void assertArticleHasTitle() {
        this.assertElementPresent(
                TITLE,
                "We've not found article title by request: " + TITLE
        );
    }

    @Step("Adding the article to my saved articles")
    public void addArticleToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }

        this.waitForElementAndClick(ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
    }

    @Step("Removing the article from saved if it has been added")
    public void removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove article from saved",
                    1
            );
        }
    }
}
