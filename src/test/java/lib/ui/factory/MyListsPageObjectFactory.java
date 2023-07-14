package lib.ui.factory;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.android.AndroidNavigationUI;
import lib.ui.android.AndroidSearchPageObject;
import lib.ui.ios.iOSMyListsPageObject;
import lib.ui.ios.iOSNavigationUI;
import lib.ui.ios.iOSSearchPageObject;
import lib.ui.mobile_web.MWMyListsPageObject;
import lib.ui.mobile_web.MWSearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListsPageObjectFactory
{
    public static MyListsPageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidMyListsPageObject(driver);
        } else if (Platform.getInstance().isiOS()) {
            return new iOSMyListsPageObject(driver);
        } else {
            return new MWMyListsPageObject(driver);
        }
    }
}

