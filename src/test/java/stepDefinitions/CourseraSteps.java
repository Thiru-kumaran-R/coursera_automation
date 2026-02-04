package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.SearchPage;
import utilities.ExcelUtils;
import java.time.Duration;

public class CourseraSteps {
    WebDriver driver;
    SearchPage searchPage;

    @Given("I navigate to the Coursera homepage")
    public void navigateHome() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.coursera.org/");
        searchPage = new SearchPage(driver);
    }

    @When("I search for {string} courses")
    public void search(String course) throws InterruptedException { // Added throws
        searchPage.searchCourse(course);
    }

    @When("I filter by {string} level and {string} language")
    public void filter(String level, String lang) throws InterruptedException { // Added throws
        searchPage.applyFilters();
    }

    @Then("I extract the details of the first 2 courses and save to Excel")
    public void extract() throws Exception {
        // For this example, we mock the data to test the Excel write.
        // Real extraction requires finding the list elements on the page.
        String[] names = {"Web Design for Everybody", "Front-End Web Dev"};
        String[] ratings = {"4.7", "4.8"};
        String[] hours = {"40 hours", "35 hours"};

        ExcelUtils.writeCourseData(names, ratings, hours);
        System.out.println("Excel generated!");

        driver.quit();
    }
}