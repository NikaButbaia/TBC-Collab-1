package ge.tbc.testautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static ge.tbc.testautomation.data.Constants.*;

public class CheckNotifications {

    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        ChromeOptions options = new ChromeOptions();

        String profileDir = "C:\\temp\\chrome-profile-cors-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + profileDir);

        options.addArguments("--disable-web-security");

        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
    @Test
    public void checkNotifications() throws InterruptedException {
        driver.get(URL_1);

        waitForElementVisible("//input[@id='email']", 10);
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='email']"));
        emailInput.sendKeys(EMAIL);

        waitForElementVisible("//p-password//input", 10);
        WebElement passwordInput = driver.findElement(By.xpath("//p-password//input"));
        passwordInput.sendKeys(PASSWORD);

        WebElement rememberMe = driver.findElement(By.xpath("//label[text()='Remember me']"));
        rememberMe.click();

        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        Thread.sleep(1000);

        waitForElementClickable("//button[@class='p-toast-close-button']",10);
        WebElement loginX = driver.findElement(By.xpath("//button[@class='p-toast-close-button']"));
        loginX.click();

        waitForElementClickable("//button[contains(@class,'notification-btn')]", 10);
        WebElement notificationButton = driver.findElement(By.xpath("//button[contains(@class,'notification-btn')]"));
        notificationButton.click();

        waitForElementVisible("//h1[@class='notifications-title']", 10);
        WebElement notification = driver.findElement(By.xpath("//h1[@class='notifications-title']"));
        Assert.assertEquals(notification.getText(), NOTIFICATIONS);

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
