//Geçersiz arama testi
package tests;

import base.BaseTest;
import helpers.WaitManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;
import java.util.Locale;

public class InvalidSearchTest extends BaseTest {

    HomePage homePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }

    @Test(description = "Geçersiz arama sonucu 'ürün bulunamadı' mesajı kontrol edilir")
    public void testInvalidProductSearch() {
        String invalidSearchTerm = "asdfghqwertyu123456";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.search2(invalidSearchTerm);

        wait.until(ExpectedConditions.urlContains(invalidSearchTerm));

        Assert.assertTrue(homePage.isNoResultSearch(), "Geçersiz arama sonrası 'ürün bulunamadı' mesajı görünmeli.");
    }

    @Test(description = "Boş arama sorgusu gönderildiğinde hiçbir aksiyon alınmamalı")
    public void testEmptySearchDoesNothing() {
        // 1. Başlangıç URL
        String initialUrl = driver.getCurrentUrl();

        homePage.search2("     ");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlToBe(initialUrl)); // sayfa aynı kalmalı

        String finalUrl = driver.getCurrentUrl();

        Assert.assertEquals(finalUrl, initialUrl, "Boş arama yönlendirme yapmamalı.");
    }

}