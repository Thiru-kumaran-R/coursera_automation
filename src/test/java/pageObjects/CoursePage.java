package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelUtils; // Import Excel Util
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CoursePage {
    WebDriver driver;
    WebDriverWait wait;

    public CoursePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
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
    public void extractAndSaveFirstTwoCourses() {
        List<String> courseTitles = new ArrayList<>();
        List<String> courseRatings = new ArrayList<>();
        List<String> courseDurations = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            try {
                // List refresh to avoid StaleElementReferenceException
                List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@id='searchResults']/div/div/ul/li")));

                if (i >= items.size()) break;
                WebElement item = items.get(i);

                // Data Extraction
                String title = item.findElement(By.cssSelector("h3.cds-CommonCard-title")).getText();
                String rating = item.findElement(By.cssSelector("span[class*='css-4s48ix']")).getText();
                String durationRaw = item.findElement(By.cssSelector("div.cds-CommonCard-metadata > p")).getText();

                String[] timeParts = durationRaw.split("·");
                String duration = (timeParts.length > 2) ? timeParts[2].trim() : "N/A";

                // List mein add karna
                courseTitles.add(title);
                courseRatings.add(rating);
                courseDurations.add(duration);

                System.out.println("Extracted: " + title);

            } catch (Exception e) {
                System.out.println("Error at item " + i + ": " + e.getMessage());
            }
        }

         if (!courseTitles.isEmpty()) {
            ExcelUtils.writeCourseData(courseTitles, courseRatings, courseDurations);
        }
    }
}