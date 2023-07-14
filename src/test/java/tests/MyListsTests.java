package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.MyListsPageObjectFactory;
import lib.ui.factory.NavigationUIFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    private String login = "Arslan100500";
    private String password = "password100500";
    private String name_of_folder = "Saved";
    private String search_line = "Java";
    private String article_title = "Java (programming language)";
    private String article_substring = "Object-oriented programming language";
    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubstring(article_substring);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(article_title);
        articlePageObject.addArticlesToMySaved();
        if (Platform.getInstance().isMW()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement(article_title);

            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isiOS()) {
            searchPageObject.clickCanselSearchButton();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
        {
            myListsPageObject.openFolderByName(name_of_folder);
        } else if (Platform.getInstance().isiOS()) {
            myListsPageObject.clickCanselButton();
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }
}
