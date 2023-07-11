package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.MyListsPageObjectFactory;
import lib.ui.factory.NavigationUIFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;

public class HomeWorkTest extends CoreTestCase
{
    private String name_of_folder = "Saved";
    private String search_line = "Java";
    private String article_title1 = "Java (programming language)";
    private String article_substring1 = "Object-oriented programming language";
    private String article_title2 = "JavaScript";
    private String article_substring2 = "High-level programming language";
    @Test
    public void testHomeWork() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubstring(article_substring1);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(article_title1);
        articlePageObject.addArticlesToMySaved();
        articlePageObject.closeArticle();

        searchPageObject.clickByArticleWithSubstring(article_substring2);

        articlePageObject.waitForTitleElement(article_title2);
        articlePageObject.addArticlesToMySaved();
        articlePageObject.closeArticle();

        searchPageObject.clickCanselSearchButton();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        } else {
            myListsPageObject.clickCanselButton();
        }
        myListsPageObject.swipeByArticleToDelete(article_title1);
        myListsPageObject.waitForArticleToDisappearByTitle(article_title1);
        myListsPageObject.waitForArticleToAppearByTitle(article_title2);
        myListsPageObject.openArticleByTitle(article_title2);
        articlePageObject.waitForSubstringElement(article_substring2);
    }
}
