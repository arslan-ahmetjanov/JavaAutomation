package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;
@Epic("Tests for welcome page")
public class GetStartedTest extends CoreTestCase
{
    @Test
    @Features(value = {@Feature(value = "Welcome Page")})
    @DisplayName("Check Welcome Page")
    @Description("We go through the new user welcome page")
    @Step("Starting test testPassThroughWelcome")
    public void testPassThroughWelcome()
    {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){
            return;
    }

        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLangLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedLink();
        welcomePageObject.clickGetStartedButton();
    }
}
