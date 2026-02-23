package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Screenshot {

    private static final String DEFAULT_DIR = "screenshots";
    private static final DateTimeFormatter TS_FMT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    public static File takeScreenshot(WebDriver driver) throws IOException {
        String autoName = "screenshot-" + LocalDateTime.now().format(TS_FMT) + ".png";
        return takeScreenshot(driver, DEFAULT_DIR, autoName);
    }

    private static String coalesceDir(String dirPath, String defaultDir) {
        if (dirPath == null) return defaultDir;
        String trimmed = dirPath.trim();
        return trimmed.isEmpty() ? defaultDir : trimmed;
    }

    public static File takeScreenshot(WebDriver driver, String dirPath, String fileName) throws IOException {
        // Ensure driver supports screenshots
        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalArgumentException("Driver does not support screenshots: " + driver.getClass());
        }

        // Ensure .png extension
        String finalName = fileName.toLowerCase().endsWith(".png") ? fileName : fileName + ".png";

        // Prepare destination path
        Path dir = Paths.get(coalesceDir(dirPath, DEFAULT_DIR));
        Files.createDirectories(dir); // safe if already exists

        Path dest = dir.resolve(finalName);
        File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(tmp.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

        return dest.toFile();
    }

}