package practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsTest
{
    @Test
    public void testGetAllProducts()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Send GET request to /products endpoint
        Response response = RestAssured
                .given()
                .when().get("/products")
                .then().extract().response();

        // Validate the status code
        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        // Print the response body
        System.out.println("Response Body: " + response.asString());
        System.out.println("******************");
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @Test
    public void testCreateProduct()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = """
        {
            "title": "New product One",
            "price": 19.19,
            "description": "A new product for testing purpose",
            "image": "http://i.pravatar.cc",
            "category": "electronic"
        }
    """;

        // Send GET request to /products endpoint
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when().post("/products")
                .then().extract().response();

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void testUpdateProduct()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = """
        {
            "title": "Updated product",
            "price": 39.19,
            "description": "Updated product for testing purpose",
            "image": "http://i.pravatar.cc",
            "category": "electronic"
        }
    """;

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/products/7")
                .then()
                .extract().response();

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void testDeleteProduct()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = RestAssured
                .given()
                .when()
                .delete("/products/7")
                .then()
                .extract().response();

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());
    }
}
