package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject
{
     static  {
         SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container";
         SEARCH_INPUT = "id:org.wikipedia:id/search_src_text";
         SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@class='android.view.ViewGroup']//*[@text='{SUBSTRING}']";
         SEARCH_CANCEL_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
         SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@class='android.view.ViewGroup']";
         SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results']";
     }

    public AndroidSearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
