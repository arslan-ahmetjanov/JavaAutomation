package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class NavigationUI extends MainPageObject{
    private static final Logger logger = LoggerFactory.getLogger(NavigationUI.class);

    protected static  String
            MY_LISTS_LINK,
            LOG_IN,
            OPEN_NAVIGATION,
            MENU;

    public NavigationUI(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Open navigation menu")
    public void openNavigation(){
        if (Platform.getInstance().isMW()){
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button.", 5);
        } else {
            logger.info("Method openNavigation() does nothing for platform {}", Platform.getInstance().getPlatformVar());
        }
        screenshot(this.takeScreenshot("menu_page"));
    }


    @Step("Open My Lists page")
    public void clickMyLists(){
        this.waitForElementToBeClickable(
                MY_LISTS_LINK,
                "Cannot find navigation button to My List",
                this.defaultTimeoutInSeconds()
        );
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My List",
                20
        );
    }
}
