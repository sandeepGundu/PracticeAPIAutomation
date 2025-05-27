package practice;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductApiTests
{
    @BeforeAll
    public static void setup()
    {
        // Set the base URL for all requests
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    public Response sendGetRequest(String endPoint)
    {
        return RestAssured
                .given()
                //.log().all()
                .when()
                .get(endPoint)
                .then()
                //.log().all()
                .extract().response();
    }

    public Response sendGetRequestWithQueryParam(String endPoint, int queryParam)
    {
        return RestAssured
                .given()
                .pathParam("id", queryParam)
                .log().all()
                .when()
                //.queryParam("id", queryParam)
                .get(endPoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response sendPostRequest(String endPoint, String requestBody)
    {
        return RestAssured
                .given()
                //.log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(endPoint)
                .then()
                //.log().all()
                .extract().response();
    }

    public Response sendPutResponse(String endPoint, String requestBody)
    {
        return RestAssured
                .given()
                //.log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put(endPoint)
                .then()
                //.log().all()
                .extract().response();
    }

    public Response sendDeleteRequest(String endPoint)
    {
        return RestAssured
                .given()
                //.log().all()
                .when()
                .delete(endPoint)
                .then()
                //.log().all()
                .extract().response();
    }

    @Test
    public void testGetAllProducts()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Send GET request to /products endpoint
        Response response = sendGetRequest("/products");

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
        Response response = sendPostRequest("/products", requestBody);

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());

        // Validate the response body
        String title = response.jsonPath().getString("title");
        assertEquals("New product One", title, "Product title should match");
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

        Response response = sendPutResponse("/products/7", requestBody);

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());

        // Validate the response body
        String title = response.jsonPath().getString("title");
        assertEquals("Updated product", title, "Product title should match");
    }

    @Test
    public void testDeleteProduct()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = sendDeleteRequest("/products/7");

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());

        // Validate the response body
        int id = response.jsonPath().getInt("id");
        assertEquals(7, id, "Deleted product ID should match");
    }

    @Test
    public void testCreateProductWithDynamicData()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Generate dynamic data using Faker
        Faker faker = new Faker();
        String title = faker.commerce().productName();
        double price = Double.parseDouble(faker.commerce().price());
        String description = faker.lorem().sentence();
        String category = faker.commerce().department();

        String requestBody = String.format("""
        {
            "title": "%s",
            "price": %.2f,
            "description": "%s",
            "image": "http://i.pravatar.cc",
            "category": "%s"
        }
    """, title, price, description, category);

        Response response = sendPostRequest("/products", requestBody);

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @Test
    public void testUpdateProductWithDynamicData()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Generate dynamic data using Faker
        Faker faker = new Faker();
        String title = faker.commerce().productName();
        double price = Double.parseDouble(faker.commerce().price());
        String description = faker.lorem().sentence();
        String category = faker.commerce().department();

        String requestBody = String.format("""
            {
                "title": "%s",
                "price": %.2f,
                "description": "%s",
                "image": "http://i.pravatar.cc",
                "category": "%s"
            }
        """, title, price, description, category);

        Response response = sendPutResponse("/products/7", requestBody);

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @Test
    public void testGetAProduct()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Send GET request to /products endpoint
        Response response = sendGetRequestWithQueryParam("/products", 1);

        // Validate the status code
        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        // Print the response body
        System.out.println("Response Body: " + response.asString());
        System.out.println("******************");
        System.out.println("Response Body: " + response.asPrettyString());
    }
}
