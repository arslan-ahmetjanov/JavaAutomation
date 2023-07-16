package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{
    private static final String
    LOGIN_BUTTON = "xpath://a[text()='Log in']",
    LOGIN_BUTTON_FIELD = "xpath://div[@class='drawer-container view-border-box']",
    LOGIN_INPUT = "css:input[name='wpName']",
    PASSWORD_INPUT = "css:input[name='wpPassword']",
    SUBMIT_BUTTON = "css:button#wpLoginAttempt";


    public AuthorizationPageObject(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Click auth button")
    public void clickAuthButton() throws InterruptedException{
        Thread.sleep(1000);
        screenshot(this.takeScreenshot("login_button"));
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot call auth menu", 5);
    }

    @Step("Enter user's login data")
    public void enterLoginData(String login, String password){
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input", 10);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the password input", 5);
    }

    @Step("Submit user's login data form")
    public void submitForm(){
        screenshot(this.takeScreenshot("authorization_page"));
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button", 5);
    }
}
