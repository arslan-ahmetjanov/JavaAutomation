package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
    private String search_line = "Java";
    private String article_title = "Java (programming language)";
    private String article_substring = "Object-oriented programming language";
    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
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
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForSearchResult(article_substring);
        this.backgroundApp(2);
        searchPageObject.waitForSearchResult(article_substring);
    }
}
