package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{
    private static final String
            SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container",
            SEARCH_INPUT = "id:org.wikipedia:id/search_src_text",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }
    // TEMPLATES METHODS
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    // TEMPLATES METHODS

    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5);
        this.waitForElementPresent(
                SEARCH_INPUT,
                "Cannot find search input after clicking search init element"
        );
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                5
        );
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5
        );
    }

    public void clickCanselSearchButton(){
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }

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

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring
        );
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                5
        );
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElement(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not find any results");
    }
}

