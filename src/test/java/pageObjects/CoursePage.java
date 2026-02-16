package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CoursePage {
    WebDriver driver;
    WebDriverWait wait;

    public CoursePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void filterByLevel(String levelName) {
        try {
            WebElement levelDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Level']")));
            levelDropdown.click();
            WebElement levelOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + levelName + "']")));
            levelOption.click();
            // Re-clicking 'View' or clicking outside to apply if necessary
            driver.findElement(By.xpath("//span[text()='View']")).click();
        } catch (Exception e) {
            System.out.println("Error filtering level: " + e.getMessage());
        }
    }

    public void filterByLanguage(String langName) {
        try {
            WebElement langDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Language']")));
            langDropdown.click();
            WebElement langOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + langName + "']")));
            langOption.click();
            driver.findElement(By.xpath("//span[text()='View']")).click();
        } catch (Exception e) {
            System.out.println("Error filtering language: " + e.getMessage());
        }
    }

    public void extractFirstTwoCourses() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='searchResults']//li")));
        List<WebElement> items = driver.findElements(By.xpath("//div[@id='searchResults']//li"));

        for (int i = 0; i < 2; i++) {
            WebElement item = items.get(i);
            String title = item.findElement(By.cssSelector("h3.cds-CommonCard-title")).getText();
            String rating = item.findElement(By.cssSelector("span.css-4s48ix")).getText();
            String metaData = item.findElement(By.cssSelector("div.cds-CommonCard-metadata > p")).getText();

            String[] parts = metaData.split("·");
            String duration = (parts.length >= 3) ? parts[2].trim() : "N/A";

            System.out.println("--- Course " + (i + 1) + " ---");
            System.out.println("Title: " + title);
            System.out.println("Rating: " + rating);
            System.out.println("Duration: " + duration);
        }
    }
}