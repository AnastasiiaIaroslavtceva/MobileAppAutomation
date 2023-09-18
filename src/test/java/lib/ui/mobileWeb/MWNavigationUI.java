package lib.ui.mobileWeb;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "css:a[data-event-name='menu.watchlist']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
    }

    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
