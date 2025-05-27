Feature: Update an existing product
  As a user
  I want to update an existing product
  So that I can modify its details

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