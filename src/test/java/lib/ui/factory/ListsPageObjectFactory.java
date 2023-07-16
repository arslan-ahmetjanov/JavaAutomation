package lib.ui.factory;

import lib.Platform;
import lib.ui.ListsPageObject;
import lib.ui.android.AndroidListsPageObject;
import lib.ui.ios.iOSListsPageObject;
import lib.ui.mobile_web.MWListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ListsPageObjectFactory
{
    public static ListsPageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidListsPageObject(driver);
        } else if (Platform.getInstance().isiOS()) {
            return new iOSListsPageObject(driver);
        } else {
            return new MWListsPageObject(driver);
        }
    }
}

