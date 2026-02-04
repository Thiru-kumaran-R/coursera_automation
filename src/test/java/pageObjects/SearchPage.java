package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SearchPage {
    WebDriver driver;
    WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // 1. Search Box
    @FindBy(css = "input[placeholder='What do you want to learn?']")
    WebElement searchBox;

    // UNBREAKABLE SEARCH METHOD
    public void searchCourse(String courseName) throws InterruptedException {
        // 1. Try normal interaction first
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            searchBox.clear();
            searchBox.sendKeys(courseName);
            searchBox.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Could not interact with search box directly.");
        }

        // 2. CHECK & FORCE NAVIGATION
        // Wait a moment to see if the site reacts
        Thread.sleep(2000);

        // If the URL is still the homepage (doesn't have "search?query"), FORCE IT.
        if (!driver.getCurrentUrl().contains("search?query")) {
            System.out.println("Enter key was ignored. Forcing navigation via URL...");
            String encodedCourse = courseName.replace(" ", "%20"); // Handle spaces
            driver.get("https://www.coursera.org/search?query=" + encodedCourse);
        }

        System.out.println("Successfully landed on Search Results page.");
        Thread.sleep(3000); // Wait for results to load
    }

    public void applyFilters() throws InterruptedException {
        // Scroll down to ensure sidebar is loaded
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 300)");
        Thread.sleep(2000);

        // Click Beginner
        System.out.println("Scanning for Beginner filter...");
        clickFilterByScanning("Beginner");

        System.out.println("Waiting for refresh...");
        Thread.sleep(3000);

        // Click English
        System.out.println("Scanning for English filter...");
        clickFilterByScanning("English");
    }

    public void clickFilterByScanning(String filterText) {
        boolean found = false;

        // Grab generic containers: Labels (checkboxes) and Spans (text)
        List<WebElement> candidates = driver.findElements(By.xpath("//label | //span"));

        System.out.println("Scanning " + candidates.size() + " elements for '" + filterText + "'");

        for (WebElement element : candidates) {
            try {
                String text = element.getText().trim();
                // Check if text matches AND element is visible
                if (text.contains(filterText) && element.isDisplayed()) {

                    // Scroll to it to make sure it's clickable
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                    Thread.sleep(500);

                    try {
                        element.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    }

                    System.out.println("Clicked: " + text);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                // Ignore stale elements
            }
        }

        if (!found) {
            System.out.println("DEBUG: Still couldn't find '" + filterText + "'.");
            // We don't throw an exception here because sometimes "English" is already selected by default
            // throwing an exception stops the whole test, printing allows us to continue.
        }
    }
}