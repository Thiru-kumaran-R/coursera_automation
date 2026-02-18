// support/DriverManager.java
package support;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static void set(WebDriver driver) {
        TL.set(driver);
    }

    public static WebDriver get() {
        return TL.get();
    }
    public static void quitAndRemove() {
        WebDriver d = TL.get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }
}