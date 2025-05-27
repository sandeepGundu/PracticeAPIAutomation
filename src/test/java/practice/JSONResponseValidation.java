package practice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONResponseValidation
{
    ProductApiTests productApiTests = new ProductApiTests();

    @Test
    public void simpleJsonParsing()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "id": 1,
            "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            "price": 109.95,
            "description": "Your perfect pack for everyday use and walks in the forest.",
            "category": "men's clothing"
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        // Extract fields
        int id = jsonPath.getInt("id");
        String title = jsonPath.getString("title");
        double price = jsonPath.getDouble("price");
        String description = jsonPath.getString("description");
        String category = jsonPath.getString("category");

        // Print the extracted values
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Price: " + price);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
    }

    @Test
    public void nestedJsonParsing()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "id": 1,
            "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            "price": 109.95,
            "description": "Your perfect pack for everyday use and walks in the forest.",
            "category": "men's clothing",
            "rating": {
                "rate": 3.9,
                "count": 120
            }
        }
        """;

        JsonPath jsonPath = new JsonPath(jsonResponse);
        double rate = jsonPath.getDouble("rating.rate");
        int count = jsonPath.getInt("rating.count");

        System.out.println("Rating (Rate): " + rate);
        System.out.println("Rating (Count): " + count);
    }

    @Test
    public void complexJsonParsing()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "products": [
                {
                    "id": 1,
                    "title": "Product A",
                    "price": 109.95,
                    "rating": {
                        "rate": 3.9,
                        "count": 120
                    }
                },
                {
                    "id": 2,
                    "title": "Product B",
                    "price": 22.3,
                    "rating": {
                        "rate": 4.1,
                        "count": 259
                    }
                }
            ]
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        // Extract arrays
        List<String> titles = jsonPath.getList("products.title");
        List<Double> prices = jsonPath.getList("products.price");
        List<Double> rates = jsonPath.getList("products.rating.rate");

        // Print the extracted values
        System.out.println("Titles: " + titles);
        System.out.println("Prices: " + prices);
        System.out.println("Rates: " + rates);
    }

    @Test
    public void accessProductsArray()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "products": [
                {
                    "id": 1,
                    "title": "Product A",
                    "price": 109.95,
                    "rating": {
                        "rate": 3.9,
                        "count": 120
                    }
                },
                {
                    "id": 2,
                    "title": "Product B",
                    "price": 22.3,
                    "rating": {
                        "rate": 4.1,
                        "count": 259
                    }
                }
            ]
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        // Extract the size of the products array
        int productCount = jsonPath.getList("products").size();

        // Iterate through each product in the array
        for(int i = 0; i < productCount; i++)
        {
            // Extract fields for each product
            String title = jsonPath.getString("products[" + i + "].title");
            double price = jsonPath.getDouble("products[" + i + "].price");
            int count = jsonPath.getInt("products[" + i + "].rating.count");

            // Print the extracted fields
            System.out.println("Product " + (i + 1) + ":");
            System.out.println("Title: " + title);
            System.out.println("Price: " + price);
            System.out.println("Count: " + count);
            System.out.println("-------------------------");
        }
    }

    @Test
    public void accessProductsArrayAlternative()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "products": [
                {
                    "id": 1,
                    "title": "Product A",
                    "price": 109.95,
                    "rating": {
                        "rate": 3.9,
                        "count": 120
                    }
                },
                {
                    "id": 2,
                    "title": "Product B",
                    "price": 22.3,
                    "rating": {
                        "rate": 4.1,
                        "count": 259
                    }
                }
            ]
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        List<Map<String, Object>> productsList = jsonPath.getList("products");

        //Iterate through each product in the list
        for(Map<String , Object> product : productsList)
        {
            String title = (String) product.get("title");

            //float price = (Float) product.get("price");

            // Use Number to handle both Float and Double
            Number priceNumber = (Number) product.get("price");
            double price = priceNumber.doubleValue(); // Convert to double

            // Extract nested fields (rating.count)
            Map<String, Object> rating = (Map<String, Object>) product.get("rating");
            int count = (Integer) rating.get("count");

            // Print the extracted fields
            System.out.println("Title: " + title);
            System.out.println("Price: " + price);
            System.out.println("Count: " + count);
            System.out.println("-------------------------");
        }
    }

    @Test
    public void accessProductsArrayAlternative2()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "products": [
                {
                    "id": 1,
                    "title": "Product A",
                    "price": 109.95,
                    "rating": {
                        "rate": 3.9,
                        "count": 120
                    }
                },
                {
                    "id": 2,
                    "title": "Product B",
                    "price": 22.3,
                    "rating": {
                        "rate": 4.1,
                        "count": 259
                    }
                }
            ]
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        // Extract the products array as a list of maps
        List<Map<String, Object>> products = jsonPath.getList("products");

        // Iterate through each product in the list
        for (Map<String, Object> product : products) {
            String title = (String) product.get("title");

            // Use getFloat() or getDouble() to extract the price
            float price = jsonPath.getFloat("products[" + products.indexOf(product) + "].price");

            // Extract nested fields (rating.count)
            Map<String, Object> rating = (Map<String, Object>) product.get("rating");
            int count = (Integer) rating.get("count");

            // Print the extracted fields
            System.out.println("Title: " + title);
            System.out.println("Price: " + price);
            System.out.println("Count: " + count);
            System.out.println("-------------------------");
        }
    }

    @Test
    public void accessJsonArrayWithoutArrayName()
    {
        // Sample JSON response
        String jsonResponse = """
        [
            {
                "id": 1,
                "title": "Product A",
                "price": 109.95,
                "rating": {
                    "rate": 3.9,
                    "count": 120
                }
            },
            {
                "id": 2,
                "title": "Product B",
                "price": 22.3,
                "rating": {
                    "rate": 4.1,
                    "count": 259
                }
            }
        ]
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        // Access the first object (index 0)
        System.out.println("Accessing First Object:");
        int id1 = jsonPath.getInt("[0].id");
        String title1 = jsonPath.getString("[0].title");
        double price1 = jsonPath.getDouble("[0].price");
        double rate1 = jsonPath.getDouble("[0].rating.rate");
        int count1 = jsonPath.getInt("[0].rating.count");

        System.out.println("ID: " + id1);
        System.out.println("Title: " + title1);
        System.out.println("Price: " + price1);
        System.out.println("Rate: " + rate1);
        System.out.println("Count: " + count1);
        System.out.println("-------------------------");

        // Access the second object (index 1)
        System.out.println("Accessing Second Object:");
        int id2 = jsonPath.getInt("[1].id");
        String title2 = jsonPath.getString("[1].title");
        double price2 = jsonPath.getDouble("[1].price");
        double rate2 = jsonPath.getDouble("[1].rating.rate");
        int count2 = jsonPath.getInt("[1].rating.count");

        System.out.println("ID: " + id2);
        System.out.println("Title: " + title2);
        System.out.println("Price: " + price2);
        System.out.println("Rate: " + rate2);
        System.out.println("Count: " + count2);

    }

    @Test
    public void accessJsonArrayWithoutArrayNameDynamicIndex()
    {
            // Sample JSON response
            String jsonResponse = """
        [
            {
                "id": 1,
                "title": "Product A",
                "price": 109.95,
                "rating": {
                    "rate": 3.9,
                    "count": 120
                }
            },
            {
                "id": 2,
                "title": "Product B",
                "price": 22.3,
                "rating": {
                    "rate": 4.1,
                    "count": 259
                }
            }
        ]
        """;

            // Parse the JSON response using JsonPath
            JsonPath jsonPath = new JsonPath(jsonResponse);

            int itemCount = jsonPath.getList("$").size();
            System.out.println(itemCount);

            for(int i = 0; i < itemCount; i++)
            {
                int id = jsonPath.getInt("[" + i + "].id");
                String title = jsonPath.getString("[" + i + "].title");
                float price = jsonPath.getFloat("[" + i + "].price");
                float rate = jsonPath.getFloat("[" + i + "].rating.rate");

                System.out.println("ID: " + id);
                System.out.println("Title: " + title);
                System.out.println("Price: " + price);
                System.out.println("Rate: " + rate);
                System.out.println("-------------------------");
            }
            // Access the first object (index 0)
            /*System.out.println("Accessing First Object:");
            int id1 = jsonPath.getInt("[0].id");
            String title1 = jsonPath.getString("[0].title");
            double price1 = jsonPath.getDouble("[0].price");
            double rate1 = jsonPath.getDouble("[0].rating.rate");
            int count1 = jsonPath.getInt("[0].rating.count");

            System.out.println("ID: " + id1);
            System.out.println("Title: " + title1);
            System.out.println("Price: " + price1);
            System.out.println("Rate: " + rate1);
            System.out.println("Count: " + count1);
            System.out.println("-------------------------");

            // Access the second object (index 1)
            System.out.println("Accessing Second Object:");
            int id2 = jsonPath.getInt("[1].id");
            String title2 = jsonPath.getString("[1].title");
            double price2 = jsonPath.getDouble("[1].price");
            double rate2 = jsonPath.getDouble("[1].rating.rate");
            int count2 = jsonPath.getInt("[1].rating.count");

            System.out.println("ID: " + id2);
            System.out.println("Title: " + title2);
            System.out.println("Price: " + price2);
            System.out.println("Rate: " + rate2);
            System.out.println("Count: " + count2);*/


    }

    @Test
    public void parseLittleComplexJson()
    {
        String responseBody = """
                [
                  {
                    "id": 123,
                    "name": "Pankaj",
                    "permanent": true,
                    "address": {
                      "street": "Albany Dr",
                      "city": "San Jose",
                      "zipcode": 95129
                    },
                    "phoneNumbers": [
                      123456,
                      987654
                    ],
                    "role": "Manager",
                    "cities": [
                      "Los Angeles",
                      "New York"
                    ],
                    "properties": {
                      "age": "29 years",
                      "salary": "20000 USD"
                    }
                  },
                  {
                    "id": 456,
                    "name": "Panav",
                    "permanent": true,
                    "address": {
                      "street": "yyyy Dr",
                      "city": "San yyyy",
                      "zipcode": 95129
                    },
                    "phoneNumbers": [
                      654321,
                      456789
                    ],
                    "role": "Developer",
                    "cities": [
                      "YYY Angeles",
                      "Net York"
                    ],
                    "properties": {
                      "age": "19 years",
                      "salary": "1500 USD"
                    }
                  },
                  {
                    "id": 678,
                    "name": "Pramod",
                    "permanent": false,
                    "address": {
                      "street": "xxxx Dr",
                      "city": "San Fansisco",
                      "zipcode": 12312
                    },
                    "phoneNumbers": [
                      112233,
                      334455
                    ],
                    "role": "HR",
                    "cities": [
                      "Los xxxx",
                      "New York"
                    ],
                    "properties": {
                      "age": "39 years",
                      "salary": "10000 USD"
                    }
                  }
                ]
                """;

        JsonPath jsonPath = new JsonPath(responseBody);

        int itemCount = jsonPath.getList("$").size();

        for(int i = 0; i < itemCount; i++)
        {
            int id = jsonPath.getInt("[" + i + "].id");
            String name = jsonPath.getString("[" + i + "].name");
            String permanent = jsonPath.getString("[" + i + "].permanent");

            String street = jsonPath.getString("[" + i + "].address.street");
            String city = jsonPath.getString("[" + i + "].address.city");
            int zipcode = jsonPath.getInt("[" + i + "].address.zipcode");

            int phoneNumbers_1 = jsonPath.getInt("[" + i + "].phoneNumbers[0]");
            int phoneNumbers_2 = jsonPath.getInt("[" + i + "].phoneNumbers[1]");

            String role = jsonPath.getString("[" + i + "].role");

            String cities_1 = jsonPath.getString("[" + i + "].cities[0]");
            String cities_2 = jsonPath.getString("[" + i + "].cities[1]");

            String properties_age = jsonPath.getString("[" + i + "].properties.age");
            String properties_salary = jsonPath.getString("[" + i + "].properties.salary");

            System.out.println("Id: " + id);
            System.out.println("name: " + name);
            System.out.println("permanent: " + permanent);
            System.out.println("street: " + street);
            System.out.println("city: " + city);
            System.out.println("zipcode: " + zipcode);
            System.out.println("phoneNumbers_1: " + phoneNumbers_1);
            System.out.println("phoneNumbers_2: " + phoneNumbers_2);
            System.out.println("role: " + role);
            System.out.println("cities_1: " + cities_1);
            System.out.println("cities_2: " + cities_2);
            System.out.println("age: " + properties_age);
            System.out.println("salary: " + properties_salary);
            System.out.println("-----------------");
        }

    }

    @Test
    public void complexJsonParsing2()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "store": {
                "books": [
                    {
                        "id": 1,
                        "title": "Book A",
                        "author": "Author X",
                        "price": 10.99,
                        "category": "fiction"
                    },
                    {
                        "id": 2,
                        "title": "Book B",
                        "author": "Author Y",
                        "price": 15.99,
                        "category": "non-fiction"
                    },
                    {
                        "id": 3,
                        "title": "Book C",
                        "author": "Author Z",
                        "price": 8.99,
                        "category": "fiction"
                    }
                ],
                "electronics": [
                    {
                        "id": 101,
                        "name": "Laptop",
                        "brand": "Brand A",
                        "price": 799.99,
                        "features": {
                            "processor": "Intel i7",
                            "ram": "16GB",
                            "storage": "512GB SSD"
                        }
                    },
                    {
                        "id": 102,
                        "name": "Smartphone",
                        "brand": "Brand B",
                        "price": 499.99,
                        "features": {
                            "processor": "Snapdragon 888",
                            "ram": "8GB",
                            "storage": "128GB"
                        }
                    }
                ]
            }
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        // Retrieve all books with category "fiction"
        List<Map<String, Object>> fictionBooks = jsonPath.getList("store.books.findAll { it.category == 'fiction' }");

        System.out.println("Books with category 'fiction':");
        for (Map<String, Object> book : fictionBooks) {
            int id = (Integer) book.get("id");
            String title = (String) book.get("title");
            String author = (String) book.get("author");
            float price = (Float) book.get("price");
            String category = (String) book.get("category");

            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Price: " + price);
            System.out.println("Category: " + category);
            System.out.println("-------------------------");
        }

        // Retrieve all electronics with brand "Brand A"
        List<Map<String, Object>> brandAElectronics = jsonPath.getList("store.electronics.findAll { it.brand == 'Brand A' }");

        System.out.println("Electronics with brand 'Brand A':");
        for (Map<String, Object> electronic : brandAElectronics) {
            int id = (Integer) electronic.get("id");
            String name = (String) electronic.get("name");
            String brand = (String) electronic.get("brand");
            float price = (Float) electronic.get("price");

            // Extract nested features
            Map<String, Object> features = (Map<String, Object>) electronic.get("features");
            String processor = (String) features.get("processor");
            String ram = (String) features.get("ram");
            String storage = (String) features.get("storage");

            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Brand: " + brand);
            System.out.println("Price: " + price);
            System.out.println("Features:");
            System.out.println("  Processor: " + processor);
            System.out.println("  RAM: " + ram);
            System.out.println("  Storage: " + storage);
            System.out.println("-------------------------");
        }
    }

    @Test
    public void dynamicFiltering()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "products": [
                {
                    "id": 1,
                    "name": "Laptop",
                    "price": 799.99,
                    "category": "electronics"
                },
                {
                    "id": 2,
                    "name": "Smartphone",
                    "price": 499.99,
                    "category": "electronics"
                },
                {
                    "id": 3,
                    "name": "Book",
                    "price": 15.99,
                    "category": "books"
                }
            ]
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        List<Map<String, Object>> itemsList = jsonPath.getList("products.findAll {it.price > 500}");
        System.out.println("Products with price > 500:");

        for(Map<String, Object> item : itemsList)
        {
            System.out.println("id: " + item.get("id"));
            System.out.println("price: " + item.get("price"));
        }
    }

    @Test
    public void deeplyNestedJsonParsing()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "store": {
                "books": [
                    {
                        "id": 1,
                        "title": "Book A",
                        "author": "Author X",
                        "price": 10.99,
                        "details": {
                            "publisher": "Publisher A",
                            "year": 2021
                        }
                    },
                    {
                        "id": 2,
                        "title": "Book B",
                        "author": "Author Y",
                        "price": 15.99,
                        "details": {
                            "publisher": "Publisher B",
                            "year": 2020
                        }
                    }
                ]
            }
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        List<Map<String, Object>> booksList = jsonPath.getList("store.books");

        for(Map<String, Object> book : booksList)
        {
            System.out.println("id: " +  book.get("id"));
            System.out.println("title: " + (String) book.get("title"));
            System.out.println("author: " + (String) book.get("author"));
            System.out.println("price: " + (Float) book.get("price"));

            Map<String, Object> details = (Map<String, Object>) book.get("details");
            System.out.println("publisher: " + (String) details.get("publisher"));
            System.out.println("year: " +  details.get("year"));
        }
    }

    @Test
    public void extractSpecificFields()
    {
        // Sample JSON response
        String jsonResponse = """
        {
            "users": [
                {
                    "id": 1,
                    "name": "Alice",
                    "age": 25,
                    "email": "alice@example.com"
                },
                {
                    "id": 2,
                    "name": "Bob",
                    "age": 30,
                    "email": "bob@example.com"
                }
            ]
        }
        """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(jsonResponse);

        List<String> nameList = jsonPath.getList("users.name");
        List<String> emailList = jsonPath.getList("users.email");

        System.out.println(nameList);
        System.out.println(emailList);
    }

    @Test
    public void  extractFields_Filter_NestedFields()
    {
        String responseBody = """
                {
                    "orders": [
                        {
                            "id": 1,
                            "customer": {
                                "name": "Alice",
                                "email": "alice@example.com"
                            },
                            "total": 150.0
                        },
                        {
                            "id": 2,
                            "customer": {
                                "name": "Bob",
                                "email": "bob@example.com"
                            },
                            "total": 50.0
                        }
                    ]
                }
                """;

        // Parse the JSON response using JsonPath
        JsonPath jsonPath = new JsonPath(responseBody);

        List<Map<String, Object>> items = jsonPath.getList("orders.findAll {it.total > 100}");

        for(Map<String, Object> item : items)
        {
            Map<String, Object> customer = (Map<String, Object>) item.get("customer");
            String name = (String) customer.get("name");
            String email = (String) customer.get("email");

            System.out.println("Customer Name: " + name);
            System.out.println("Customer Email: " + email);
            System.out.println("-------------------------");

        }
    }

    //@Test
    public void testGetAllProducts()
    {
        // Base URL of the API
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Send GET request to /products endpoint
        Response response = productApiTests.sendGetRequest("/products");

        // Validate the status code
        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        // Print the response body
        System.out.println("Response Body: " + response.asString());
        System.out.println("******************");
        System.out.println("Response Body: " + response.asPrettyString());
        //List<Object> products = response.jsonPath().getList("$.products");
        List<Map<String, Object>> expensiveProducts = response.jsonPath().getList("products.findAll { it.price > 50 }");
        System.out.println("Expensive Products: " + expensiveProducts);

        /*for(Object obj : expensiveProducts)
        {
            System.out.println(obj.toString());
            System.out.println("----------------");

        }*/
    }
}
