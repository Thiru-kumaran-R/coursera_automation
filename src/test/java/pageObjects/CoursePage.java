package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CoursePage {
    // code for course page should be added here
    WebDriver driver;


    public CoursePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Web Elements
    @FindBy(xpath = "//button[@data-testid = 'filter-dropdown-productDifficultyLevel']")
    WebElement levelButton;

    @FindBy(xpath = "//button[@data-testid = 'filter-dropdown-language']")
    WebElement languageButton;

//    @FindBy()





}
