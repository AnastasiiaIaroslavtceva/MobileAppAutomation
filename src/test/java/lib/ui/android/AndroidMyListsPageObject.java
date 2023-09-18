package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text = '{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text = '{TITLE}']";
        CLOSE_SYNC_WINDOW_BUTTON = "id:org.wikipedia:id/negativeButton";
        ARTICLE_IN_MY_LIST = "id:org.wikipedia:id/page_list_item_container";
        ARTICLE_TITLE_IN_MY_LIST = "id:org.wikipedia:id/page_list_item_title";
    }

    public AndroidMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
