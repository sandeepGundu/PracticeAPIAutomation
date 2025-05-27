package stepdefinitions;

import io.cucumber.java.en.And;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartsSteps
{
    private static final Logger logger = LogManager.getLogger(CartsSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    private Response response;

    @And("the response should contain a list of carts")
    public void theResponseShouldContainAListOfCarts() {
        response = sharedState.getResponse();
        List<String> ids = response.jsonPath().getList("id");
        logger.info("List of cart ids: " + ids);
        ExtentReportUtils.logInfo("List of cart ids: " + ids);
        assert ids != null && !ids.isEmpty() : "No Cart ids found in the response!";
        assert response.jsonPath().getList("$").size() > 0 : "No Carts found in the response!";
    }

    @And("the response should contain the created cart details")
    public void theResponseShouldContainTheCreatedCartDetails() {
        logger.info("Validating the response...");
        ExtentReportUtils.logInfo("Validating the response...");
        response = sharedState.getResponse();
        logger.debug("Response Body: " + response.asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + response.asPrettyString());

        if (response.jsonPath().getString("userId") == null) {
            throw new AssertionError("UserId not found in the response!");
        }
        assert response.jsonPath().getString("userId") != null : "UserId not found in the response!";

        JsonPath requestPayloadJson = new JsonPath(sharedState.getRequestBody());
        int productCount = requestPayloadJson.getList("products").size();
        logger.info("Actual Product Count: " + productCount);
        ExtentReportUtils.logInfo("Actual Product Count: " + productCount);

        for(int i = 0; i < productCount; i++)
        {
            int expectedProductId = requestPayloadJson.getInt("products[" + i + "].productId");
            logger.info("Expected ProductId: " + expectedProductId);
            ExtentReportUtils.logInfo("Expected ProductId: " + expectedProductId);
            int expectedProductQuantity = requestPayloadJson.getInt("products[" + i + "].quantity");
            logger.info("Expected Product Quantity: " + expectedProductQuantity);
            ExtentReportUtils.logInfo("Expected Product Quantity: " + expectedProductQuantity);

            // Validate the price in the response
            int actualProductId = response.jsonPath().getInt("products[" + i + "].productId");
            logger.info("Actual ProductId: " + actualProductId);
            ExtentReportUtils.logInfo("Actual ProductId: " + actualProductId);
            int actualProdQuantity = response.jsonPath().getInt("products[" + i + "].quantity");
            logger.info("Actual Product Quantity: " + actualProdQuantity);
            ExtentReportUtils.logInfo("Actual Product Quantity: " + actualProdQuantity);
            assertEquals(expectedProductId, actualProductId, "ProductId mismatch!");
            assertEquals(expectedProductQuantity, actualProdQuantity, "Product Quantity mismatch!");
        }

        logger.info("Product validation successful!");
        ExtentReportUtils.logInfo("Product validation successful!");
    }

    @And("the response should contain the cart id details")
    public void theResponseShouldContainTheCartIdDetails() {
        response = sharedState.getResponse();
        String id = response.jsonPath().getString("id");
        logger.info("Cart id: " + id);
        ExtentReportUtils.logInfo("Cart id: " + id);
        assert id != null : "No Cart id found in the response!";
        //assert response.jsonPath().getList("$").size() > 0 : "No products found in the response!";

    }

    @And("the response should contain the updated cart details")
    public void theResponseShouldContainTheUpdatedCartDetails() {
        response = sharedState.getResponse();
        logger.info("Response Body: " + response.asPrettyString());
        ExtentReportUtils.logInfo("Response Body: " + response.asPrettyString());
        int productCount = response.jsonPath().getList("products").size();
        logger.info("Total number of products: " + productCount);
        logger.info("Updated product title: " + response.jsonPath().getString("products[0].title"));
        ExtentReportUtils.logInfo("Updated product title: " + response.jsonPath().getString("products[0].title"));
        if (response.jsonPath().getString("products[0].title") == null) {
            throw new AssertionError("Product title not found in the response!");
        }
        assert response.jsonPath().getString("products[0].title") != null : "Updated product title not found in the response!";

        logger.info("Updated Product Title: " + response.jsonPath().getString("products[0].title"));
        ExtentReportUtils.logInfo("Updated Product Title: " + response.jsonPath().getString("products[0].title"));
    }

    @And("the response should confirm the cart was deleted")
    public void theResponseShouldConfirmTheProductWasDeleted() {
        /*if (!response.jsonPath().getString("message").contains("deleted")) {
            throw new AssertionError("Product deletion not confirmed!");
        }*/
        //assert response.jsonPath().getString("message").contains("deleted") : "Product deletion not confirmed!";
        assert response.jsonPath().getInt("id") != 1 : "Deleted product ID mismatch!";
    }

}
