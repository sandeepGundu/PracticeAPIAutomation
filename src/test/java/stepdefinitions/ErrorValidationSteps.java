package stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorValidationSteps {

    private static final Logger logger = LogManager.getLogger(ErrorValidationSteps.class);
    private final SharedState sharedState = SharedState.getInstance();

    @Then("the response body should contain an error message")
    public void theResponseBodyShouldContainAnErrorMessage() {
        Response response = sharedState.getResponse();
        String responseBody = response.getBody().asString();

        // Validate that the response body contains an error message
        boolean containsErrorMessage = responseBody.toLowerCase().contains("error") || responseBody.toLowerCase().contains("invalid");

        assertTrue(containsErrorMessage, "Response body does not contain an error message! Actual response body: " + responseBody);

        logger.info("Response body contains an error message as expected.");
        ExtentReportUtils.logInfo("Response body contains an error message as expected.");
    }
}