package stepdefinitions;

import config.EnvironmentConfig;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;
import utils.RequestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateProductSteps
{
    private static final Logger logger = LogManager.getLogger(UpdateProductSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    private Response response;

    @When("I send a PUT request to the endpoint with the payload")
    public void iSendAPUTRequestToTheEndpointWithThePayload() {
        logger.info("Full Endpoint: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint: " + sharedState.getFullEndpoint());
        logger.info("Request Body Before POST: " + sharedState.getRequestBody());
        ExtentReportUtils.logInfo("Request Body Before POST: " + sharedState.getRequestBody());
        response = RequestUtils.sendPutRequest(sharedState.getFullEndpoint(), sharedState.getRequestBody());

        sharedState.setResponse(response); // Set the response body dynamically
        logger.debug("Response Body: " + sharedState.getResponse().asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + sharedState.getResponse().asPrettyString());
        //System.out.println("Response Body: " + sharedState.getResponse().asPrettyString());
    }

    @And("the response should contain the updated product details")
    public void theResponseShouldContainTheUpdatedProductDetails() {
        if (response.jsonPath().getString("title") == null) {
            throw new AssertionError("Product title not found in the response!");
        }
        assert response.jsonPath().getString("title") != null : "Updated product title not found in the response!";

        logger.info("Updated Product Title: " + response.jsonPath().getString("title"));
        ExtentReportUtils.logInfo("Updated Product Title: " + response.jsonPath().getString("title"));
    }
}
