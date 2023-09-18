package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String NAME_OF_FOLDER = "Learning programming";
    private static final String
            LOGIN = "MobileAppAutomation",
            PASSWORD = "Appium123";

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "MyLists")})
    @DisplayName("Save an article to My List")
    @Description("We save one article to my list")
    @Step("Starting test testSaveFirstArticleToMyList")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToNewMyList(NAME_OF_FOLDER);
            articlePageObject.closeArticle();
        } else {
            articlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(LOGIN, PASSWORD);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle()
            );
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.closeSyncWindow();
            myListsPageObject.openFolderByName(NAME_OF_FOLDER);
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "MyLists")})
    @DisplayName("Save two article to My List")
    @Description("We save two article to my list and delete one of them")
    @Step("Starting test testSaveTwoArticleToMyList")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSaveTwoArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToNewMyList(NAME_OF_FOLDER);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(LOGIN, PASSWORD);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle()
            );
        }

        articlePageObject.closeArticle();

        if (Platform.getInstance().isMW()) {
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Java");
        }

        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToExistedMyList(NAME_OF_FOLDER);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        int amountOfSavedArticleBeforeDeletion = myListsPageObject.getArticleAmountInMyList();

        Assert.assertEquals(
                "We have saved not two article!",
                2,
                amountOfSavedArticleBeforeDeletion);

        myListsPageObject.swipeByArticleToDelete("JavaScript");
        int amountOfSavedArticleAfterDeletion = myListsPageObject.getArticleAmountInMyList();

        Assert.assertEquals(
                "Not one article present after deletion!",
                1,
                amountOfSavedArticleAfterDeletion);

        String titleBeforeOpen = myListsPageObject.getArticleTitleInMyList();
        myListsPageObject.clickOnArticleInMyList();
        String titleAfterOpen = articlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article description have changed after article open",
                titleBeforeOpen,
                titleAfterOpen
        );
    }
}
