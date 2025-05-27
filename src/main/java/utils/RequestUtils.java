package utils;

import clients.ApiClient;
import clients.DefaultApiClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import factories.ApiClientFactory;

public class RequestUtils
{
    private static final ApiClient apiClient = new DefaultApiClient();

    public static Response sendGetRequest(String endpoint) {

        /*RestAssured
                .given()
         */
        /*
        ApiClientFactory.createDefaultClient()
         */
        /*
        apiClient.getClient()
         */
        return ApiClientFactory.createDefaultClient(endpoint)
                .when()
                .get(endpoint)
                .then()
                .extract().response();
    }

    /**
     * Sends a GET request to the specified endpoint with optional path parameters.
     *
     * @param endpoint   The endpoint to send the request to.
     * @param pathParams The path parameters to be appended to the endpoint.
     * @return The response from the API.
     */
    /*public static Response sendGetRequest(String endpoint, Object... pathParams) {
        return ApiClientFactory.createDefaultClient(endpoint)
                .pathParams("id", pathParams) // Dynamically set path parameters
                .when()
                .get("/{id}") // Use the path parameter in the request
                .then()
                .extract().response();
    }*/

    /**
     * Sends a GET request to the specified endpoint with optional path parameters.
     *
     * @param endpoint   The endpoint to send the request to.
     * @param pathParams The path parameters to be appended to the endpoint.
     * @return The response from the API.
     */
    public static Response sendGetRequest(String endpoint, Object... pathParams) {
        // Dynamically construct the full endpoint with path parameters
        String fullEndpoint = endpoint;
        if (pathParams != null && pathParams.length > 0) {
            fullEndpoint = String.format("%s/%s", endpoint, pathParams[0]);
        }

        // Log the full endpoint for debugging
        System.out.println("Full Endpoint: " + fullEndpoint);

        // Send the GET request
        return ApiClientFactory.createDefaultClient(fullEndpoint)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response sendPostRequest(String endpoint, String requestBody) {

        if (requestBody == null || requestBody.isEmpty()) {
            throw new IllegalArgumentException("Request body cannot be null or empty");
        }

        /*RestAssured
                .given()
                .header("Content-Type", "application/json")*/
        /*
        ApiClientFactory.createDefaultClient()
         */
        /*
        apiClient.getClient()
         */

        return  ApiClientFactory.createDefaultClient(endpoint)
                .log().all()
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response sendPutRequest(String endpoint, String requestBody) {

        if (requestBody == null || requestBody.isEmpty()) {
            throw new IllegalArgumentException("Request body cannot be null or empty");
        }

        /*RestAssured
                .given()
                .header("Content-Type", "application/json")
         */
        /*
        ApiClientFactory.createDefaultClient()
         */
        /*
        apiClient.getClient()
         */
        return ApiClientFactory.createDefaultClient(endpoint)
                .body(requestBody)
                .when()
                .put(endpoint)
                .then()
                .extract().response();
    }

    public static Response sendDeleteRequest(String endpoint) {

        /*RestAssured
                .given()
         */
        /*
        ApiClientFactory.createDefaultClient()
         */
        /*
        apiClient.getClient()
         */
        return ApiClientFactory.createDefaultClient(endpoint)
                .when()
                .delete(endpoint)
                .then()
                .extract().response();
    }
}
