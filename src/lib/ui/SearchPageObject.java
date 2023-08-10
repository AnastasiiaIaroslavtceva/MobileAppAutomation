package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@class = 'android.view.ViewGroup']//*[@text = '{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "org.wikipedia:id/page_list_item_title",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text = 'No results']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL = "//*[@text = '{TITLE_SUBSTRING}']//following-sibling::*[@text = '{DESCRIPTION_SUBSTRING}']/parent::*";

    public SearchPageObject(AppiumDriver driver) {
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

    public void initSearchInput() {
        this.waitForElementPresent(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search init element",
                5
        );
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button!",
                5
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present!",
                5
        );
    }

    public void clickCancelButton() {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button",
                5
        );
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT),
                searchLine, "Cannot find and type into search input",
                5
        );
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                By.xpath(searchResultXpath),
                "Cannot find search result with substring " + substring)
        ;
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String resultSearchElementXpath = getResultSearchElementByTitleAndDescriptionSubstrings(title, description);
        this.waitForElementPresent(
                By.xpath(resultSearchElementXpath),
                "Cannot find search result by title: '" + title + "' and description: '" + description + "'");
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(searchResultXpath),
                "Cannot find and click search result with substring " + substring,
                10)
        ;
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.id(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(By.id(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultLabel() {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15
        );
    }

    public void assetThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                By.id(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any result"
        );
    }

    public void assertSearchInputHasText(String expectedText) {
        this.assertElementHasText(
                By.id(SEARCH_INPUT),
                expectedText,
                "We see unexpected text!"
        );
    }

    public void assertSearchResultTitlesContainText(String expectedText) {
        this.assertElementsContainText(
                By.id(SEARCH_RESULT_ELEMENT),
                expectedText,
                "Article titles don't contains " + expectedText)
        ;
    }
}
