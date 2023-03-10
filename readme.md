Checking positive and negative code responses for methods GET and POST
Using Swagger


Api class check-list
| № |               Check name                                                  | Status |
-- | -------------|--------
| 1 | Check order with id 5, method GET, response 200                           | Passed |
| 2 | Check orders with different id, method GET, response 200                  | Passed |
| 3 | Check order creation and status code, method POST, status 200             | Passed |
| 4 | Check negative order creation and status code, method POST, status 200    | Passed |
| 5 | Check positive body response for method GET                               | Passed |


PetStoreAPI class check-list
| № |               Check name                                                  | Status |
-- | -------------|--------
| 1 | Check orders with positive id, method GET, response  200                  | Passed |
| 2 | Check orders with negative id  method GET, response  404                  | Passed |
| 3 | Check order id 9 status, method GET, response 200                         | Passed |
| 4 | Check order id 9 status, method GET, response 200                         | Passed |
| 5 | Check invenroty status, method GET, response 200                          | Passed |