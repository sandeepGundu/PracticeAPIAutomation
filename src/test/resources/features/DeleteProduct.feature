Feature: Delete a product
  As a user
  I want to delete a product
  So that it is removed from the store

  Scenario: Delete a product successfully
    Given the API endpoint is "/products/1"
    When I send a DELETE request to the endpoint
    Then the response status code should be 200
    And the response should confirm the product was deleted