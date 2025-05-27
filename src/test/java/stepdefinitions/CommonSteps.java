package stepdefinitions;

import config.EnvironmentConfig;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

public class CommonSteps
{
    private static final Logger logger = LogManager.getLogger(CommonSteps.class);
    private final SharedState sharedState = SharedState.getInstance();
    @Given("the API endpoint is {string}")
    public void theApiEndpointIs(String endpoint) {

        // Replace placeholders (e.g., <id>) with actual values if needed
        // Replace placeholders (e.g., <id>) with actual values if needed
        if (endpoint.contains("<id>")) {
            String id = "1"; // Replace with dynamic value if needed
            endpoint = endpoint.replace("<id>", id);
            //endpoint = endpoint.replace("<id>", "1"); // Replace <id> with actual value (e.g., 1)
        }

        String fullEndpoint = EnvironmentConfig.BASE_URL + endpoint;
        logger.info("Full Endpoint: " + fullEndpoint);
        ExtentReportUtils.logInfo("Full Endpoint: " + fullEndpoint);
        //System.out.println("Full Endpoint: " + fullEndpoint);

        sharedState.setFullEndpoint(fullEndpoint); // Set the request payload dynamically
        logger.info("Full Endpoint Set: " + sharedState.getFullEndpoint());
        ExtentReportUtils.logInfo("Full Endpoint Set: " + sharedState.getFullEndpoint());
        //System.out.println("Full Endpoint Set: " + sharedState.getFullEndpoint()); // Debug statement
    }
}
