package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelButton();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linking Park discography";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We find too few results!",
                amountOfSearchResult > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "zxcvasdfqwer";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assetThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchInputText() {
        String searchInputText = "Search Wikipedia";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.assertSearchInputHasText(searchInputText);
    }

    @Test
    public void testSearchAndCancelSearch() {
        String searchLine = "Sky";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We see less articles than expected!",
                amountOfSearchResult > 1
        );

        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelButton();
        searchPageObject.assetThereIsNoResultOfSearch();
    }

    @Test
    public void testArticleTitlesContent() {
        String searchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.assertSearchResultTitlesContainText(searchLine);
    }

    @Test
    public void testFirstThreeSearchResult() {
        String searchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForElementByTitleAndDescription(searchLine, "Island in Indonesia");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
    }
}
