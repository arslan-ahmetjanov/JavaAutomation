package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import lib.ui.factory.WelcomePageObjectFactory;
import org.junit.Assume;
import org.junit.Test;
@Epic("Tests for welcome page")
public class GetStartedTest extends CoreTestCase
{
    private static final long WELCOME_PAGE_WAIT_TIMEOUT = 3;

    /**
     * Сценарий рассчитан на первый запуск приложения, поэтому welcome-экран не должен быть
     * закрыт в setUp: тест переопределяет {@link #shouldSkipWelcomePage()} и получает экран как есть.
     * Запускать с чистым состоянием приложения: {@code -Dplatform=ios -Dreset.app=true}
     * (reset.app=true включает fullReset для сессии Appium). Если welcome-экрана нет — тест честно скипается.
     */
    @Test
    @Features(value = {@Feature(value = "Welcome Page")})
    @DisplayName("Check Welcome Page")
    @Description("We go through the new user welcome page")
    @Step("Starting test testPassThroughWelcome")
    public void testPassThroughWelcome()
    {
        Assume.assumeFalse("Welcome page is checked on iOS only",
                Platform.getInstance().isAndroid() || Platform.getInstance().isMW());

        WelcomePageObject welcomePageObject = WelcomePageObjectFactory.get(driver);

        Assume.assumeTrue("Welcome page is not displayed: the app has already been onboarded. "
                        + "Run the test with -Dreset.app=true to get a clean app state.",
                welcomePageObject.isLearnMoreLinkPresent(WELCOME_PAGE_WAIT_TIMEOUT));

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForAddOrEditPreferredLangLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedLink();
        welcomePageObject.clickGetStartedButton();
    }

    @Override
    protected boolean shouldSkipWelcomePage()
    {
        return false;
    }
}
