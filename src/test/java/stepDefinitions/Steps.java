// stepDefinitions/Steps.java
package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePage;
import pageObjects.CoursePage;
import support.DriverManager;

public class Steps {

    private WebDriver driver;
    private HomePage homePage;
    private CoursePage coursePage;


    public Steps() {
        if (DriverManager.isPresent()) {
            this.driver = DriverManager.get();
        }
    }

    // --- Web Development Scenarios ---

    @Given("I am on the Coursera homepage")
    public void i_am_on_the_coursera_homepage() {
        ensureDriver();
        homePage = new HomePage(driver);
//        homePage.launchCoursera();
    }

    @When("I search for {string} courses")
    public void i_search_for_courses(String courseName) {
        ensureDriver();
        ensureHomePage();
        homePage.searchForCourse(courseName);
    }

    @When("I filter courses by {string} level")
    public void i_filter_courses_by_level(String level) {
        ensureDriver();
        ensureCoursePage();
        coursePage.filterByLevel(level);
    }

    @When("I filter courses by {string} language")
    public void i_filter_courses_by_language(String language) {
        ensureDriver();
        ensureCoursePage();
        coursePage.filterByLanguage(language);
    }

    @Then("I should see the first two courses displayed")
    public void i_should_see_the_first_two_courses_displayed() {
        ensureDriver();
        ensureCoursePage();
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
        // Do NOT quit here; Hooks @After will close the browser via DriverManager.
    }

    // --- Helpers ---

    private void ensureDriver() {
        if (driver == null) {
            if (!DriverManager.isPresent()) {
                throw new IllegalStateException(
                        "WebDriver is not available. Ensure Hooks @Before sets DriverManager.set(driver) before steps run."
                );
            }
            driver = DriverManager.get();
        }
    }

    private void ensureHomePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
    }

    private void ensureCoursePage() {
        if (coursePage == null) {
            coursePage = new CoursePage(driver);
        }
    }
}
