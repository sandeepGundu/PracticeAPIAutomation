Feature: Bank services related scenarios
  As a user
  I am able to generate the authorization token
  And using the authorization token and other required details I am able to create a saving account

  Scenario: Create a Savings Account
    Given the API endpoint is "/api/accounts"
    And Login to the Application using <UserName>, <Password> and get the Authorization token
    And the request payload is:
    """
    {
      "accountType": "SAVINGS",
      "branch": "MAIN_BRANCH"
    }
    """
    When I send a POST request to the endpoint with the payload
    Then the response status code should be 200
    And the response should contain the created savings account details