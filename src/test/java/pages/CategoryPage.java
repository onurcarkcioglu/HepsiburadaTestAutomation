package pages;

import helpers.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CategoryPage {

    private WebDriver driver;
    private WaitManager waitManager;

    private final By categoryTitle = By.cssSelector("h1[data-test-id='header-h1']");
    private final By productCards = By.cssSelector("li[id^='i']");
    private final By breadcrumbLastItem = By.cssSelector("[data-test-id='breadcrumb-last-item']");
    private final By sortingTitle = By.cssSelector("span[class*='horizontalSortingBar'][class*='o9D1Edf']");
    private final By priceLowToHigh = By.xpath("//div[contains(text(),'En düşük fiyat')]/ancestor::label");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver, 500);
    }

    public void openCategory(String categorySlug) {
        driver.get("https://www.hepsiburada.com/" + categorySlug);
        waitManager.waitForVisibility(categoryTitle);
    }

    public String getCategoryTitle() {
        waitManager.waitForVisibility(categoryTitle);
        return driver.findElement(categoryTitle).getText();
    }

    private int getProductCount() {
        waitManager.waitForVisibility(productCards);
        return driver.findElements(productCards).size();
    }

    public boolean hasProducts() {
        return getProductCount() > 0;
    }

    public boolean isBreadcrumbVisible() {
        try {
            waitManager.waitForVisibility(breadcrumbLastItem);
            return driver.findElement(breadcrumbLastItem).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    public void selectSortingLowToHigh() {
        clickElement(sortingTitle);   // Sıralama menüsünü aç
        clickElement(priceLowToHigh);    // "Fiyata göre artan" seçeneğine tıkla
        waitManager.waitForSeconds(2);
    }


    public String getSelectedSortingText() {
        return driver.findElement(sortingTitle).getText();
    }
}