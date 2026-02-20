// OrderedSuite.java
package testRunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WebDevRunner.class,
        LanguageRunner.class,
        EnterpriseRunner.class
})
public class OrderedSuite { }