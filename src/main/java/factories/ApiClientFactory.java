package factories;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ApiClientFactory
{
    public static RequestSpecification createDefaultClient(String endpoint) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .baseUri(endpoint);
    }

    public static RequestSpecification createCustomClient(String endpoint, String token) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .baseUri(endpoint);
    }
}
