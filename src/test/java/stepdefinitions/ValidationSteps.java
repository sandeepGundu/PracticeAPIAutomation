package stepdefinitions;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationSteps
{
    private static final Logger logger = LogManager.getLogger(ValidationSteps.class);
    private final SharedState sharedState = SharedState.getInstance();

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        logger.info("Response Status Code: " + sharedState.getResponse().getStatusCode());
        ExtentReportUtils.logInfo("Response Status Code: " + sharedState.getResponse().getStatusCode());
        assertEquals(expectedStatusCode, sharedState.getResponse().getStatusCode(), "Status code mismatch!");
    }
}
