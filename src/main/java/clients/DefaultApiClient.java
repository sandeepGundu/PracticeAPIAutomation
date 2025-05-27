package clients;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class DefaultApiClient implements ApiClient
{
    @Override
    public RequestSpecification getClient() {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .baseUri("https://fakestoreapi.com");
    }
}
