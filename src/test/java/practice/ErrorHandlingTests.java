package practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorHandlingTests
{
    @Test
    public void testGetAllProducts()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Send GET request to /products endpoint
        Response response = RestAssured
                .given()
                .when()
                .post("/product")
                .then()
                .extract().response();

        // Validate the status code
        int statusCode = response.getStatusCode();
        /*if(statusCode != 200)
        {
            System.err.println("Unexpected status code: " + statusCode);
            System.err.println("Response Body: " + response.asPrettyString());
        }
        else if (statusCode == 400 || statusCode == 415) {
            System.err.println("Bad Request: " + response.asPrettyString());
        } else if (statusCode == 500) {
            System.err.println("Internal Server Error: " + response.asPrettyString());
        }*/
        handleError(response);

        assertTrue(statusCode == 200 || statusCode == 201, "Unexpected status code: " + statusCode);
        //assertEquals(200, statusCode, "Status code should be 200");
    }
    @Test
    public void testCreateProductWithErrorHandling()
    {
        RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = """
            {
                "title": "Invalid Product",
                "price": -19.99, // Invalid price
                "description": "This product has an invalid price",
                "image": "http://i.pravatar.cc",
                "category": "electronics"
            }
        """;

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/products")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        /*if(statusCode != 200 && statusCode != 201)
        {
            System.err.println("Unexpected status code: " + statusCode);
            System.err.println("Response Body: " + response.asPrettyString());
        }
        else if (statusCode == 400 || statusCode == 415) {
            System.err.println("Bad Request: " + response.asPrettyString());
        } else if (statusCode == 500) {
            System.err.println("Internal Server Error: " + response.asPrettyString());
        }*/
        handleError(response);

        assertTrue(statusCode == 200 || statusCode == 201, "Unexpected status code: " + statusCode);
        //assertEquals(200, statusCode, "Status code should be 200");
    }

    @Test
    public void testUpdateProductWithErrorHandling()
    {
        RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = """
            {
                "title": "Invalid Product",
                "price": -19.99, // Invalid price
                "description": "This product has an invalid price",
                "image": "http://i.pravatar.cc",
                "category": "electronics"
            }
        """;

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/products/9")
                .then()
                .extract().response();

        handleError(response);
    }

    @Test
    public void testDeleteProduct()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = RestAssured
                .given()
                .when()
                .delete("/product/5")
                .then()
                .extract().response();

        handleError(response);
    }

    public void handleError(Response response) {
        /*int statusCode = response.getStatusCode();
        if (statusCode != 200 && statusCode != 201) {
            System.err.println("Unexpected status code: " + statusCode);
            System.err.println("Response Body: " + response.asPrettyString());
        }
        else if (statusCode == 400 || statusCode == 415) {
            System.err.println("Bad Request: " + response.asPrettyString());
        } else if (statusCode == 500) {
            System.err.println("Internal Server Error: " + response.asPrettyString());
        }
        assertEquals(200, statusCode, "Status code should be 200");*/

        int statusCode = response.getStatusCode();
        if (statusCode == 400) {
            System.err.println("Bad Request: " + response.asPrettyString());
        } else if (statusCode == 401) {
            System.err.println("Unauthorized: " + response.asPrettyString());
        } else if (statusCode == 404) {
            System.err.println("Not Found: " + response.asPrettyString());
        } else if (statusCode == 500) {
            System.err.println("Internal Server Error: " + response.asPrettyString());
        }
        assertTrue(statusCode == 200 || statusCode == 201, "Unexpected status code: " + statusCode);
    }
}
