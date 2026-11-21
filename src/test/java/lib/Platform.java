package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {
    private static final Logger logger = LoggerFactory.getLogger(Platform.class);
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL_DEFAULT = "http://127.0.0.1:4723/wd/hub";
    private static Platform instance;
    private Platform(){}

    public static Platform getInstance()
    {
        if(instance == null)
        {
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver() throws Exception
    {
        String appiumUrl = Config.get("appium.url", APPIUM_URL_DEFAULT);
        if (this.isAndroid()) {
            logger.info("Creating Android driver session, appium: {}, app: {}", appiumUrl, Config.getPath("android.app", "apks/org.wikipedia.apk"));
            return new AndroidDriver(new URL(appiumUrl), this.getAndroidDesiredCapabilities());
        } else if (this.isiOS()) {
            logger.info("Creating iOS driver session, appium: {}, app: {}", appiumUrl, Config.getPath("ios.app", "apps/Wikipedia.app"));
            return new IOSDriver(new URL(appiumUrl), this.getiOSDesiredCapabilities());
        } else if (this.isMW()) {
            String chromeDriver = Config.getPath("chrome.driver", "chromedriver");
            System.setProperty("webdriver.chrome.driver", chromeDriver);
            logger.info("Creating Chrome driver session, chromedriver: {}", chromeDriver);
            return new ChromeDriver(this.getMWChromeOptions());
        } else {
            throw new IllegalArgumentException(
                    "Unknown platform '" + this.getPlatformVar() + "'. Allowed values: "
                            + PLATFORM_ANDROID + ", " + PLATFORM_IOS + ", " + PLATFORM_MOBILE_WEB
                            + ". Set it with -Dplatform=..., environment variable PLATFORM or key 'platform' in config.properties.");
        }
    }
    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }
    public boolean isiOS()
    {
        return isPlatform(PLATFORM_IOS);
    }
    public boolean isMW()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }
    public String getPlatformVar(){
        return Config.getPlatform();
    }
    private DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName", Config.get("android.device", "AndroidTestDevice"));
        capabilities.setCapability("platformVersion", Config.get("android.version", "8.0"));
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app", Config.getPath("android.app", "apks/org.wikipedia.apk"));
        capabilities.setCapability("automationName","UiAutomator2");
        this.applyResetCapabilities(capabilities);
        return capabilities;
    }
    private DesiredCapabilities getiOSDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName", Config.get("ios.device", "iPhone SE (3rd generation)"));
        capabilities.setCapability("platformVersion", Config.get("ios.version", "16.4"));
        capabilities.setCapability("app", Config.getPath("ios.app", "apps/Wikipedia.app"));
        capabilities.setCapability("automationName","XCUITest");
        this.applyResetCapabilities(capabilities);
        return capabilities;
    }

    private void applyResetCapabilities(DesiredCapabilities capabilities)
    {
        boolean resetApp = Config.getBoolean("reset.app", false);
        capabilities.setCapability("fullReset", resetApp);
        logger.info("fullReset capability: {}", resetApp);
    }

    private ChromeOptions getMWChromeOptions()
    {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) " +
                        "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=360,640");
        return chromeOptions;
    }
}
