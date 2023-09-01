package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientationOnSearchResult() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String descriptionBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String descriptionAfterRotation = articlePageObject.getArticleTitle();

        assertEquals(
                "Article description have changed after screen rotation",
                descriptionBeforeRotation,
                descriptionAfterRotation
        );

        this.rotateScreenPortrait();
        String descriptionAfterSecondRotation = articlePageObject.getArticleTitle();

        assertEquals(
                "Article description have changed after screen rotation",
                descriptionBeforeRotation,
                descriptionAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
