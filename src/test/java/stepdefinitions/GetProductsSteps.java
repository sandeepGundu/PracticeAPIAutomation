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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsSteps
{
    private static final Logger logger = LogManager.getLogger(CreateProductSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    private Response response;

    @When("I send a GET request to the endpoint")
    public void iSendAGETRequestToTheEndpoint() {
        logger.info("Full Endpoint: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint: " + sharedState.getFullEndpoint());
        response = RequestUtils.sendGetRequest(sharedState.getFullEndpoint());

        sharedState.setResponse(response); // Set the response body dynamically
        logger.info("Response Body: " + sharedState.getResponse().asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + sharedState.getResponse().asPrettyString());
    }

    @And("the response should contain a list of products")
    public void theResponseShouldContainAListOfProducts() {
        List<String> titles = response.jsonPath().getList("title");
        logger.info("List of products titles: " + titles);
        ExtentReportUtils.logInfo("List of products titles: " + titles);
        assert titles != null && !titles.isEmpty() : "No product titles found in the response!";
        assert response.jsonPath().getList("$").size() > 0 : "No products found in the response!";
    }

    @When("I send a GET request to the endpoint with path param {string}")
    public void iSendAGETRequestToTheEndpointWithPathParamId(String id) {
        logger.info("Full Endpoint: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint: " + sharedState.getFullEndpoint());
        response = RequestUtils.sendGetRequest(sharedState.getFullEndpoint(), id);

        sharedState.setResponse(response); // Set the response body dynamically

        // Check the Content-Type header
        String contentType = response.getHeader("Content-Type");
        logger.info("Content-Type: " + contentType);
        ExtentReportUtils.logInfo("Content-Type: " + contentType);

        if (contentType == null || !contentType.contains("application/json")) {
            throw new AssertionError("Response is not in JSON format! Content-Type: " + contentType);
        }
        // Log the raw response body for debugging
        String rawResponseBody = sharedState.getResponse().asString();
        logger.info("Raw Response Body: " + rawResponseBody);
        ExtentReportUtils.logInfo("Raw Response Body: " + rawResponseBody);

        // Check if the response body is empty
        if (rawResponseBody == null || rawResponseBody.isEmpty()) {
            ExtentReportUtils.logFail("Response body is empty!");
            throw new AssertionError("Response body is empty!");
        }

        // Log the parsed response body
        logger.info("DEBUG Parsed Response Body: " + sharedState.getResponse().asPrettyString());
        ExtentReportUtils.logInfo("Parsed Response Body: " + sharedState.getResponse().asPrettyString());
    }

    //the response should contain the product id details
    @And("the response should contain the product id details")
    public void theResponseShouldContainTheProductIdDetails() {
        String id = response.jsonPath().getString("id");
        logger.info("product id: " + id);
        ExtentReportUtils.logInfo("product id: " + id);
        assert id != null : "No product id found in the response!";
        //assert response.jsonPath().getList("$").size() > 0 : "No products found in the response!";
    }
}
