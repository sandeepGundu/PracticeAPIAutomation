package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExtentReportUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayloadSteps
{
    private static final Logger logger = LogManager.getLogger(PayloadSteps.class);

    private final SharedState sharedState = SharedState.getInstance();

    @And("the request payload is:")
    public void theRequestPayloadIs(String payload) {
        sharedState.setRequestBody(payload); // Set the request payload dynamically
        logger.debug("Request Payload Set: " + sharedState.getRequestBody());
        ExtentReportUtils.logInfo("Request Payload Set: " + sharedState.getRequestBody());
    }
}
