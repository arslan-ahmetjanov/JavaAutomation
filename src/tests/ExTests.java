package tests;

import lib.CoreTestCase;
import lib.ui.*;
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
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Search Wikipedia",
                "We see unexpected text!"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find field 'Search Wikipedia'",
                5
        );

        mainPageObject.assertElementHasText(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Search…",
                "We see unexpected text!"
        );
    }

    @Test
    public void testEx3()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
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
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
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

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(title_of_first_article);
        myListsPageObject.waitForArticleToDisappearByTitle(title_of_first_article);
        myListsPageObject.waitForArticleToAppearByTitle(title_of_second_article);
    }

    @Test
    public void testEx6()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.assertTitlePresent();
    }

}
