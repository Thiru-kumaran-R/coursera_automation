package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class EnterprisePage {
    WebDriver driver;
    JavascriptExecutor js;

    public EnterprisePage(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    By enterpriseLink =  By.partialLinkText("Campus");
    // Form XPaths
    By firstName = By.xpath("//form[@id='mktoForm_1512']//div[@class='mktoFieldWrap mktoRequiredField']/child::input[@name='FirstName']");
    By lastName = By.xpath("//form[@id='mktoForm_1512']//div[@class='mktoFieldWrap mktoRequiredField']/child::input[@name='LastName']");
    By email = By.xpath("//form[@id='mktoForm_1512']//div[@class='mktoFieldWrap mktoRequiredField']/child::input[@name='Email']");
    By phone = By.xpath("//form[@id='mktoForm_1512']//div[@class='mktoFieldWrap mktoRequiredField']/child::input[@name='Phone']");
    By institutionType = By.xpath("//select[@id='Institution_Type__c']");
    By institutionName = By.xpath("//form[@id='mktoForm_1512']//div[@class='mktoFieldWrap mktoRequiredField']/child::input[@name='Company']");
    By jobRole = By.xpath("//select[@name='Title']");
    By department = By.xpath("//select[@id='Department']");
    By needs = By.xpath("//select[@id='Self_Reported_Needs__c']");
    By country = By.xpath("//select[@id='Country']");
    By submitBtn = By.xpath("//button[@type='submit']");
    By errorMsg = By.xpath("//div[@id='ValidMsgEmail']");


    public void navigateToEnterprise(){
        WebElement campus = driver.findElement(enterpriseLink);
        scrollIntoView(campus);

        String parentWindow = driver.getWindowHandle();

        campus.click();


    }

    public void scrollDownToForm(){
        WebElement fn = driver.findElement(firstName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fn);
    }

    public void fillForm(String fName, String lName, String mail, String ph, String instType, String instName, String role, String dept, String need, String ctry) {
        WebElement fn = driver.findElement(firstName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fn);

        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(email).sendKeys(mail);
        driver.findElement(phone).sendKeys(ph);

        new Select(driver.findElement(institutionType)).selectByValue(instType);
        driver.findElement(institutionName).sendKeys(instName);
        new Select(driver.findElement(jobRole)).selectByVisibleText(role);
        new Select(driver.findElement(department)).selectByVisibleText(dept);
        new Select(driver.findElement(needs)).selectByVisibleText(need);
        new Select(driver.findElement(country)).selectByVisibleText(ctry);
    }

    public void submitForm() {
        driver.findElement(submitBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }

    // Helper
    private void scrollIntoView(WebElement el) {
        js.executeScript(
                "arguments[0].scrollIntoView();", el
        );
    }
}
