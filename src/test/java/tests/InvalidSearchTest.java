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
        HomePage homePage = new HomePage(driver);

        String invalidSearchTerm = "asdfghqwertyu123456";
        homePage.search2(invalidSearchTerm);

        Assert.assertTrue(homePage.isNoResultSearch(), "Geçersiz arama sonrası 'ürün bulunamadı' mesajı görünmeli.");
    }

    @Test(description = "Boş arama sorgusu gönderildiğinde hiçbir aksiyon alınmamalı")
    public void testEmptySearchDoesNothing() {
        // 1. Başlangıç URL ve title'ı al
        String initialUrl = driver.getCurrentUrl();

        // 2. Boş arama gönder
        homePage.search2("     ");  // sadece boşluk

        // 3. Kısa bir bekleme (herhangi bir yönlendirme oldu mu?)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlToBe(initialUrl)); // sayfa aynı kalmalı

        // 4. Kontroller
        String finalUrl = driver.getCurrentUrl();

        Assert.assertEquals(finalUrl, initialUrl, "Boş arama yönlendirme yapmamalı.");
    }

}