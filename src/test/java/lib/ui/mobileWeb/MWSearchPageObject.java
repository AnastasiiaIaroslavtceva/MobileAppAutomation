package lib.ui.mobileWeb;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type=search]";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL = "xpath://*[text() = '{TITLE_SUBSTRING}']//following::*[text() = '{DESCRIPTION_SUBSTRING}']/parent::*";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
