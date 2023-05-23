package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;

abstract public class ArticlePageObject extends MainPageObject{
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_NAME_LIST_INPUT,
            MY_LIST_OK_BUTTON,
            MY_SAVED_LIST_BUTTON,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    // TEMPLATES METHODS
    private static String getSavedListXpathByName(String name_of_folder){
        return MY_SAVED_LIST_BUTTON.replace("{NAME_OF_SAVED_LIST}", name_of_folder);
    }
    // TEMPLATES METHODS

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(TITLE,
                "Cannot find article title on page",
                5);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid())
        {
            return title_element.getAttribute("text");
        } else
        {
            return title_element.getAttribute("name");
        }

    }

    public void assertTitlePresent()
    {
        this.assertElementPresent(TITLE, "We supposed find any results");
    }

    public void swipeToFooter()
    {
        if(Platform.getInstance().isAndroid())
        {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        } else
        {
            this.swipeUpTitleElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }

    }

    public void addArticleToNewList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got It' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_NAME_LIST_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_NAME_LIST_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press 'OK' button",
                5
        );
    }

    public void addArticleToOldList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        String folder_name_xpath = getSavedListXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find list by name " + name_of_folder,
                5
        );
    }
    public void addArticlesToMySaved()
    {
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
    }


    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find 'X' link",
                5
        );
    }
}
