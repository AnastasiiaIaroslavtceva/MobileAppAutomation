package lib.ui.android;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath://*[@resource-id='pcs']/*[@class='android.view.View'][1]/*[@class='android.widget.TextView'][1]";
        FOOTER_ELEMENT = "xpath://*[@text='View article in browser']";
        SAVE_BUTTON = "id:org.wikipedia:id/page_save";
        ADD_TO_MY_LIST_BUTTON = "id:org.wikipedia:id/snackbar_action";
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON = "xpath://*[@text = 'OK']";
        MY_LIST_TITLE = "id:org.wikipedia:id/item_title";
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
    }

    public AndroidArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
