package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultPage;
import io.qameta.allure.*;

import java.time.Duration;

@Epic("Arama Fonksiyonu")
@Feature("Geçerli Arama Senaryoları")
public class ValidSearchTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }

    @Step("Arama işlemi yapılır: {keyword}")
    private SearchResultPage performValidSearch(String keyword) {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.search2(keyword);
        return new SearchResultPage(driver);
    }

    @Test(description = "Sayfa başlığı arama terimini içermeli")
    @Story("Sayfa başlığı kontrolü")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchTitleContainsKeyword() {
        homePage.search2("laptop");
        Assert.assertTrue(homePage.hasSearchHeader("laptop"), "Sayfa başlığı 'laptop' içermiyor!");
    }

    @Test(description = "Arama sonucu ürünler listelenmeli")
    @Story("Ürün listesi kontrolü")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearchResultsAreDisplayed() {
        homePage.search2("laptop");
        Assert.assertTrue(homePage.hasProducts(), "Arama sonuçları görüntülenmedi!");
    }

    //Bunları yapmalı mıyız?
    @Test(description = "Filtre paneli görünür olmalı")
    @Story("Filtre paneli görünürlüğü")
    @Severity(SeverityLevel.MINOR)
    public void testFilterPanelIsVisible() {
        SearchResultPage resultPage = performValidSearch("laptop");
        Assert.assertTrue(resultPage.isFilterPanelVisible(), "Filtre paneli görünmüyor!");
    }

    //Bunları yapmalı mıyız?
    @Test(description = "Sayfa içeriği arama terimini içermeli")
    @Story("Sayfa içeriği kontrolü")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchTermInPageSource() {
        String searchTerm = "laptop";
        performValidSearch(searchTerm);
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(pageSource.contains(searchTerm), "Sayfa içeriğinde arama terimi bulunamadı!");
    }
}
