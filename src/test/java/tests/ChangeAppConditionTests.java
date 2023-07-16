package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;
@Epic("Tests for change app conditions")
public class ChangeAppConditionTests extends CoreTestCase
{
    private String search_line = "Java";
    private String article_title = "Java (programming language)";
    private String article_substring = "Object-oriented programming language";
    @Test
    @Features(value = {@Feature(value = "App Conditions"), @Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Change screen orientation on search results page")
    @Description("We search articles and change screen orientation: Portrait -> LandScape -> Portrait")
    @Step("Starting test testChangeScreenOrientationOnSearchResults")
    public void testChangeScreenOrientationOnSearchResults()
    {
        if (Platform.getInstance().isMW()){
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubstring(article_substring);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement(article_title);

        this.rotateScreenLandscape();
        articlePageObject.waitForTitleElement(article_title);

        this.rotateScreenPortrait();
        articlePageObject.waitForTitleElement(article_title);
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check search page after background state")
    @Description("We search articles and switch to background: App -> Background -> App")
    @Step("Starting test testCheckSearchArticleInBackground")
    public void testCheckSearchArticleInBackground()
    {
        if (Platform.getInstance().isMW()){
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForSearchResult(article_substring);
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult(article_substring);
    }
}
