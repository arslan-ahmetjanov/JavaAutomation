package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSWelcomePageObject extends WelcomePageObject
{
    static  {
        STEP_LEARN_MORE_LINK = "xpath://*[@name='Learn more about Wikipedia']";
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "xpath://*[@name='New ways to explore']";
        STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "xpath://*[@name='Add or edit preferred languages']";
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "xpath://*[@name='Learn more about data collected']";
        NEXT_BUTTON = "xpath://*[@name='Next']";
        GET_STARTED_BUTTON = "xpath://*[@name='Get started']";
        SKIP = "xpath://*[@name='Skip']";
    }

    public iOSWelcomePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
