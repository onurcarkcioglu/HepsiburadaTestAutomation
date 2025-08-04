package pages;

import helpers.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class SearchResultPage {

    private WebDriver driver;
    private WaitManager waitManager;

    // CSS selectorlar (elementlerin değişme ihtimaline karşı burada tanımlanıyor)
    private final By resultTitle = By.cssSelector("h1"); // Arama sonucu başlığı
    private final By productList = By.cssSelector("[data-test-id='product-card']"); // Ürün kutuları
    private final By filterPanel = By.cssSelector(".filters-wrapper"); // Filtre bölmesi

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver, 500);
        // Arama sonucu sayfasının yüklendiğinden emin olalım:
        waitManager.waitForVisibility(resultTitle);
    }

    // Arama sonucu başlığı görünür mü?
    public boolean isResultTitleVisible() {
        try {
            return driver.findElement(resultTitle).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Ürün sayısı
    public int getNumberOfProducts() {
        return driver.findElements(productList).size();
    }

    // En az 1 ürün var mı?
    public boolean hasProducts() {
        return getNumberOfProducts() > 0;
    }

    // Filtre paneli görünür mü?
    public boolean isFilterPanelVisible() {
        return driver.findElements(filterPanel).size() > 0;
    }

    // Belirli bir markayı filtrele (örnek: Apple, Lenovo)
    public void filterByBrand(String brandName) {
        By brandCheckbox = By.xpath("//div[contains(@class, 'brand')]//label[contains(., '" + brandName + "')]");
        waitManager.waitForVisibility(brandCheckbox);
        WebElement checkbox = driver.findElement(brandCheckbox);

        // Label içindeki input öğesi üzerinden kontrol ve tıklama
        WebElement input = checkbox.findElement(By.tagName("input"));
        if (!input.isSelected()) {
            checkbox.click();
        }

        // Filtre uygulandıktan sonra yeni ürünlerin yüklenmesini bekle
        waitManager.waitForSeconds(3);
    }
}