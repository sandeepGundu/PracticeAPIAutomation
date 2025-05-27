Feature: Products related scenarios
  As a user
  I want to create a new product
  So that it can be added to the store

  Scenario: Fetch all products successfully
    Given the API endpoint is "/products"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of products

  Scenario: Fetch a product successfully
    Given the API endpoint is "/products"
    When I send a GET request to the endpoint with path param "1"
    Then the response status code should be 200
    And the response should contain the product id details

  Scenario: Create a product with static request body successfully
    Given the API endpoint is "/products"
    And the request payload is:
      """
      {
        "title": "New Product",
        "price": 19.99,
        "description": "A new product for testing",
        "category": "electronics"
      }
      """
    When I send a POST request to the endpoint with the payload
    Then the response status code should be 200
    And the response should contain the created product details

  Scenario: Create a product with dynamic request body successfully
    Given the API endpoint is "/products"
    And the request payload is built with title "New Product 2", price 29.99, description "A new product 2 for testing", and category "electronics"
    When I send a POST request to the endpoint with the payload
    Then the response status code should be 200
    And the response should contain the created product details

  Scenario Outline: Update a product successfully
    Given the API endpoint is "/products/<id>"
    And the request payload is:
      """
      {
        "title": "<title>",
        "price": <price>,
        "description": "<description>",
        "category": "<category>"
      }
      """
    When I send a PUT request to the endpoint with the payload
    Then the response status code should be 200
    And the response should contain the updated product details

    Examples:
      | id | title           | price | description               | category     |
      | 1  | Updated Product | 29.99 | Updated product details   | electronics  |
      | 2  | New Title       | 49.99 | Another updated product   | jewelery     |

  Scenario: Delete a product successfully
    Given the API endpoint is "/products/1"
    When I send a DELETE request to the endpoint
    Then the response status code should be 200
    And the response should confirm the product was deleted