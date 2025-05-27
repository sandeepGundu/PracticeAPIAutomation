package practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedTests
{
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @ParameterizedTest
    @CsvSource({
            "Product A, 19.99, 'Description for Product A', electronics",
            "Product B, 29.99, 'Description for Product B', jewelery",
            "Product C, 39.99, 'Description for Product C', men's clothing"
    })
    public void testCreateProductWithParameters(String title, double price, String description, String category)
    {
        //RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = String.format("""
            {
                "title": "%s",
                "price": %.2f,
                "description": "%s",
                "image": "http://i.pravatar.cc",
                "category": "%s"
            }
        """, title, price, description, category);

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/products")
                .then()
                .extract().response();
        System.out.println("Generated Data: Title = " + title + ", Price = " + price + ", Description = " + description + ", Category = " + category);

        assertEquals(title, response.jsonPath().getString("title"), "Title should match");
        assertEquals(price, response.jsonPath().getDouble("price"), "Price should match");

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @ParameterizedTest
    @CsvSource({
            "3, Updated Product A, 10.99, 'Description for Updated Product A', jewelery",
            "5, Updated Product B, 20.99, 'Description for Updated Product B', men's clothing",
            "7, Updated Product C, 30.99, 'Description for Updated Product C', electronics"
    })
    public void testUpdateProductWithDynamicData(int id, String title, double price, String description, String category)
    {
        // Base URL of the API
        //RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = String.format("""
            {
                "title": "%s",
                "price": %.2f,
                "description": "%s",
                "image": "http://i.pravatar.cc",
                "category": "%s"
            }
        """, title, price, description, category);

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/products/" + id)
                .then()
                .extract().response();

        System.out.println("Updated Data: Title = " + title + ", Price = " + price + ", Description = " + description + ", Category = " + category);

        assertEquals(title, response.jsonPath().getString("title"), "Title should match");
        assertEquals(price, response.jsonPath().getDouble("price"), "Price should match");

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @ParameterizedTest
    @CsvSource({
            "3",
            "5",
            "7"
    })
    public void testDeleteProductWithDynamicData(int id)
    {
        // Base URL of the API
        //RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = RestAssured
                .given()
                .when()
                .delete("/products/" + id)
                .then()
                .extract().response();

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asString());

        // Validate the response body
        int id_Actual = response.jsonPath().getInt("id");
        assertEquals(id, id_Actual,"Deleted product ID should match");
    }

    static Stream<Arguments> provideProductData() {
        return Stream.of(
                Arguments.of("Product A", 19.99, "Description for Product A", "electronics"),
                Arguments.of("Product B", 29.99, "Description for Product B", "jewelery"),
                Arguments.of("Product C", 39.99, "Description for Product C", "men's clothing")
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductData")
    public void testCreateProductWithMethodSource(String title, double price, String description, String category)
    {
        //RestAssured.baseURI = "https://fakestoreapi.com";

        String requestBody = String.format("""
            {
                "title": "%s",
                "price": %.2f,
                "description": "%s",
                "image": "http://i.pravatar.cc",
                "category": "%s"
            }
        """, title, price, description, category);

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/products")
                .then()
                .extract().response();
        System.out.println("Generated Data: Title = " + title + ", Price = " + price + ", Description = " + description + ", Category = " + category);

        assertEquals(title, response.jsonPath().getString("title"), "Title should match");
        assertEquals(price, response.jsonPath().getDouble("price"), "Price should match");

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asPrettyString());
    }
}
