package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject{
    protected static String
            TITLE,
            SUBSTRING,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_NAME_LIST_INPUT,
            MY_LIST_OK_BUTTON,
            MY_SAVED_LIST_BUTTON,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getTitleByName(String name_of_title){
        return TITLE.replace("{NAME_OF_TITLE}", name_of_title);
    }

    private static String getSubstringByName(String article_title){
        return SUBSTRING.replace("{NAME_OF_SUBSTRING}", article_title);
    }
    // TEMPLATES METHODS

    @Step("Wait for title on the article page")
    public WebElement waitForTitleElement(String name_of_title){
        String title_name = getTitleByName(name_of_title);
        screenshot(this.takeScreenshot("article_title"));
        return this.waitForElementPresent(title_name,
                "Cannot find article title on page",
                5);
    }

    @Step("Wait for substring on the article page")
    public WebElement waitForSubstringElement(String name_of_substring){
        String title_name = getSubstringByName(name_of_substring);
        screenshot(this.takeScreenshot("article_substring"));
        return this.waitForElementPresent(title_name,
                "Cannot find article substring on page",
                5);
    }

    @Step("Swipe to footer on article page")
    public void swipeToFooter()
    {
        if(Platform.getInstance().isAndroid())
        {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    50);
        } else if (Platform.getInstance().isiOS()) {
            this.swipeUpTitleElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    50);
        } else {
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    50);
        }
        screenshot(this.takeScreenshot("article_footer_element"));
    }

    @Step("Add the article to my list")
    public void addArticlesToMySaved () throws InterruptedException
    {
        if (Platform.getInstance().isMW())
        {
            this.removeArticlesFromMySavedIfItAdded();

        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
        Thread.sleep(1000);
        screenshot(this.takeScreenshot("article_added"));
    }

    @Step("Remove the article from saved if it has been added")
    public void removeArticlesFromMySavedIfItAdded()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)){
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click to remove an article from saved",
                    5);
            this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
        }
        screenshot(this.takeScreenshot("article_removed"));
    }

    @Step("Close the article")
    public void closeArticle()
    {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isiOS()){
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find 'X' link",
                    5
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

//    IRRELEVANT:
//    private static String getSavedListXpathByName(String name_of_folder){
//        return MY_SAVED_LIST_BUTTON.replace("{NAME_OF_SAVED_LIST}", name_of_folder);
//    }
//    public String getArticleTitle(){
//        WebElement title_element = waitForTitleElement();
//        if (Platform.getInstance().isAndroid())
//        {
//            return title_element.getAttribute("content-desc");
//        } else
//        {
//            return title_element.getAttribute("name");
//        }
//    }
//    public void addArticleToNewList(String name_of_folder)
//    {
//        this.waitForElementAndClick(
//                OPTIONS_BUTTON,
//                "Cannot find button to open article options",
//                5
//        );
//
//        this.waitForElementAndClick(
//                OPTIONS_ADD_TO_MY_LIST_BUTTON,
//                "Cannot find option to add article to reading list",
//                5
//        );
//
//        this.waitForElementAndClick(
//                ADD_TO_MY_LIST_OVERLAY,
//                "Cannot find 'Got It' tip overlay",
//                5
//        );
//
//        this.waitForElementAndClear(
//                MY_NAME_LIST_INPUT,
//                "Cannot find input to set name of articles folder",
//                5
//        );
//
//        this.waitForElementAndSendKeys(
//                MY_NAME_LIST_INPUT,
//                name_of_folder,
//                "Cannot put text into articles folder input",
//                5
//        );
//
//        this.waitForElementAndClick(
//                MY_LIST_OK_BUTTON,
//                "Cannot press 'OK' button",
//                5
//        );
//    }
//
//    public void addArticleToOldList(String name_of_folder)
//    {
//        this.waitForElementAndClick(
//                OPTIONS_BUTTON,
//                "Cannot find button to open article options",
//                5
//        );
//
//        this.waitForElementAndClick(
//                OPTIONS_ADD_TO_MY_LIST_BUTTON,
//                "Cannot find option to add article to reading list",
//                5
//        );
//
//        String folder_name_xpath = getSavedListXpathByName(name_of_folder);
//        this.waitForElementAndClick(
//                folder_name_xpath,
//                "Cannot find list by name " + name_of_folder,
//                5
//        );
//    }
}
