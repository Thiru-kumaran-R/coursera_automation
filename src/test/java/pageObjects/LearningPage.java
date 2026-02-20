package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitHelper;

import java.util.ArrayList;
import java.util.List;

public class LearningPage {
    private WebDriver driver;
    private JavascriptExecutor js;
    private WaitHelper waitHelper;

    public LearningPage(WebDriver driver){
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // Elements
    @FindBy(xpath = "//a[text() = 'Language Learning']")
    WebElement langaugeLearning;

    @FindBy(xpath = "// div[@data-testid = 'search-filter-group-Language']//div[@class = 'cds-254 cds-formGroup-groupWrapper']")
    WebElement languageOptions;

    @FindBy(xpath = "// div[@data-testid = 'search-filter-group-Level']//div[@class = 'cds-254 cds-formGroup-groupWrapper']")
    WebElement levelOptions;

    // Actions to be performed
    public void clickLanguageLearning(){
        waitHelper.waitForElement(langaugeLearning, 10);
        langaugeLearning.click();
    }

    public List<String> extractAllLanguages(){
        waitHelper.waitForElement(languageOptions, 10);
        js.executeScript("arguments[0].scrollIntoView()", languageOptions);
        List<WebElement> listOfLanguages = languageOptions.findElements(By.cssSelector("div.css-j7pf8u"));
        List<String> allLanguages = new ArrayList<>();

        for(WebElement parentContainer : listOfLanguages){
            String langName = parentContainer.findElement(By.cssSelector("div.css-j7pf8u > label > div span:not(.css-ebbjlp)")).getText();
            String langCount = parentContainer.findElement(By.cssSelector("span.css-ebbjlp")).getText();

            String langData = langName + "-->" + langCount;
            allLanguages.add(langData);
        }

        return allLanguages;
    }

    public void extractAllLevel(){
        waitHelper.waitForElement(levelOptions, 10);
        js.executeScript("arguments[0].scrollIntoView()", levelOptions);

        WebElement listOfLevels = levelOptions.findElement(By.cssSelector("div.css-5ji5n2"));
//                levelOptions.findElements(By.cssSelector("div.css-j7pf8u > label > div span:not()"))
    }
}
