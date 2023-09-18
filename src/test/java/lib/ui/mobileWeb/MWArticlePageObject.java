package lib.ui.mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1>span";
        FOOTER_ELEMENT = "css: footer";
        ADD_TO_MY_LIST_BUTTON = "css:#page-actions li#page-actions-watch";
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        REMOVE_FROM_MY_LIST_BUTTON ="xpath://*[@title='Remove this page from your watchlist']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
