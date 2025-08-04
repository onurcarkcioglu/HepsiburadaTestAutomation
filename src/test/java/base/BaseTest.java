//Test öncesi sonrası işlemler
package base;

import core.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.hepsiburada.com/");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }


}