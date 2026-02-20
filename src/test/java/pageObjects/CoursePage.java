package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
        for (int i = 0; i < 2; i++) {
            // 1. Re-find the list inside the loop to ensure elements aren't "Stale"
            List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@id='searchResults']/div/div/ul/li")));

            // Safety check to ensure the index exists
            if (i >= items.size()) break;

            WebElement item = items.get(i);

            try {
                // 2. Extract Title

                String title = item.findElement(By.cssSelector("h3.cds-CommonCard-title")).getText();

                // 3. Extract Rating (Using a more flexible selector if class names change)
                String rating = item.findElement(By.cssSelector("span[class*='css-4s48ix']")).getText();

                // 4. Extract Duration with Safety Split
                String durationRaw = item.findElement(By.cssSelector("div.cds-CommonCard-metadata > p")).getText();
                String[] timeParts = durationRaw.split("·");

                // Safety check: only access index 2 if the split produced enough parts
                String duration = (timeParts.length > 2) ? timeParts[2].trim() : "N/A";

                System.out.println("--- Course " + (i + 1) + " ---");
                System.out.println("Title: " + title);
                System.out.println("Rating: " + rating);
                System.out.println("Duration: " + duration);

            } catch (Exception e) {
                System.out.println("Skip item " + i + " due to element change: " + e.getMessage());
                // Optional: refresh items and try again
            }
        }
    }
}