package lib.ui.mobile_web;

import lib.ui.ListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWListsPageObject extends ListsPageObject {
        static {
        ARTICLE_BY_TITLE_TPL = "xpath://li[@title='{TITLE}']/a[@class='title']/h3[contains(text(),'{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://li[@title='{TITLE}']/a[@aria-controls='mw-watchlink-notification']";
        IMAGE_TITLE = "xpath://li[@title='{TITLE}']/a[@class='title']/div[@class='list-thumb list-thumb-y']";
    }

    public MWListsPageObject(RemoteWebDriver driver)
        {
            super(driver);
        }
}

