package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_BUTTON,
            REMOVE_FROM_SAVED_BUTTON,
            IMAGE_TITLE;

    // TEMPLATES METHODS
    private static String getFolderByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemovedButtonByTitle(String article_title){
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    private static String getImageArticleByTitle(String article_title){
        return IMAGE_TITLE.replace("{TITLE}", article_title);
    }
    // TEMPLATES METHODS

    public ListsPageObject(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Open folder lists")
    public void openFolderByName(String name_of_folder){
        String folder_name = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    @Step("Open article")
    public void openArticleByTitle(String article_title){
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementAndClick(
                article,
                "Cannot find saved article by title " + article_title,
                15
        );
        screenshot(this.takeScreenshot("article_page"));
    }

    @Step("Open article")
    public void waitImageOfArticle(String article_title){
        String image = getImageArticleByTitle(article_title);
        this.waitForElementPresent(
                image,
                "Cannot find image article by title " + article_title,
                15
        );
    }

    @Step("Wait appear article")
    public void waitForArticleToAppearByTitle(String article_title){
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementPresent(
                article,
                "Cannot find saved article by title " + article_title,
                15
        );
        screenshot(this.takeScreenshot("folder_page_with_any_article"));
    }

    @Step("Wait disappear article")
    public void waitForArticleToDisappearByTitle(String article_title){
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementNotPresent(
                article,
                "Saved article still present by title " + article_title,
                15
        );
        screenshot(this.takeScreenshot("folder_page_without_any_article"));
    }

    @Step("Swipe article to delete")
    public void swipeByArticleToDelete (String article_title) throws InterruptedException{
        this.waitForArticleToAppearByTitle(article_title);
        String article = getSavedArticleByTitle(article_title);
        if (Platform.getInstance().isiOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    article,
                    "Cannot find saved article"
            );
        } else {
            String remove_locator = getRemovedButtonByTitle(article_title);
            this.waitForElementAndClick(remove_locator, "Cannot click button to remove article from saved.", 5);
        }

        if (Platform.getInstance().isiOS())
        {
            this.clickElementToTheRightUpperCorner(article, "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()){
            Thread.sleep(1000);
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    @Step("Click cancel button to close login modal")
    public void clickLoginModalCanselButton(){
        screenshot(this.takeScreenshot("modal"));
        this.waitForElementAndClick(
                CLOSE_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }
}
