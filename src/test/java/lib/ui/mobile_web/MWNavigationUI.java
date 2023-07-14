package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "xpath://a[@data-event-name='menu.watchlist']";
        LOG_IN = "xpath://a[@data-event-name='menu.login']";
        OPEN_NAVIGATION = "css:#mw-mf-main-menu-button";
        MENU = "xpath://input[@aria-expanded='true']";
    }

    public MWNavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }
}
