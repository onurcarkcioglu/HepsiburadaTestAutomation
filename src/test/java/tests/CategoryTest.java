package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;

public class CategoryTest extends BaseTest {

    @Test(description = "Kategori sayfası başlığı doğru görünmeli")
    public void testCategoryTitleVisible() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.openCategory("laptop-notebook-dizustu-bilgisayarlar-c-98");

        String title = categoryPage.getCategoryTitle();
        Assert.assertTrue(title.contains("Laptop Modelleri"),
                "Kategori başlığı beklenenden farklı. Gerçek başlık: " + title);
    }

    @Test(description = "Kategori altında ürünler listelenmeli")
    public void testCategoryHasProducts() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.openCategory("bilgisayarlar-c-3000500");
        Assert.assertTrue(categoryPage.hasProducts(), "Kategori altında ürün bulunamadı.");
    }

    @Test(description = "Breadcrumb (navigasyon) görünmeli")
    public void testBreadcrumbVisible() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.openCategory("aksesuar"); // Kategoriyi açıyoruz
        Assert.assertTrue(categoryPage.isBreadcrumbVisible(), "Breadcrumb görünmüyor.");
    }

    @Test(description = "Fiyata göre artan sıralama seçildiğinde doğru şekilde uygulanmalı")
    public void testSortByPriceLowToHigh() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.openCategory("yazicilar-c-3013118");

        categoryPage.selectSortingLowToHigh();

        String selectedText = categoryPage.getSelectedSortingText();
        Assert.assertTrue(selectedText.contains("En düşük fiyat"), "Sıralama seçimi doğru değil.");
    }
}