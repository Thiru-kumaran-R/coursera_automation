// stepDefinitions/Steps.java
package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageObjects.EnterprisePage;
import pageObjects.HomePage;
import pageObjects.CoursePage;
import pageObjects.LearningPage;
import support.DriverManager;
import utilities.RepositoryParser;
import utilities.Screenshot;

import java.io.IOException;
import java.util.List;

public class Steps {

    private WebDriver driver;
    private HomePage homePage;
    private CoursePage coursePage;
    private LearningPage learningPage;
    private EnterprisePage enterprisePage;
    private RepositoryParser repositoryParser;

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
        homePage.launchCoursera();
    }

    @When("I search for {string} courses")
    public void i_search_for_courses(String courseName) throws InterruptedException {
        ensureDriver();
        ensureHomePage();
        homePage.searchForCourse(courseName);
        Thread.sleep(5000);
    }

    @When("I filter courses by {string} level")
    public void i_filter_courses_by_level(String level) throws InterruptedException {
        ensureDriver();
        ensureCoursePage();
        coursePage.filterByLevel(level);
        Thread.sleep(5000);
    }

    @When("I filter courses by {string} language")
    public void i_filter_courses_by_language(String language) throws InterruptedException {
        ensureDriver();
        ensureCoursePage();
        coursePage.filterByLanguage(language);
        Thread.sleep(5000);
    }

    @Then("I should see the first two courses displayed")
    public void i_should_see_the_first_two_courses_displayed() throws InterruptedException {
        ensureDriver();
        ensureCoursePage();
        coursePage.extractFirstTwoCourses();
        Thread.sleep(5000);
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
        coursePage.navigateBackAndWait();
        // Do NOT quit here; Hooks @After will close the browser via DriverManager.
    }

    @When("I navigate to {string} category")
    public void i_navigate_to_category(String string) {
        ensureLearningPage();
        learningPage.clickLanguageLearning();
    }

    @Then("I should extract all available languages")
    public void i_should_extract_all_available_languages() throws InterruptedException {
        List<String> allLanguages = learningPage.extractAllLanguagesViaJS();
        System.out.println(allLanguages);
        Thread.sleep(5000);
    }

    @Then("I should extract all levels for each language")
    public void i_should_extract_all_levels_for_each_language() throws InterruptedException {
        List<String> allLevels = learningPage.extractAllLevels();
        System.out.println(allLevels);
        Thread.sleep(5000);
    }

    @Then("I should display the total count of courses per language and level")
    public void i_should_display_the_total_count_of_courses_per_language_and_level() {
        System.out.println("Storing data into Excel File.....");
        learningPage.navigateBack();
    }


    @When("I navigate to {string}")
    public void i_navigate_to(String string) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        enterprisePage = new EnterprisePage(driver);
        enterprisePage.navigateToEnterprise();
        Thread.sleep(4000);
    }

    @When("I go to {string} under Product")
    public void i_go_to_under_product(String string) {
        System.out.print("Hi.......");
    }
    @When("I scroll down to the form")
    public void i_scroll_down_to_the_form() {
        enterprisePage.scrollDownToForm();
    }

    @When("I fill the form with invalid email input")
    public void i_fill_the_form_with_invalid_email_input() throws IOException {
        repositoryParser = new RepositoryParser("src/test/resources/config.properties");
        String fName = repositoryParser.getData("fName");
        String lName = repositoryParser.getData("lName");
        String mail = repositoryParser.getData("mail");
        String fakeMail = repositoryParser.getData("fakeMail");
        String phone = repositoryParser.getData("phone");
        String instType = repositoryParser.getData("instType");
        String instName = repositoryParser.getData("instName");
        String role = repositoryParser.getData("role");
        String dept = repositoryParser.getData("dept");
        String need = repositoryParser.getData("need");
        String ctry = repositoryParser.getData("ctry");
        enterprisePage.fillForm(fName, lName, fakeMail, phone,instType, instName, role, dept, need, ctry);
    }

    @When("I submit the form")
    public void i_submit_the_form() throws InterruptedException {
        enterprisePage.submitForm();
        Thread.sleep(4000);
    }

    @Then("I should capture and display the error message")
    public void i_should_capture_and_display_the_error_message() throws IOException {
        Screenshot.takeScreenshot(driver);
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

    private void ensureLearningPage() {
        if (learningPage == null) {
            learningPage = new LearningPage(driver);
        }
    }
}
