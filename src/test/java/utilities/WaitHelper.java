package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
    public WebDriver driver;

    public WaitHelper(WebDriver driver){
        this.driver = driver;
    }

    public void waitForElement(WebElement element, long timeoutseconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutseconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
