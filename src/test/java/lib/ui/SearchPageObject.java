package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject{
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver){
        super(driver);
    }
    // TEMPLATES METHODS
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    // TEMPLATES METHODS

    @Step("Initialization the search field")
    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5);
        this.waitForElementPresent(
                SEARCH_INPUT,
                "Cannot find search input after clicking search init element"
        );
        screenshot(this.takeScreenshot("search_page"));
    }

    @Step("Waite for button to cancel search result")
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                5
        );
    }

    @Step("Waite for search cancel button to disappear")
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5
        );
        screenshot(this.takeScreenshot("main_page"));
    }

    @Step("Click button to cancel search result")
    public void clickCanselSearchButton(){
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }

    @Step("Type '{search_Line}' to the search line")
    public void typeSearchLine(String search_Line){
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_Line,
                "Cannot find and type into search input",
                5
        );
        this.assertElementHasText(
                SEARCH_INPUT,
                search_Line,
                "Entered value is not displayed"
        );
    }

    @Step("Waite for search line")
    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring
        );
        screenshot(this.takeScreenshot("search_result_page"));
    }

    @Step("Waite for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                5
        );
    }

    @Step("Waite for search result and select an article by substring in article title")
    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        screenshot(this.takeScreenshot("search_page_with_any_result"));
        return this.getAmountOfElement(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waite for empty result label")
    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
        screenshot(this.takeScreenshot("search_page_with_empty_result"));
    }
}

