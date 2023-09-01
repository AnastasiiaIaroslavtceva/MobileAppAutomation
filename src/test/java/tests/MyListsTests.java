package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() {
        String nameOfFolder = "Learning programming";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToNewMyList(nameOfFolder);
        articlePageObject.closeArticle();
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.closeSyncWindow();
        myListsPageObject.openFolderByName(nameOfFolder);
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticleToMyList() {
        String nameOfFolder = "Learning programming";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToNewMyList(nameOfFolder);
        articlePageObject.closeArticle();

        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToExistedMyList(nameOfFolder);

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        int amountOfSavedArticleBeforeDeletion = myListsPageObject.getArticleAmountInMyList();

        assertEquals(
                "We have saved not two article!",
                2,
                amountOfSavedArticleBeforeDeletion);

        myListsPageObject.swipeByArticleToDelete("JavaScript");
        int amountOfSavedArticleAfterDeletion = myListsPageObject.getArticleAmountInMyList();

        assertEquals(
                "Not one article present after deletion!",
                1,
                amountOfSavedArticleAfterDeletion);

        String titleBeforeOpen = myListsPageObject.getArticleTitleInMyList();
        myListsPageObject.clickOnArticleInMyList();
        String titleAfterOpen = articlePageObject.getArticleTitle();

        assertEquals(
                "Article description have changed after article open",
                titleBeforeOpen,
                titleAfterOpen
        );
    }
}
