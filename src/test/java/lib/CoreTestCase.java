package lib;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lib.ui.MainPageObject;
import lib.ui.WelcomePageObject;
import lib.ui.factory.WelcomePageObjectFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase {

    private static final Logger logger = LoggerFactory.getLogger(CoreTestCase.class);
    private static final String WIKI_URL_DEFAULT = "https://en.m.wikipedia.org";
    private static final long WELCOME_PAGE_CHECK_TIMEOUT = 3;

    protected RemoteWebDriver driver;

    /**
     * JUnit 4 выполняет @After раньше колбэков {@link TestWatcher}, поэтому сессия закрывается здесь:
     * сначала снимается скриншот упавшего теста (сессия ещё жива), затем приложение закрывается.
     */
    @Rule
    public final TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            attachScreenshotOnFailure(description.getMethodName());
        }

        @Override
        protected void finished(Description description) {
            tearDown();
        }
    };

    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception
    {
        driver = Platform.getInstance().getDriver();
        this.createAllurePropertyFile();
        this.rotateScreenPortrait();
        this.skipWelcomePageForApp();
        this.openWikiWebPageForMobileWeb();
    }

    @Step("Remove driver and session")
    public void tearDown()
    {
        if (driver == null) {
            logger.debug("Driver is not initialized, nothing to close");
            return;
        }
        try {
            driver.quit();
        } catch (Exception e) {
            logger.warn("Cannot quit the driver session", e);
        } finally {
            driver = null;
        }
    }

    @Step("Rotate screen to portrait mode")
    protected void rotateScreenPortrait()
    {
        if (driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            logger.info("Method rotateScreenPortrait() does nothing for platform {}", Platform.getInstance().getPlatformVar());
        }

    }

    @Step("Rotate screen to landscape mode")
    protected void rotateScreenLandscape()
    {
        if (driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            logger.info("Method rotateScreenLandscape() does nothing for platform {}", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Send mobile app to background(this method does nothing for Mobile Web)")
    protected void backgroundApp(int seconds)
    {
        if (driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        } else {
            logger.info("Method backgroundApp() does nothing for platform {}", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Open Wiki URL for Mobile Web(this method does nothing for Android and iOS)")
    protected void openWikiWebPageForMobileWeb()
    {
        if (Platform.getInstance().isMW()){
            String wikiUrl = Config.get("wiki.url", WIKI_URL_DEFAULT);
            logger.info("Opening {}", wikiUrl);
            driver.get(wikiUrl);
        } else {
            logger.info("Method openWikiWebPageForMobileWeb() does nothing for platform {}", Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Skip welcome page screen for Android and iOS(this method does nothing for Mobile Web)")
    private void skipWelcomePageForApp()
    {
        if (!Platform.getInstance().isAndroid() && !Platform.getInstance().isiOS())
        {
            logger.info("Method skipWelcomePageForApp() does nothing for platform {}", Platform.getInstance().getPlatformVar());
            return;
        }
        if (!shouldSkipWelcomePage()) {
            logger.info("Welcome page skipping is disabled (reset.app=false), the app is expected to be already onboarded");
            return;
        }
        WelcomePageObject welcomePageObject = WelcomePageObjectFactory.get(driver);
        if (welcomePageObject.isSkipButtonPresent(WELCOME_PAGE_CHECK_TIMEOUT)) {
            welcomePageObject.clickSkipButton();
        } else {
            logger.warn("Welcome page is not displayed although reset.app=true, skip step is ignored");
        }
    }

    /**
     * Пропуск welcome-экрана нужен только вместе со сбросом состояния приложения.
     * Тест welcome-экрана переопределяет метод, чтобы экран остался доступен сценарию.
     */
    protected boolean shouldSkipWelcomePage()
    {
        return Config.getBoolean("reset.app", false);
    }

    private void attachScreenshotOnFailure(String testName)
    {
        File screenshot = MainPageObject.saveScreenshot(driver, "failed_" + testName);
        if (screenshot == null) {
            logger.warn("Screenshot for failed test '{}' was not saved", testName);
            return;
        }
        try (InputStream stream = new FileInputStream(screenshot)) {
            Allure.addAttachment("Screenshot of failed test: " + testName, "image/png", stream, "png");
        } catch (IOException e) {
            logger.warn("Cannot attach screenshot {} to the Allure report", screenshot.getAbsolutePath(), e);
        }
    }

    private void createAllurePropertyFile()
    {
        String path = System.getProperty("allure.results.directory");
        if (path == null || path.trim().isEmpty()) {
            logger.warn("System property allure.results.directory is not set, environment.properties is skipped");
            return;
        }
        File directory = new File(path.trim());
        if (!directory.isDirectory() && !directory.mkdirs()) {
            logger.warn("Cannot create allure results directory {}", directory.getAbsolutePath());
            return;
        }
        Properties properties = new Properties();
        properties.setProperty("Environment", Platform.getInstance().getPlatformVar());
        File propertiesFile = new File(directory, "environment.properties");
        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile)) {
            properties.store(fileOutputStream, "See https://docs.qameta.io/allure/#_environment");
        } catch (IOException e) {
            logger.warn("IO problem when writing allure properties file {}", propertiesFile.getAbsolutePath(), e);
        }
    }
}
