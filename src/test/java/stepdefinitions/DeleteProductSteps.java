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

public class DeleteProductSteps
{
    private static final Logger logger = LogManager.getLogger(DeleteProductSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    private Response response;
    //private String baseUrl;

    /*@Given("the API endpoint is {string}")
    public void theApiEndpointIs(String endpoint) {
        // Retrieve the base URL dynamically from the environment.properties file
        baseUrl = EnvironmentConfig.getBaseUrl() + endpoint;
        System.out.println("Base URL: " + baseUrl);
        //EnvironmentConfig.BASE_URL = endpoint; // Set the base URL dynamically
    }*/

    @When("I send a DELETE request to the endpoint")
    public void iSendADELETERequestToTheEndpoint() {
        logger.info("Full Endpoint: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint: " + sharedState.getFullEndpoint());
        logger.info("Request Body Before POST: " + sharedState.getRequestBody());
        ExtentReportUtils.logInfo("Request Body Before POST: " + sharedState.getRequestBody());
        //System.out.println("Full Endpoint: " + sharedState.getFullEndpoint()); // Debug statement
        response = RequestUtils.sendDeleteRequest(sharedState.getFullEndpoint());

        sharedState.setResponse(response); // Set the response body dynamically
        //System.out.println("Response Body: " + sharedState.getResponse().asPrettyString());
        logger.info("Response Body: " + sharedState.getResponse().asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + sharedState.getResponse().asPrettyString());
    }

    @And("the response should confirm the product was deleted")
    public void theResponseShouldConfirmTheProductWasDeleted() {
        /*if (!response.jsonPath().getString("message").contains("deleted")) {
            throw new AssertionError("Product deletion not confirmed!");
        }*/
        //assert response.jsonPath().getString("message").contains("deleted") : "Product deletion not confirmed!";
        assert response.jsonPath().getInt("id") != 1 : "Deleted product ID mismatch!";
    }


}
