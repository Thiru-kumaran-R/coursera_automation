package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitHelper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LearningPage {
    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WaitHelper waitHelper;
    private final WebDriverWait wait;

    public LearningPage(WebDriver driver){
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.waitHelper = new WaitHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ===== Elements (adjust if DOM changes) =====

    // Category card/link to "Language Learning"
    @FindBy(xpath = "//a[normalize-space()='Language Learning' or contains(@href,'language-learning')]")
    private WebElement languageLearning;

    // Language filter group container
    @FindBy(xpath = "//div[@data-testid='search-filter-group-Language']//div[contains(@class,'cds-formGroup-groupWrapper')]")
    private WebElement languageOptions;

    // Level filter group container
    @FindBy(xpath = "//div[@data-testid='search-filter-group-Level']//div[contains(@class,'cds-formGroup-groupWrapper')]")
    private WebElement levelOptions;

    // ===== Actions =====

    /** Scrolls to the Language Learning category and clicks it */
    public void clickLanguageLearning(){
        // Prefer scrolling the element that you will click
        waitForVisible(languageLearning);
        scrollIntoView(languageLearning);
        clickSafely(languageLearning);
    }

    public List<String> extractAllLanguagesViaJS() throws InterruptedException {
        waitForVisible(languageOptions);
        scrollIntoView(languageOptions);

        WebElement showMore = driver.findElement(By.cssSelector("button[data-testid='expand-filter-items-button']"));
        if (showMore.isDisplayed()) {
            showMore.click();
        }

        // Scroll to bottom similarly as in Option A
        WebElement scrollContainer = languageOptions;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int prevCount = -1;
        int stableIterations = 0;
        while (stableIterations < 2) {
            Long before = (Long) js.executeScript("return arguments[0].scrollHeight;", scrollContainer);
            js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", scrollContainer);
            Thread.sleep(300);
            Long after = (Long) js.executeScript("return arguments[0].scrollHeight;", scrollContainer);
            if (before.equals(after)) {
                stableIterations++;
            } else {
                stableIterations = 0;
            }
        }

        @SuppressWarnings("unchecked")
        List<String> allLanguages = (List<String>) js.executeScript(
                "const root = arguments[0];" +
                        "const items = root.querySelectorAll('div.css-j7pf8u');" +
                        "return Array.from(items).map(it => {" +
                        "  const name = it.querySelector('label > div span:not(.css-ebbjlp)');" +
                        "  const count = it.querySelector('span.css-ebbjlp');" +
                        "  const n = (name && name.textContent ? name.textContent.trim() : '');" +
                        "  const c = (count && count.textContent ? count.textContent.trim() : '');" +
                        "  return n + ' --> ' + c;" +
                        "});",
                languageOptions
        );

        return allLanguages;
    }


    public List<String> extractAllLevels(){
        waitForVisible(levelOptions);
        scrollIntoView(levelOptions);

        List<WebElement> levelItems = levelOptions.findElements(
                By.cssSelector("div.css-j7pf8u") // adjust if needed
        );

        List<String> levels = new ArrayList<>();
        for (WebElement item : levelItems) {
            try {
                String labelText = item.findElement(
                        By.cssSelector("label div span:not(.css-ebbjlp)")
                ).getText().trim();
                if (!labelText.isEmpty()) {
                    levels.add(labelText);
                }
            } catch (NoSuchElementException | StaleElementReferenceException ignored) { }
        }
        return levels;
    }

    public void navigateBack(){
        driver.navigate().back();
    }

    // ===== Helpers =====

    private void waitForVisible(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    private void scrollIntoView(WebElement el) {
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", el
        );
    }

    private void clickSafely(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el));
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            // Fallback to JS click if something overlays it
            js.executeScript("arguments[0].click();", el);
        }
    }
}