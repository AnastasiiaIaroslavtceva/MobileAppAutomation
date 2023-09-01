package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public static final String
            FOLDER_BY_NAME_TPL = "//*[@text = '{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text = '{TITLE}']",
            CLOSE_SYNC_WINDOW_BUTTON = "org.wikipedia:id/negativeButton",
            ARTICLE_IN_MY_LIST = "org.wikipedia:id/page_list_item_container",
            ARTICLE_TITLE_IN_MY_LIST = "org.wikipedia:id/page_list_item_title";

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);
        this.waitForElementAndClick(
                By.xpath(folderNameXpath),
                "Cannot find folder by name " + nameOfFolder,
                5
        );
    }

    public void waitForArticleAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementPresent(
                By.xpath(articleXpath),
                "Cannot fins saved article by title " + articleTitle,
                15
        );
    }

    public void waitForArticleDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementNotPresent(
                By.xpath(articleXpath),
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.swipeElementToLeft(
                By.xpath(articleXpath),
                "Cannot find saved article"
        );
        this.waitForArticleDisappearByTitle(articleTitle);
    }

    public void closeSyncWindow() {
        this.waitForElementAndClick(
                By.id(CLOSE_SYNC_WINDOW_BUTTON),
                "Cannot close sync window",
                5
        );
    }

    public int getArticleAmountInMyList() {
        return this.getAmountOfElements(By.id(ARTICLE_IN_MY_LIST));
    }

    public String getArticleTitleInMyList() {
        return this.waitForElementAndGetAttribute(
                By.id(ARTICLE_TITLE_IN_MY_LIST),
                "text", "Cannot find article title",
                15
        );
    }

    public void clickOnArticleInMyList() {
        this.waitForElementAndClick(
                By.id(ARTICLE_TITLE_IN_MY_LIST),
                "Cannot find saved article",
                10
        );
    }
}
