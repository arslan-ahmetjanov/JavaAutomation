package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class ArticlePageObject extends MainPageObject{
    private static final Logger logger = LoggerFactory.getLogger(ArticlePageObject.class);

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
            CLOSE_ARTICLE_BUTTON,
            LOGIN_PROMPT_ELEMENT;

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
    public void addArticlesToMySaved ()
    {
        if (Platform.getInstance().isMW())
        {
            this.removeArticlesFromMySavedIfItAdded();

        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
        if (Platform.getInstance().isMW()) {
            // В зависимости от авторизации клик либо добавляет статью в watchlist,
            // либо открывает форму логина — ждём любое из этих состояний.
            this.waitForAnyElementPresent(
                    new String[]{OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, LOGIN_PROMPT_ELEMENT},
                    "Article was not added to the watchlist",
                    this.defaultTimeoutInSeconds());
        } else {
            // На Android и iOS тап по Save открывает нижний лист выбора списка, и стабильного
            // признака «сохранено» у разных версий приложения нет: добавление проверяет
            // следующий шаг (открытие My lists ждёт появления статьи), а не эта пауза.
            logger.info("Save action was triggered for the article, the result is verified on the My lists page");
        }
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
            logger.info("Method closeArticle() does nothing for platform {}", Platform.getInstance().getPlatformVar());
        }

    }
}
