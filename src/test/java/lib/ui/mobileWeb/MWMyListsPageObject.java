package lib.ui.mobileWeb;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {
    static {
        //FOLDER_BY_NAME_TPL = "xpath://*[@text = '{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class,'watchlist')]//h3[contains(text(),'{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class,'watchlist')]//h3[contains(text(),'{TITLE}')]/../../a[contains(@class,'watched')]";
      //  CLOSE_SYNC_WINDOW_BUTTON = "id:org.wikipedia:id/negativeButton";
        ARTICLE_IN_MY_LIST = "xpath://li[@class='page-summary with-watchstar']";
        ARTICLE_TITLE_IN_MY_LIST = "xpath://h3";
    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
