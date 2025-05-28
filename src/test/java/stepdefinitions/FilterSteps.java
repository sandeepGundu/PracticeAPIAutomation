package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;
import utils.RequestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterSteps
{
    private static final Logger logger = LogManager.getLogger(FilterSteps.class);
    private final SharedState sharedState = SharedState.getInstance();

    @When("I send a GET request to the endpoint with the query parameter {string}")
    public void iSendAGetRequestToTheEndpointWithTheQueryParameter(String queryParam) {
        String fullEndpoint = sharedState.getFullEndpoint() + "?" + queryParam;
        logger.info("Sending GET request to: " + fullEndpoint);
        ExtentReportUtils.logInfo("Sending GET request to: " + fullEndpoint);

        Response response = RequestUtils.sendGetRequest(fullEndpoint);
        sharedState.setResponse(response);

        logger.info("Response received with status code: " + response.getStatusCode());
        ExtentReportUtils.logInfo("Response received with status code: " + response.getStatusCode());
    }

    @Then("the response body should contain a JSON array")
    public void theResponseBodyShouldContainAJSONArray() {
        Response response = sharedState.getResponse();
        List<Map<String, Object>> jsonArray = response.jsonPath().getList("$");

        assertTrue(jsonArray != null && !jsonArray.isEmpty(), "Response body is not a JSON array or is empty!");
        logger.info("Response body contains a JSON array with size: " + jsonArray.size());
        ExtentReportUtils.logInfo("Response body contains a JSON array with size: " + jsonArray.size());
    }

    @Then("each object in the array should have the {string} value as {string}")
    public void eachObjectInTheArrayShouldHaveTheValueAs(String key, String expectedValue) {
        Response response = sharedState.getResponse();
        List<Map<String, Object>> jsonArray = response.jsonPath().getList("$");

        for (Map<String, Object> obj : jsonArray) {
            assertEquals(expectedValue, obj.get(key), "Object does not have the expected value for key: " + key);
        }

        logger.info("All objects in the array have the key '" + key + "' with value '" + expectedValue + "'");
        ExtentReportUtils.logInfo("All objects in the array have the key '" + key + "' with value '" + expectedValue + "'");
    }

}
