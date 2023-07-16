package lib.ui.ios;

import lib.ui.ListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSListsPageObject extends ListsPageObject
{
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']";
        CLOSE_BUTTON = "id:Close";
    }

    public iOSListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
