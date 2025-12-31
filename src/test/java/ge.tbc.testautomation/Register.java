package ge.tbc.testautomation;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static ge.tbc.testautomation.data.Constants.*;

public class Register {
    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");


        options.addArguments("--user-data-dir=/tmp/chrome-profile-" + System.currentTimeMillis());

        String profileDir = "C:\\temp\\chrome-profile-cors-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + profileDir);

        options.addArguments("--disable-web-security");

        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
    }
    @Test
    public void registerTest(){
        driver.get(URL_2);

        waitForElementVisible("//input[@id='firstName']",10);
        WebElement firstName = driver.findElement(By.xpath("//input[@id='firstName']"));
        firstName.sendKeys(FIRSTNAME);

        WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));
        lastName.sendKeys(LASTNAME);

        waitForElementVisible("//input[@id='email']",10);
        WebElement registerEmail = driver.findElement(By.xpath("//input[@id='email']"));
        registerEmail.sendKeys(EMAIL_2);

        waitForElementVisible("(//input[@type='password'])[1]",10);
        WebElement passwordInput = driver.findElement(By.xpath("(//input[@type='password'])[1]"));
        passwordInput.sendKeys(PASSWORD_Register);

        WebElement confirmPasswordInput = driver.findElement(By.xpath("(//input[@type='password'])[2]"));
        confirmPasswordInput.sendKeys(PASSWORD_Register);

        WebElement registerButton = driver.findElement(By.xpath("//button[@type='submit']"));
        registerButton.click();
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        safeQuit(driver);
        driver = null;
    }

    private static void safeQuit(WebDriver d) {
        if (d == null) return;
        try { d.manage().deleteAllCookies(); } catch (Throwable ignored) {}
        try { d.quit(); }
        catch (Throwable e1) {
            try { d.close(); } catch (Throwable ignored) {}
        }
    }
    public void waitForElementVisible(String xpath, int timeoutSeconds) {
        System.out.println("Waiting for element "+xpath);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
    public void waitForElementClickable(String xpath, int timeoutSeconds){
        System.out.println("Waiting for element clickable " + xpath);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }
}
