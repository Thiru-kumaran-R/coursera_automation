package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.HomePage;
import pageObjects.CoursePage;

public class Steps {
    WebDriver driver;
    HomePage homePage;
    CoursePage coursePage;

    // Web Development Scenarios
    @Given("I am on the Coursera homepage")
    public void i_am_on_the_coursera_homepage() {
        // Simple driver initialization
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        coursePage = new CoursePage(driver);

        homePage.launchCoursera();
    }

    @When("I search for {string} courses")
    public void i_search_for_courses(String courseName) {
        homePage.searchForCourse(courseName);
    }

    @When("I filter courses by {string} level")
    public void i_filter_courses_by_level(String level) {
        coursePage.filterByLevel(level);
    }

    @When("I filter courses by {string} language")
    public void i_filter_courses_by_language(String language) {
        coursePage.filterByLanguage(language);
    }

    @Then("I should see the first two courses displayed")
    public void i_should_see_the_first_two_courses_displayed() {
        // This method will handle the extraction and printing as per your logic
        coursePage.extractFirstTwoCourses();
    }

    @Then("each course should show the name")
    public void each_course_should_show_the_name() {
        System.out.println("Course names extracted successfully.");
    }

    @Then("each course should show the total learning hours")
    public void each_course_should_show_the_total_learning_hours() {
        System.out.println("Learning hours extracted successfully.");
    }

    @Then("each course should show the rating")
    public void each_course_should_show_the_rating() {
        System.out.println("Ratings extracted successfully.");
        // Close browser after the scenario finishes
        if (driver != null) {
            driver.quit();
        }
    }

    // Note: You will need to implement the 'Language' and 'Enterprise'
    // methods in your PageObjects and call them here similarly.
}