package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_BUTTON;

    // TEMPLATES METHODS
    private static String getFolderByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    // TEMPLATES METHODS

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }

    public void openFolderByName(String name_of_folder){
        String folder_name = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void openArticleByTitle(String article_title){
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementAndClick(
                article,
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementPresent(
                article,
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article = getSavedArticleByTitle(article_title);
        this.waitForElementNotPresent(
                article,
                "Saved article still present by title " + article_title,
                15
        );
    }

    public void swipeByArticleToDelete(String article_title){
        this.waitForArticleToAppearByTitle(article_title);
        String article = getSavedArticleByTitle(article_title);
        this.swipeElementToLeft(
                article,
                "Cannot find saved article"
        );

        if (Platform.getInstance().isiOS())
        {
            this.clickElementToTheRightUpperCorner(article, "Cannot find saved article");
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void clickCanselButton(){
        this.waitForElementAndClick(
                CLOSE_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }
}
