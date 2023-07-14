package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{
    protected static  String
            MY_LISTS_LINK,
            LOG_IN,
            OPEN_NAVIGATION,
            MENU;

    public NavigationUI(RemoteWebDriver driver){
        super(driver);
    }

    public void openNavigation(){
        if (Platform.getInstance().isMW()){
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button.", 5);
        } else {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }


    public void clickMyLists() throws InterruptedException{
        Thread.sleep(1000);
//        if (Platform.getInstance().isMW()){
//            this.tryClickElementWithFewAttempts(
//                    MY_LISTS_LINK,
//                    "Cannot find navigation button to My List",
//                    5
//            );
//        }
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My List",
                20
        );
    }
}
