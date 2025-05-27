Feature: Fetch all products
  As a user
  I want to fetch all products
  So that I can view the available items in the store

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