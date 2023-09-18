package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    @Feature(value = "Search")
    @DisplayName("Check search")
    @Description("We type in search line and wait for search result")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Cancel search")
    @Description("We type in search line and click on cancel button")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelButton();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Amount of not empty search")
    @Description("We type in search line and make sure that have several search result")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linking Park discography";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = searchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We find too few results!",
                amountOfSearchResult > 0
        );
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Amount of empty search")
    @Description("We type in search line and make sure that have no search result")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {
        String searchLine = "zxcvasdfqwer";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assetThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check search input text")
    @Description("Make sure that search input has expected text")
    @Step("Starting test testSearchInputText")
    @Severity(value = SeverityLevel.MINOR)
    public void testSearchInputText() {
        String searchInputText = "Search Wikipedia";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.assertSearchInputHasText(searchInputText);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Search and cancel search")
    @Description("We type in search line, make sure that have several result, click on cancel button and check that result disappears")
    @Step("Starting test testSearchAndCancelSearch")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchAndCancelSearch() {
        String searchLine = "Sky";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = searchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We see less articles than expected!",
                amountOfSearchResult > 1
        );

        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelButton();
        searchPageObject.assetThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check search result titles")
    @Description("We type in search line and make sure that search result titles contains expected text")
    @Step("Starting test testArticleTitlesContent")
    @Severity(value = SeverityLevel.NORMAL)
    public void testArticleTitlesContent() {
        String searchLine = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.assertSearchResultTitlesContainText(searchLine);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Check first three search result")
    @Description("We type in search line and make sure that first three search result contains expected title and description")
    @Step("Starting test testArticleTitlesContent")
    public void testFirstThreeSearchResult() {
        String searchLine = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForElementByTitleAndDescription(searchLine, "Island in Indonesia");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
    }
}
