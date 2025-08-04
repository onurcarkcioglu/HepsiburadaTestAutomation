//Sınır durumları testi
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class InvalidInputSearchTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }


    @Test(description = "Anlamsız, çok uzun bir karakter dizisiyle yapılan aramada sistemin doğru şekilde 'sonuç bulunamadı' mesajı göstermesi beklenir")
    public void testExcessivelyLongSearchWithVerbigeration() {
        String longSearchTerm = "a".repeat(1000);

        homePage.search2(longSearchTerm);

        Assert.assertTrue(homePage.isNoResultSearch(), "Çok uzun ve geçersiz bir sorgu sonucunda 'ürün bulunamadı' mesajının görüntülenip görüntülenmediği kontrol edilir.");
    }

    @Test(description = "Anlamlı kelimenin tekrar edilmesiyle oluşturulan uzun arama sorgusunun, geçerli sonuçlar döndürdüğü doğrulanır")
    public void testExcessivelyLongSearchWithSignificantWord() {
        String longSearchTerm = "kalem ".repeat(50);

        homePage.search2(longSearchTerm);

        Assert.assertTrue(homePage.hasProducts(), "Sistem, 'kalem' gibi geçerli bir kelimenin 50 kez tekrarıyla oluşturulan uzun sorguya karşılık ürün listelemeye devam etmelidir.");
    }

    @Test(description = "Özel karakterler içeren geçersiz arama sorgusu")
    public void testSpecialCharactersSearch() {
        homePage.search2("!@#$%^&*()");

        Assert.assertTrue(homePage.isNoResultSearch(), "Özel karakter arama testi geçerli değil.");
    }

    @Test(description = "SQL Injection tarzı zararlı arama sorgusu test edilir")
    public void testSqlInjectionSearch() {
        homePage.searchWithSqlInjection("' OR '1'='1");

        Assert.assertTrue(homePage.isConnectionError(("OR")), "SQL Injection benzeri girişlerde sayfa çökmemeli.");
    }

    @Test(description = "HTML/JS injection testi")
    public void testHtmlInjectionSearch() {
        homePage.searchWithSqlInjection("<script>alert('xss')</script>");

        Assert.assertTrue(homePage.isConnectionError("script"), "Güvenlik uyarısı verilmeli.");
    }
}