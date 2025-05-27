package stepdefinitions;

import builders.ProductPayloadBuilder;
import config.EnvironmentConfig;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;
import utils.RequestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateProductSteps
{
    private static final Logger logger = LogManager.getLogger(CreateProductSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    private Response response;

    @When("I send a POST request to the endpoint with the payload")
    public void iSendAPOSTRequestToTheEndpointWithThePayload() {
        /*System.out.println("Full Endpoint: " + fullEndpoint); // Debug statement
        System.out.println("Request Body Before POST: " + requestBody); // Debug statement
        response = RequestUtils.sendPostRequest(fullEndpoint, requestBody);*/
        //System.out.println("Full Endpoint: " + sharedState.getFullEndpoint()); // Debug statement
        //System.out.println("Request Body Before POST: " + sharedState.getRequestBody()); // Debug statement
        logger.info("Full Endpoint: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint: " + sharedState.getFullEndpoint());
        logger.info("Request Body Before POST: " + sharedState.getRequestBody());
        ExtentReportUtils.logInfo("Request Body Before POST: " + sharedState.getRequestBody());
        response = RequestUtils.sendPostRequest(sharedState.getFullEndpoint(), sharedState.getRequestBody());

        sharedState.setResponse(response); // Set the response body dynamically
        //System.out.println("Response Body: " + sharedState.getResponse().asPrettyString());
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
}
