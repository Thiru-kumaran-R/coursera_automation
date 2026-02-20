// support/DriverManager.java
package support;

import org.openqa.selenium.WebDriver;

public final class DriverManager {
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    private DriverManager() {}

    public static void set(WebDriver driver) {
        TL_DRIVER.set(driver);
    }

    public static WebDriver get() {
        WebDriver driver = TL_DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread.");
        }
        return driver;
    }

    /** For per-scenario models (quits the driver) */
    public static void quitAndRemove() {
        WebDriver driver = TL_DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            TL_DRIVER.remove();
        }
    }

    public static void removeOnly() {
        TL_DRIVER.remove();
    }

    public static boolean isPresent() {
        return TL_DRIVER.get() != null;
    }
}