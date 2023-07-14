package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject
{
        static {
            TITLE = "xpath://android.view.View[@content-desc='{NAME_OF_TITLE}']";
            SUBSTRING = "xpath://android.view.View[@content-desc='{NAME_OF_SUBSTRING}']";
            FOOTER_ELEMENT = "xpath://*[@content-desc='View article in browser']";
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://android.widget.TextView[@content-desc='Save']";
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        }

    public AndroidArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
