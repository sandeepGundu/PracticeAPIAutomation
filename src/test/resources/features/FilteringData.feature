Feature: Public API Testing - Filtering Data

  Scenario: Verify the API supports filtering data with query parameters
    Given the API endpoint is "/v1/resources"
    #Given the API endpoint "https://api.example.com/v1/resources" is available
    When I send a GET request to the endpoint with the query parameter "type=example"
    Then the response status code should be 200
    And the response body should contain a JSON array
    And each object in the array should have the "type" value as "example"

Feature: Public API Testing - Non-Existent Resource
  Scenario: Verify the API returns 404 for a non-existent resource
    Given the API endpoint is "/v1/resources/9999"
      #Given the API endpoint "https://api.example.com/v1/resources/9999" is available
    When I send a GET request to the endpoint
    Then the response status code should be 404
    And the response body should contain an error message indicating "Resource not found"
