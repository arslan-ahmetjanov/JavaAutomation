package tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factory.ArticlePageObjectFactory;
import lib.ui.factory.MyListsPageObjectFactory;
import lib.ui.factory.NavigationUIFactory;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

public class ExTests extends CoreTestCase
{
    private MainPageObject mainPageObject;
    protected void setUp() throws Exception{
        super.setUp();

        mainPageObject = new MainPageObject(driver);
    }
    @Test
    public void testEx2()
    {
        mainPageObject.assertElementHasText(
                "xpath://*[contains(@text, 'Search Wikipedia')]",
                "Search Wikipedia",
                "We see unexpected text!"
        );

        mainPageObject.waitForElementAndClick(
                "xpath://*[contains(@text, 'Search Wikipedia')]",
                "Cannot find field 'Search Wikipedia'",
                5
        );

        mainPageObject.assertElementHasText(
                "xpath://*[contains(@text, 'Search…')]",
                "Search…",
                "We see unexpected text!"
        );
    }

    @Test
    public void testEx3()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Island of Indonesia, Southeast Asia");
        searchPageObject.waitForSearchResult("High-level programming language");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCanselSearchButton();
        searchPageObject.clickCanselSearchButton();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testEx5(){
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String title_of_first_article = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning Programming";
        articlePageObject.addArticleToNewList(name_of_folder);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        articlePageObject.waitForTitleElement();
        String title_of_second_article = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToOldList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(title_of_first_article);
        myListsPageObject.waitForArticleToDisappearByTitle(title_of_first_article);
        myListsPageObject.waitForArticleToAppearByTitle(title_of_second_article);
        myListsPageObject.openArticleByTitle(title_of_second_article);

        assertEquals(
                "Cannot find title of second article " + title_of_second_article,
                title_of_second_article,
                articlePageObject.getArticleTitle());
    }

    @Test
    public void testEx6()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.assertTitlePresent();
    }

}
