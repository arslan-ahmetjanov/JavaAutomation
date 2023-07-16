package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{
    protected static String
            STEP_LEARN_MORE_LINK,
            STEP_NEW_WAYS_TO_EXPLORE_TEXT,
            STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK,
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK,
            NEXT_BUTTON,
            GET_STARTED_BUTTON,
            SKIP;



    public WelcomePageObject(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Waite for 'Learn More' link")
    public void waitForLearnMoreLink()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK,
                "Cannot find 'Learn more about Wikipedia' link", 10);
        screenshot(this.takeScreenshot("learn_more_link"));
    }
    public void waitForNewWaysToExploreText()
    {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT,
                "Cannot find 'New ways to explore' text", 10);
    }
    @Step("Waite for 'Add Or Edit Preferred Lang' link")
    public void waitForAddOrEditPreferredLangLink()
    {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK,
                "Cannot find 'Add or edit preferred languages' link", 10);
        screenshot(this.takeScreenshot("add_or_edit_preferred_lang_link"));
    }
    @Step("Waite for 'Learn More About Data Collected Link' link")
    public void waitForLearnMoreAboutDataCollectedLink()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK,
                "Cannot find 'Learn more about data collected' link", 10);
        screenshot(this.takeScreenshot("learn_more_about_data_collected_link"));
    }

    @Step("Click 'Next' button")
    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_BUTTON,
                "Cannot find 'Next' button", 10);
    }
    @Step("Click 'Get Started' button")
    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON,
                "Cannot find 'Get started' button", 10);
    }

    @Step("Click 'Skip' button")
    public void clickSkipButton()
    {
        this.waitForElementAndClick(SKIP, "Cannot find 'Skip' button", 10);
    }
}
