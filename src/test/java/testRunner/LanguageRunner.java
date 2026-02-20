package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                ".//Features/AWebDevelopment.feature",
                ".//Features/BLanguageLearning.feature",
                ".//Features/EnterpriseForm.feature"
        },
        glue = "stepDefinitions",
        monochrome = true,
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class LanguageRunner {
}