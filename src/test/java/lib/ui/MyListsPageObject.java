package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_SYNC_WINDOW_BUTTON,
            ARTICLE_IN_MY_LIST,
            ARTICLE_TITLE_IN_MY_LIST,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);
        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder by name " + nameOfFolder,
                5
        );
    }

    public void waitForArticleAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementPresent(
                articleXpath,
                "Cannot fins saved article by title " + articleTitle,
                15
        );
    }

    public void waitForArticleDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementNotPresent(
                articleXpath,
                "Saved article still present with title " + articleTitle,
                15
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        if (Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    articleXpath,
                    "Cannot find saved article"
            );
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementAndClick(removeLocator, "Cannot click button to remove article from saved", 10);
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleDisappearByTitle(articleTitle);
    }

    public void closeSyncWindow() {
        this.waitForElementAndClick(
                CLOSE_SYNC_WINDOW_BUTTON,
                "Cannot close sync window",
                5
        );
    }

    public int getArticleAmountInMyList() {
        return this.getAmountOfElements(ARTICLE_IN_MY_LIST);
    }

    public String getArticleTitleInMyList() {
        if (Platform.getInstance().isAndroid()) {
            return this.waitForElementAndGetAttribute(
                    ARTICLE_TITLE_IN_MY_LIST,
                    "text", "Cannot find article title",
                    15
            );
        } else {
            return this.waitForElementPresent(
                    ARTICLE_IN_MY_LIST,
                    "Cannot find article title",
                    5
            ).getText();
        }
    }

    public void clickOnArticleInMyList() {
        this.waitForElementAndClick(
                ARTICLE_TITLE_IN_MY_LIST,
                "Cannot find saved article",
                10
        );
    }
}
