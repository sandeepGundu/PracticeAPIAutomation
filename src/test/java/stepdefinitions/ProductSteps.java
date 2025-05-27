package stepdefinitions;

import builders.ProductPayloadBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;
import utils.RequestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSteps
{
    private static final Logger logger = LogManager.getLogger(ProductSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    private Response response;

    @When("I send a POST request to the endpoint with the payload")
    public void iSendAPOSTRequestToTheEndpointWithThePayload() {
        logger.info("Full Endpoint: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint: " + sharedState.getFullEndpoint());
        logger.info("Request Body Before POST: " + sharedState.getRequestBody());
        ExtentReportUtils.logInfo("Request Body Before POST: " + sharedState.getRequestBody());
        response = RequestUtils.sendPostRequest(sharedState.getFullEndpoint(), sharedState.getRequestBody());

        sharedState.setResponse(response); // Set the response body dynamically
        logger.info("Response Body: " + sharedState.getResponse().asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + sharedState.getResponse().asPrettyString());
    }

    @And("the response should contain the created product details")
    public void theResponseShouldContainTheCreatedProductDetails() {
        logger.info("Validating the response...");
        ExtentReportUtils.logInfo("Validating the response...");
        logger.debug("Response Body: " + response.asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + response.asPrettyString());

        if (response.jsonPath().getString("title") == null) {
            throw new AssertionError("Product title not found in the response!");
        }
        assert response.jsonPath().getString("title") != null : "Product title not found in the response!";
        assert response.jsonPath().getDouble("price") == 19.99 : "Product price mismatch!";

        // Parse the price from the request payload dynamically
        JsonPath requestPayloadJson = new JsonPath(sharedState.getRequestBody());
        double expectedPrice = requestPayloadJson.getDouble("price");
        logger.info("Product expected price: " + expectedPrice);
        ExtentReportUtils.logInfo("Product expected price: " + expectedPrice);
        String expectedProdTitle = requestPayloadJson.getString("title");
        logger.info("Product expected title: " + expectedProdTitle);
        ExtentReportUtils.logInfo("Product expected title: " + expectedProdTitle);

        // Validate the price in the response
        double actualPrice = response.jsonPath().getDouble("price");
        logger.info("Product actual price: " + actualPrice);
        ExtentReportUtils.logInfo("Product actual price: " + actualPrice);
        String actualProdTitle = response.jsonPath().getString("title");
        logger.info("Product actual title: " + actualProdTitle);
        ExtentReportUtils.logInfo("Product actual title: " + actualProdTitle);
        assertEquals(expectedPrice, actualPrice, "Product price mismatch!");
        assertEquals(expectedProdTitle, actualProdTitle, "Product title mismatch!");
        logger.info("Price validation successful!");
        ExtentReportUtils.logInfo("Price validation successful!");
    }

    @And("the request payload is built with title {string}, price {double}, description {string}, and category {string}")
    public void theRequestPayloadIsBuilt(String title, double price, String description, String category) {
        String payload = new ProductPayloadBuilder()
                .withTitle(title)
                .withPrice(price)
                .withDescription(description)
                .withCategory(category)
                .build();

        sharedState.setRequestBody(payload);
        ExtentReportUtils.logInfo("Request Payload Set: " + sharedState.getRequestBody());
        System.out.println("Request Payload Set: " + sharedState.getRequestBody());
    }

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
