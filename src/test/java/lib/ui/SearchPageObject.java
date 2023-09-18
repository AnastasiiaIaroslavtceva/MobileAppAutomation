package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescriptionSubstrings(String titleSubstring, String descriptionSubstring) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL
                .replace("{TITLE_SUBSTRING}", titleSubstring)
                .replace("{DESCRIPTION_SUBSTRING}", descriptionSubstring);
    }
    /* TEMPLATE METHODS */

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find search init element",
                5
        );
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5
        );
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button!",
                5
        );
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present!",
                5
        );
    }

    @Step("Clicking button to cancel search result")
    public void clickCancelButton() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }

    @Step("Typing '{searchLine}' to the search line")
    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                searchLine, "Cannot find and type into search input",
                5
        );
    }

    @Step("Waiting for search result")
    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                searchResultXpath,
                "Cannot find search result with substring " + substring
        );
    }

    @Step("Waiting for element by substring in title and description")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String resultSearchElementXpath = getResultSearchElementByTitleAndDescriptionSubstrings(title, description);
        this.waitForElementPresent(
                resultSearchElementXpath,
                "Cannot find search result by title: '" + title + "' and description: '" + description + "'");
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                searchResultXpath,
                "Cannot find and click search result with substring " + substring,
                10)
        ;
    }

    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty result label")
    public void waitForEmptyResultLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    @Step("Making sure there are no results for the search")
    public void assetThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any result"
        );
    }

    @Step("Making sure search input has expected text")
    public void assertSearchInputHasText(String expectedText) {
        this.assertElementHasText(
                SEARCH_INPUT,
                expectedText,
                "We see unexpected text!"
        );
    }

    @Step("Making sure search result titles contain expected text")
    public void assertSearchResultTitlesContainText(String expectedText) {
        this.assertElementsContainText(
                SEARCH_RESULT_ELEMENT,
                expectedText,
                "Article titles don't contains " + expectedText)
        ;
    }
}
