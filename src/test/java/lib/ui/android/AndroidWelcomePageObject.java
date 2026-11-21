package lib.ui.android;

import lib.ui.WelcomePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidWelcomePageObject extends WelcomePageObject
{
    static  {
                SKIP = "xpath://*[@text='Skip']";
    }

    public AndroidWelcomePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
