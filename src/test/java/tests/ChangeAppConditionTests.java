package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    @Feature(value = "App condition")
    @DisplayName("Change screen orientation")
    @Description("We change screen orientation on search result twice")
    @Step("Starting test testChangeScreenOrientationOnSearchResult")
    @Severity(value = SeverityLevel.MINOR)
    public void testChangeScreenOrientationOnSearchResult() {
        if (Platform.getInstance().isMW()) {
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String descriptionBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String descriptionAfterRotation = articlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article description have changed after screen rotation",
                descriptionBeforeRotation,
                descriptionAfterRotation
        );

        this.rotateScreenPortrait();
        String descriptionAfterSecondRotation = articlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article description have changed after screen rotation",
                descriptionBeforeRotation,
                descriptionAfterSecondRotation
        );
    }

    @Test
    @Feature(value = "App condition")
    @DisplayName("Send mobile app to background")
    @Description("We Send mobile app to background and check working after it")
    @Step("Starting test testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCheckSearchArticleInBackground() {
        if (Platform.getInstance().isMW()) {
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
