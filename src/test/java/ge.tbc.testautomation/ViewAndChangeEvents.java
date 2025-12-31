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

public class ViewAndChangeEvents {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
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
    public void viewEventsTest() throws InterruptedException{
        driver.get(URL_1);

        waitForElementVisible("//input[@id='email']",10);
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='email']"));
        emailInput.sendKeys(EMAIL);

        waitForElementVisible("//p-password//input",10);
        WebElement passwordInput = driver.findElement(By.xpath("//p-password//input"));
        passwordInput.sendKeys(PASSWORD);

        WebElement rememberMe = driver.findElement(By.xpath("//label[text()='Remember me']"));
        rememberMe.click();

        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        Thread.sleep(1000);

        waitForElementClickable("//a[@href='/home']",10);
        WebElement eventsHub = driver.findElement(By.xpath("//a[@href='/home']"));
        eventsHub.click();

        waitForElementClickable("//a[@routerlink='/events']",10);
        WebElement eventsViewAll = driver.findElement(By.xpath("//a[@routerlink='/events']"));
        eventsViewAll.click();

        waitForElementVisible("//label[contains(normalize-space(text()), 'Online')]",10);
        WebElement filterCheckbox = driver.findElement(By.xpath("//label[contains(normalize-space(text()), 'Online')]"));
        filterCheckbox.click();

        waitForElementClickable("//div[@class='p-card-body']",10);
        WebElement clickEvent = driver.findElement(By.xpath("//div[@class='p-card-body']"));
        clickEvent.click();

        waitForElementVisible("//button[@class='register-btn p-button p-component ng-star-inserted']",10);
        WebElement registerButton = driver.findElement(By.xpath("//button[@class='register-btn ng-star-inserted p-button p-component']"));
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
