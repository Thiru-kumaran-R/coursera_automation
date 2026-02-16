package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor to initialize driver from Step Definition
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void launchCoursera() {
        driver.get("https://www.coursera.org");
        driver.manage().window().maximize();
    }

    public void searchForCourse(String courseName) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[placeholder='What do you want to learn?']")));
            searchBox.clear();
            searchBox.sendKeys(courseName);
            searchBox.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Error finding search box: " + e.getMessage());
        }
    }
}