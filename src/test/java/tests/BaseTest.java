package tests;

import helpers.PropertiesProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    public void generalSetup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        this.driver = new ChromeDriver(chromeOptions);

        driver.get(PropertiesProvider.getProperty("w2aSiteURL"));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }

    public WebDriver getDriver () {
        return driver;
    }

}
