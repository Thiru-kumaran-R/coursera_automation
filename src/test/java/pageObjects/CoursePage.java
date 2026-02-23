package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CoursePage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public CoursePage(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void filterByLevel(String levelName) {
        try {
            WebElement levelDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Level']")));
            scrollIntoView(levelDropdown);
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
            scrollIntoView(langDropdown);
            langDropdown.click();
            WebElement langOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + langName + "']")));
            langOption.click();
            driver.findElement(By.xpath("//span[text()='View']")).click();
        } catch (Exception e) {
            System.out.println("Error filtering language: " + e.getMessage());
        }
    }

    public void extractCoursesAndWriteToExcel(int maxCourses) {
        List<String> names = new ArrayList<>();
        List<String> ratings = new ArrayList<>();
        List<String> hours = new ArrayList<>();

        for (int i = 0; i < maxCourses; i++) {
            // Re-find the list each time to avoid stale element references
            List<WebElement> items = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//div[@id='searchResults']/div/div/ul/li")
                    )
            );

            if (i >= items.size()) break; // Safety check

            WebElement item = items.get(i);

            try {
                // Title
                String title = item.findElement(By.cssSelector("h3.cds-CommonCard-title"))
                        .getText()
                        .trim();

                // Rating (fallback to N/A if not present)
                String rating = "N/A";
                try {
                    rating = item.findElement(By.cssSelector("span[class*='css-4s48ix']"))
                            .getText()
                            .trim();
                } catch (NoSuchElementException ignored) {}

                // Duration / Hours (3rd part after "·" if available)
                String duration = "N/A";
                try {
                    String durationRaw = item.findElement(By.cssSelector("div.cds-CommonCard-metadata > p"))
                            .getText();
                    String[] parts = durationRaw.split("·");
                    if (parts.length > 2) {
                        duration = parts[2].trim();
                    }
                } catch (NoSuchElementException ignored) {}

                // Collect
                names.add(title);
                ratings.add(rating);
                hours.add(duration);

            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("Skipping item " + i + " due to element change: " + e.getMessage());
            }
        }

        // Convert lists to arrays and write to Excel
        String[] namesArr = names.toArray(new String[0]);
        String[] ratingsArr = ratings.toArray(new String[0]);
        String[] hoursArr = hours.toArray(new String[0]);

        try {
            ExcelUtils.writeCourseData(namesArr, ratingsArr, hoursArr);
            System.out.println("Wrote " + namesArr.length + " course(s) to CourseDetails.xlsx");
        } catch (Exception e) {
            throw new RuntimeException("Failed writing to Excel: " + e.getMessage(), e);
        }
    }

    public void navigateBackAndWait() {
        String before = driver.getCurrentUrl();
        System.out.print(before);
        driver.navigate().to("https://www.coursera.org/");
    }

    // Helper Methods
    private void scrollIntoView(WebElement el) {
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", el
        );
    }

}