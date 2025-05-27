Feature: Carts related scenarios
  As a user
  I want to fetch all carts
  So that I can view the available items in each cart

  Scenario: Fetch all carts successfully
    Given the API endpoint is "/carts"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of carts

  Scenario: Fetch a single cart successfully
    Given the API endpoint is "/carts"
    When I send a GET request to the endpoint with path param "5"
    Then the response status code should be 200
    And the response should contain the cart id details

  Scenario: Add a cart successfully
    Given the API endpoint is "/carts"
    And the request payload is:
      """
      {
        "id": 7,
        "userId": 4,
        "date": "2021-03-02T00:00:00.000Z",
        "products": [
            {
                "productId": 1,
                "quantity": 7
            },
            {
                "productId": 7,
                "quantity": 10
            },
            {
                "productId": 8,
                "quantity": 6
            }
        ],
        "__v": 0
      }
      """
    When I send a POST request to the endpoint with the payload
    Then the response status code should be 200
    And the response should contain the created cart details

#  Scenario Outline: Update a cart successfully
#    Given the API endpoint is "/products/<id>"
#    And the request payload is:
#      """
#      {
#        "id": <id>,
#        "userId": <userId>,
#        "products": [
#          {
#            "id": <productId>,
#            "title": "<title>",
#            "price": <price>,
#            "description": "<description>",
#            "category": "<category>",
#            "image": "<image>"
#          }
#        ]
#      }
#      """
#    When I send a PUT request to the endpoint with the payload
#    Then the response status code should be 200
#    And the response should contain the updated cart details
#
#    Examples:
#      | id  | userId    | productId  | title               | price     | description                       | category     | image                |
#      | 5   | 3         | 1          | Updated product     | 49.99     | A new product for testing purpose | electronics  | http://i.pravatar.cc |
      #| 2  | New Title | 49.99      | Another updated product   | jewelery     |

  Scenario: Delete a cart successfully
    Given the API endpoint is "/carts/1"
    When I send a DELETE request to the endpoint
    Then the response status code should be 200
    And the response should confirm the cart was deleted

