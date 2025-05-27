package stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.RequestUtils;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorHandlingSteps {

    private static final Logger logger = LogManager.getLogger(ErrorHandlingSteps.class);
    private final SharedState sharedState = SharedState.getInstance();

    @When("I send a GET request to the endpoint with an invalid query parameter {string}")
    public void iSendAGetRequestToTheEndpointWithAnInvalidQueryParameter(String queryParam) {
        String fullEndpoint = sharedState.getFullEndpoint();
        logger.info("Sending GET request to endpoint: " + fullEndpoint + " with query parameter: " + queryParam);
        ExtentReportUtils.logInfo("Sending GET request to endpoint: " + fullEndpoint + " with query parameter: " + queryParam);

        // Append query parameter to the endpoint
        String endpointWithQuery = fullEndpoint + "?" + queryParam;

        // Send GET request
        Response response = RequestUtils.sendGetRequest(endpointWithQuery);

        // Store the response in shared state
        sharedState.setResponse(response);

        logger.info("Response received: " + response.asString());
        ExtentReportUtils.logInfo("Response received: " + response.asString());
    }

    @And("the response body should contain an error message")
    public void theResponseBodyShouldContainAnErrorMessage() {
        Response response = sharedState.getResponse();
        String responseBody = response.getBody().asString();

        logger.info("Validating response body contains an error message");
        ExtentReportUtils.logInfo("Validating response body contains an error message");

        // Validate the response body contains an error message
        assertTrue(responseBody.contains("error"), "Error message not found in the response body!");

        logger.info("Error message validation passed");
        ExtentReportUtils.logInfo("Error message validation passed");
    }
}