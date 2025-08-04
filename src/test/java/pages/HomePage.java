//Arama sayfası elementleri ve işlemleri
package pages;

import com.sun.jna.WString;
import helpers.WaitManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WaitManager waitManager;
    private final By searchBox = By.cssSelector("input[data-test-id='search-bar-input']");
    private final By noResultMessage = By.cssSelector("span[class^='no-result-view-']");
    private final By productCards = By.cssSelector("li[id^='i']");
    private final By connectionErrorMessage = By.xpath("//h1[contains(., 'Bağlantınız güvensiz')]");
    private final By guvenlikUyarisiParagraph = By.xpath("//p[contains(text(),'Eğer bağlantınızın güvenli olduğundan eminseniz')]");
    private final By searchHeader = By.cssSelector("h1[data-test-id='header-h1']");



    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver, 500);
    }

    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    public void clickSearch() {
        clickElement(searchBox);

    }

    public void searchWithEmptyText() {
        WebElement searchInput = driver.findElement((By.className("initialComponent-jWu4fqeOfmZhku5aNxLE")));

        searchInput.click();


        searchInput.sendKeys("bilgisayar");  // Boş arama
        searchInput.sendKeys(Keys.ENTER);
    }


    public void open() {
        driver.get("https://www.hepsiburada.com");
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public void search(String keyword) {
        waitManager.waitForClickable(searchBox);
        clickSearch();

    }

    public void search2(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        // 1. Arama kutusuna tıkla
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        new Actions(driver).moveToElement(input).click().perform();

        // 2. Tıklamadan sonra input DOM'da yeniden oluşmuş olabilir, yeniden bul
        input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));

        // 3. Kelimeyi yaz ve ENTER tuşuna bas
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);

        // 4. Sayfa başlığında arama kelimesinin görünmesini bekle
        wait.until(ExpectedConditions.elementToBeClickable(searchBox)); //çalışıyor
    }

    public void searchWithSqlInjection(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Arama kutusuna tıkla
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        new Actions(driver).moveToElement(input).click().perform();

        // 2. Tıklamadan sonra input DOM'da yeniden oluşmuş olabilir, yeniden bul
        input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));

        // 3. Kelimeyi yaz ve ENTER tuşuna bas
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);

        // 4. Sayfa başlığında arama kelimesinin görünmesini bekle
        // wait.until(ExpectedConditions.titleContains(keyword)); // çalışıyor
        String currentUrl = driver.getCurrentUrl();
        wait.until(ExpectedConditions.urlToBe(currentUrl));
    }

    private int getProductCount() {
        waitManager.waitForVisibility(productCards);
        return driver.findElements(productCards).size();
    }

    public boolean hasProducts() {
        return getProductCount() > 0;
    }

    public void enterSearchTerm(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Arama kutusuna tıkla
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        new Actions(driver).moveToElement(input).click().perform();

        // 2. Tıklamadan sonra input DOM'da yeniden oluşmuş olabilir, yeniden bul
        input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));

        // 3. Kelimeyi yaz ve ENTER tuşuna bas
        input.sendKeys(keyword);
    }

    public boolean isNoResultSearch() {
        return driver.findElement(noResultMessage).getText().contains("Aramana uygun ürün bulunamadı");
    }

    public boolean isConnectionError(String keyword) {
        return driver.getCurrentUrl().contains(keyword);
    }

    public boolean isSecurityWarningVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement h1 = wait.until(ExpectedConditions.visibilityOfElementLocated(connectionErrorMessage));

            return h1.getText().startsWith("Bağlantınız güvensiz");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean hasSearchHeader(String keyword){
        return driver.findElement(searchHeader).getText().contains(keyword);
    }

}

