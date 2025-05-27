Feature: Create a new product
  As a user
  I want to create a new product
  So that it can be added to the store

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