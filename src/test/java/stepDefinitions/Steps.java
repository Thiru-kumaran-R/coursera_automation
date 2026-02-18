package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.HomePage;
import pageObjects.LearningPage;
import support.DriverManager;

public class Steps {
    WebDriver driver;
    HomePage homePage;
    LearningPage learningPage;
    String URL = "https://www.coursera.org/";

    // Web Development
    @Given("I am on the Coursera homepage")
    public void i_am_on_the_coursera_homepage() {
        // Write code here that turns the phrase above into concrete actions
        driver = DriverManager.get();
        homePage = new HomePage(driver);
        homePage.goToUrl(URL);

    }
    @When("I search for {string} courses")
    public void i_search_for_courses(String string) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        homePage.searchCourse("Web Developement");
    }
    @When("I filter courses by {string} level")
    public void i_filter_courses_by_level(String level) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        homePage.applyFilters(level);
    }
    @When("I filter courses by {string} language")
    public void i_filter_courses_by_language(String language) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        homePage.applyFilters(language);
    }
    @Then("I should see the first two courses displayed")
    public void i_should_see_the_first_two_courses_displayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("each course should show the name")
    public void each_course_should_show_the_name() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("each course should show the total learning hours")
    public void each_course_should_show_the_total_learning_hours() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("each course should show the rating")
    public void each_course_should_show_the_rating() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    // Language
    @When("I navigate to Language Learning category")
    public void i_navigate_to_category() {
        // Write code here that turns the phrase above into concrete actions
        homePage.navigateBack();
        homePage.clickLanguageLearning();
    }

    @Then("I should extract all available languages")
    public void i_should_extract_all_available_languages() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should extract all levels for each language")
    public void i_should_extract_all_levels_for_each_language() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should display the total count of courses per language and level")
    public void i_should_display_the_total_count_of_courses_per_language_and_level() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    // Enterprise

    @When("I navigate to {string}")
    public void i_navigate_to(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I go to {string} under Product")
    public void i_go_to_under_product(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I scroll down to the {string} form")
    public void i_scroll_down_to_the_form(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I fill the form with invalid email input")
    public void i_fill_the_form_with_invalid_email_input() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I submit the form")
    public void i_submit_the_form() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should capture and display the error message")
    public void i_should_capture_and_display_the_error_message() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}