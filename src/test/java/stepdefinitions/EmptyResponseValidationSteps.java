package stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyResponseValidationSteps {

    private static final Logger logger = LogManager.getLogger(EmptyResponseValidationSteps.class);
    private final SharedState sharedState = SharedState.getInstance();

    @Then("the response body should contain an empty array or object")
    public void theResponseBodyShouldContainAnEmptyArrayOrObject() {
        Response response = sharedState.getResponse();
        String responseBody = response.getBody().asString();

        // Validate that the response body is either an empty array or an empty object
        boolean isEmptyArray = "[]".equals(responseBody.trim());
        boolean isEmptyObject = "{}".equals(responseBody.trim());

        assertTrue(isEmptyArray || isEmptyObject, "Response body is not empty! Actual response body: " + responseBody);

        logger.info("Response body is an empty array or object as expected.");
        ExtentReportUtils.logInfo("Response body is an empty array or object as expected.");
    }
}