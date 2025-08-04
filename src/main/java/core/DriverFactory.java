//WebDriver yönetimi
package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {


        private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

        public static WebDriver getDriver() {
            if (driver.get() == null) {
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
            }
            return driver.get();
        }

        public static void quitDriver() {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
            }
        }


}
