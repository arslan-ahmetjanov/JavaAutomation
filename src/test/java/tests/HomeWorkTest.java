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
@Epic("Homework test")
public class HomeWorkTest extends CoreTestCase
{
    private String login = "Arslan100500";
    private String password = "password100500";
    private String name_of_folder = "Saved";
    private String search_line = "Java";
    private String first_article_title = "Java (programming language)";
    private String first_article_substring = "Object-oriented programming language";
    private String second_article_title = "JavaScript";
    private String second_article_substring = "High-level programming language";
    @Test
    @Features(value = {@Feature(value = "Lists")})
    @DisplayName("This is a homework test")
    @Description("We add the two articles to the list. Then delete first article")
    @Step("Starting test testHomeWork")
    public void testHomeWork() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubstring(first_article_substring);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(first_article_title);
        articlePageObject.addArticlesToMySaved();
        if (Platform.getInstance().isMW()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement(first_article_title);

            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        if (Platform.getInstance().isMW()){
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine(search_line);
        }
        searchPageObject.clickByArticleWithSubstring(second_article_substring);

        articlePageObject.waitForTitleElement(second_article_title);
        articlePageObject.addArticlesToMySaved();
        articlePageObject.closeArticle();

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isiOS()) {
            searchPageObject.clickCanselSearchButton();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        ListsPageObject listsPageObject = ListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            listsPageObject.openFolderByName(name_of_folder);
        } else if (Platform.getInstance().isiOS()) {
            listsPageObject.clickLoginModalCanselButton();
        }
        listsPageObject.swipeByArticleToDelete(first_article_title);
        listsPageObject.waitForArticleToAppearByTitle(second_article_title);
        if (Platform.getInstance().isiOS() || Platform.getInstance().isAndroid()){
            listsPageObject.openArticleByTitle(second_article_title);
            articlePageObject.waitForSubstringElement(second_article_substring);
        } else {
            listsPageObject.waitImageOfArticle(second_article_title);
        }
    }
}
