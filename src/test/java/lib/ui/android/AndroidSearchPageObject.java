package lib.ui.android;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@class = 'android.view.ViewGroup']//*[@text = '{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "id:org.wikipedia:id/page_list_item_title";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text = 'No results']";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_SUBSTRINGS_TPL = "xpath://*[@text = '{TITLE_SUBSTRING}']//following-sibling::*[@text = '{DESCRIPTION_SUBSTRING}']/parent::*";
    }

    public AndroidSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
