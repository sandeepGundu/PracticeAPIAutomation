Feature: Users related scenarios
  As a user
  I want to fetch all users
  So that I can view the available user details, update and delete

  Scenario: Fetch all users successfully
    Given the API endpoint is "/users"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of users

