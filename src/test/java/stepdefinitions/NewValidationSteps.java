package stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewValidationSteps {
    private static final Logger logger = LogManager.getLogger(NewValidationSteps.class);
    private final SharedState sharedState = SharedState.getInstance();

    @Then("the response body should contain a JSON object with the key {string}")
    public void theResponseBodyShouldContainAKey(String key) {
        Response response = sharedState.getResponse();
        boolean hasKey = response.jsonPath().getMap("$").containsKey(key);
        assertTrue(hasKey, "Response body does not contain the key: " + key);
        logger.info("Key '" + key + "' is present in the response body.");
        ExtentReportUtils.logInfo("Key '" + key + "' is present in the response body.");
    }

    @Then("the {string} value should be a positive integer")
    public void theValueShouldBeAPositiveInteger(String key) {
        Response response = sharedState.getResponse();
        int value = response.jsonPath().getInt(key);
        assertTrue(value > 0, "The value of '" + key + "' is not a positive integer: " + value);
        logger.info("The value of '" + key + "' is a positive integer: " + value);
        ExtentReportUtils.logInfo("The value of '" + key + "' is a positive integer: " + value);
    }
}