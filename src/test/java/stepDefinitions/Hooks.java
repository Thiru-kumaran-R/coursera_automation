// stepDefinitions/Hooks.java
package stepDefinitions;

import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import support.DriverManager;

import java.time.Duration;

public class Hooks {

    // Shared across the whole test run
    private static WebDriver sharedDriver;

    @BeforeAll
    public static void beforeAll() {
        ChromeOptions options = new ChromeOptions();

        sharedDriver = new ChromeDriver(options);
        sharedDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // prefer explicit waits
        sharedDriver.manage().window().maximize();

        // (Optional) Navigate once here if you want a known starting page
        sharedDriver.get("https://www.coursera.org/");
    }

    @Before(order = 0)
    public void beforeEachScenario(Scenario scenario) {
        if (sharedDriver == null) {
            throw new IllegalStateException("Shared driver is null. Did @BeforeAll fail?");
        }

        // 🔑 Bind the shared driver into ThreadLocal for THIS scenario thread
        DriverManager.set(sharedDriver);

        // Reset state between scenarios (so tests don't leak into each other)
        try {
            sharedDriver.manage().deleteAllCookies();
        } catch (Exception e) {
            System.err.println("[WARN] Could not delete cookies: " + e.getMessage());
        }

        System.out.println("[SETUP] Scenario: " + scenario.getName());
    }

    @After(order = 0)
    public void afterEachScenario(Scenario scenario) {
        try {
            if (scenario.isFailed() && sharedDriver instanceof org.openqa.selenium.TakesScreenshot) {
                try {
                    byte[] png = ((org.openqa.selenium.TakesScreenshot) sharedDriver)
                            .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                    scenario.attach(png, "image/png", "Failure Screenshot");
                } catch (Exception e) {
                    System.err.println("[WARN] Failed to capture failure screenshot: " + e.getMessage());
                }
            }
        } finally {
            // ❗ Shared-driver model: unbind ThreadLocal; do NOT quit here
            DriverManager.removeOnly();
        }
        System.out.println("[TEARDOWN] Scenario: " + scenario.getName() + " | Status: " + scenario.getStatus());
    }

    @AfterAll
    public static void afterAll() {
        try {
            if (sharedDriver != null) {
                sharedDriver.quit(); // Quit once at the end of the run
            }
        } finally {
            sharedDriver = null;
            System.out.println("[AFTER ALL] Closed shared browser session");
        }
    }
}