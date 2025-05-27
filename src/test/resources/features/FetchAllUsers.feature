Feature: Fetch all users

  Scenario: Fetch all users successfully
    Given the API endpoint is "/users"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of users