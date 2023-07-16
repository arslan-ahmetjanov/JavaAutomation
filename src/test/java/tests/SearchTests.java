package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factory.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
@Epic("Tests for search")
public class SearchTests extends CoreTestCase
{
    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check search field validation")
    @Description("We search for the article and check the result")
    @Step("Starting test testSearch")
    public void testSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check cancel search button")
    @Description("We point to the search field and click on the cancel search button")
    @Step("Starting test testCancelSearch")
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCanselSearchButton();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check amount of not empty search")
    @Description("We search for the articles and check amount of not empty search")
    @Step("Starting test testAmountOfNotEmptySearch")
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_elements = searchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_elements > 0
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check amount of empty search")
    @Description("We search for the articles and check amount of empty search")
    @Step("Starting test testAmountOfEmptySearch")
    public void testAmountOfEmptySearch(){
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "zxcqwenm";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultLabel();
    }
}
