package stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Then("the response body should be empty")
    public void theResponseBodyShouldBeEmpty() {
        Response response = sharedState.getResponse();
        String responseBody = response.getBody().asString();

        assertTrue(responseBody == null || responseBody.isEmpty(),
                "Response body is not empty! Actual response body: " + responseBody);

        logger.info("Response body is empty as expected.");
        ExtentReportUtils.logInfo("Response body is empty as expected.");
    }

    @Then("the response body should contain an error message indicating {string} is required")
    public void theResponseBodyShouldContainAnErrorMessageIndicatingFieldIsRequired(String fieldName) {
        String responseBody = sharedState.getResponse().getBody().asString();
        logger.info("Response Body: " + responseBody);
        ExtentReportUtils.logInfo("Response Body: " + responseBody);

        String expectedErrorMessage = String.format("\"%s\" is required", fieldName);
        assertTrue(responseBody.contains(expectedErrorMessage),
                "Expected error message not found in response body!");
    }
}
