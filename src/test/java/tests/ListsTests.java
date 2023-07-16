package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.ListsPageObjectFactory;
import lib.ui.factory.NavigationUIFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;

@Epic("Tests for user lists")
public class ListsTests extends CoreTestCase
{
    private String login = "Arslan100500";
    private String password = "password100500";
    private String name_of_folder = "Saved";
    private String search_line = "Java";
    private String article_title = "Java (programming language)";
    private String article_substring = "Object-oriented programming language";
    @Test
    @Features(value = {@Feature(value = "Lists"), @Feature(value="Search"), @Feature(value = "Article")})
    @DisplayName("Save First Article To List")
    @Description("We add the first article to the list. Then delete it")
    @Step("Starting test testSaveFirstArticleToList")
    public void testSaveFirstArticleToList() throws InterruptedException {
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

        ListsPageObject listsPageObject = ListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid())
        {
            listsPageObject.openFolderByName(name_of_folder);
        } else if (Platform.getInstance().isiOS()) {
            listsPageObject.clickLoginModalCanselButton();
        }
        listsPageObject.swipeByArticleToDelete(article_title);
    }
}
