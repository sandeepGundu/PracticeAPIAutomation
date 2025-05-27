package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;
import utils.RequestUtils;

public class FetchAllUsersSteps {
    private static final Logger logger = LogManager.getLogger(FetchAllUsersSteps.class);
    private Response response;

    @Given("the API endpoint is "/users"")
    public void theApiEndpointIs() {
        // Set the endpoint in shared state
        SharedState.getInstance().setFullEndpoint("/users");
        logger.info("API endpoint set to /users");
        ExtentReportUtils.logInfo("API endpoint set to /users");
    }

    @When("I send a GET request to the endpoint")
    public void iSendAGETRequestToTheEndpoint() {
        logger.info("Sending GET request to endpoint: " + SharedState.getInstance().getFullEndpoint());
        response = RequestUtils.sendGetRequest(SharedState.getInstance().getFullEndpoint());
        SharedState.getInstance().setResponse(response);
        logger.info("Response received: " + response.asPrettyString());
        ExtentReportUtils.logInfo("Response received: " + response.asPrettyString());
    }

    @And("the response status code should be 200")
    public void theResponseStatusCodeShouldBe200() {
        int statusCode = response.getStatusCode();
        logger.info("Response status code: " + statusCode);
        ExtentReportUtils.logInfo("Response status code: " + statusCode);
        assert statusCode == 200 : "Expected status code 200 but got " + statusCode;
    }

    @And("the response should contain a list of users")
    public void theResponseShouldContainAListOfUsers() {
        assert response.jsonPath().getList("users").size() > 0 : "No users found in the response!";
        logger.info("Users found in response: " + response.jsonPath().getList("users"));
        ExtentReportUtils.logInfo("Users found in response: " + response.jsonPath().getList("users"));
    }
}