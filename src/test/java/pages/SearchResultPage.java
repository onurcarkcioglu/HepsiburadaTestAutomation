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
    By filterLocator = By.id("AllCategories.CategoryId");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver, 500);
        // Arama sonucu sayfasının yüklendiğinden emin olalım:
        waitManager.waitForVisibility(resultTitle);
    }

    public boolean isFilterPanelVisible() {
        WebElement filterElement = driver.findElement(filterLocator);
        return filterElement.isDisplayed();
    }
}