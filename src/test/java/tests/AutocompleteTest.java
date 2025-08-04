//Otomatik tamamlama testi
package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import pages.HomePage;

import java.time.Duration;
import java.util.List;

public class AutocompleteTest extends BaseTest {

    @Test(description = "Popüler ürün önerilerinin otomatik tamamlama sırasında görünürlük testi")
    public void testPopularSuggestionsVisible() {
        HomePage homePage = new HomePage(driver);
        homePage.enterSearchTerm("samsun");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Önce öneri paneli geldi mi kontrol et
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".popularSuggestionProducts-oLCuX6eIPsdVCqruLVkG")));

        // Sonra öneri ürünlerini al
        List<WebElement> suggestions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("[data-test-id^='search-bar-popular-suggestion-product-']")));

        Assert.assertFalse(suggestions.isEmpty(), "Popüler ürün önerileri görünmedi.");
    }


}