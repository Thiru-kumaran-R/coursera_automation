// stepDefinitions/Hooks.java
package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import support.DriverManager;

public class Hooks {
    @Before
    public void setUp() {
        ChromeOptions opts = new ChromeOptions();
        // opts.addArguments("--headless=new"); // optional
        WebDriver driver = new ChromeDriver(opts);
        driver.manage().window().maximize();
        DriverManager.set(driver);
    }

    @After
    public void tearDown() {
        DriverManager.quitAndRemove();
    }
}