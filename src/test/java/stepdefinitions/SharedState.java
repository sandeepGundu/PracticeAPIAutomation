package stepdefinitions;

import io.restassured.response.Response;

public class SharedState
{
    private static SharedState instance;

    private String fullEndpoint;
    private String requestBody;
    private Response response;

    private SharedState() {
        // Private constructor to prevent instantiation
    }

    public static synchronized SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    public String getFullEndpoint() {
        return fullEndpoint;
    }

    public void setFullEndpoint(String fullEndpoint) {
        this.fullEndpoint = fullEndpoint;
        System.out.println("Full Endpoint Set: " + fullEndpoint);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response requestBody) {
        this.response = requestBody;
    }
}
