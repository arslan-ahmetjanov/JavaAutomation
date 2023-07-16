package lib.ui.android;

import lib.ui.ListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidListsPageObject extends ListsPageObject
{
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title_container']//*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";
    }

    public AndroidListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
