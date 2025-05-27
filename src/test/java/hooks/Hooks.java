package hooks;

import utils.ExtentReportUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks
{
    @Before
    public void beforeScenario(Scenario scenario) {
        // Initialize Extent Reports
        ExtentReportUtils.initializeReport("target/extent-reports.html");

        // Create a new test for the scenario
        ExtentReportUtils.createTest(scenario.getName());
        ExtentReportUtils.logInfo("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        // Log the scenario status
        if (scenario.isFailed()) {
            ExtentReportUtils.logFail("Scenario failed: " + scenario.getName());
        } else {
            ExtentReportUtils.logPass("Scenario passed: " + scenario.getName());
        }

        // Flush the report
        ExtentReportUtils.flushReport();
    }
}
