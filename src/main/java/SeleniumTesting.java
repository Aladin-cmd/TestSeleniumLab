import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class SeleniumTesting {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\универ\\git labs\\chromedriver-win32v2\\chromedriver-win32\\chromedriver.exe");

        // Initialize the ChromeDriver instance
        driver = new ChromeDriver();
    }

    @Test
    public void sauceDemoTest() {
        // Navigate to the SauceDemo website
        driver.get("https://www.saucedemo.com/");

        // Find input elements of username, password and login button
        WebElement username_input = driver.findElement(By.id("user-name"));
        WebElement password_input = driver.findElement(By.name("password"));
        WebElement login_btn = driver.findElement(By.name("login-button"));

        // Type Username and Password
        username_input.sendKeys("standard_user");
        password_input.sendKeys("wrongpassword");
        // Check if input field has typed text
        String result = username_input.getText();
        result.contains("standard_user");
        // Click login button
        login_btn.click();

        // Element that shows up when the error comes out
        WebElement error = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));

        // Assert that error message is displayed to user
        Assert.assertTrue(error.isDisplayed());

    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        driver.quit();
    }


}
