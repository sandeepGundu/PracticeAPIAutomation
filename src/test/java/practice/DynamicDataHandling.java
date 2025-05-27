package practice;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicDataHandling
{
    ProductApiTests productApiTests = new ProductApiTests();
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

        Response response = productApiTests.sendPostRequest("/products", requestBody);
        System.out.println("Generated Data: Title = " + title + ", Price = " + price + ", Description = " + description + ", Category = " + category);

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

        Response response = productApiTests.sendPutResponse("/products/7", requestBody);
        System.out.println("Updated Data: Title = " + title + ", Price = " + price + ", Description = " + description + ", Category = " + category);

        assertEquals(200, response.getStatusCode(), "Status code should be 200");
        System.out.println("Response Body: " + response.asPrettyString());
    }

}
