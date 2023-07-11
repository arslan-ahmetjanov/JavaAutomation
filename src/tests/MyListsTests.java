package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.MyListsPageObjectFactory;
import lib.ui.factory.NavigationUIFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    private String name_of_folder = "Saved";
    private String search_line = "Java";
    private String article_title = "Java (programming language)";
    private String article_substring = "Object-oriented programming language";
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubstring(article_substring);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(article_title);
        articlePageObject.addArticlesToMySaved();
        articlePageObject.closeArticle();

        searchPageObject.clickCanselSearchButton();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
        {
            myListsPageObject.openFolderByName(name_of_folder);
        } else
        {
            myListsPageObject.clickCanselButton();
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }
}
