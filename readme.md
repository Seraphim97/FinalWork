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
| 4 | Check order id 3 status, method GET, response 200                         | Passed |
| 5 | Check inventory status, method GET, response 200                          | Passed |

UI check-list
| № |               Check name                                                  | Status |
-- | -------------|--------
| 1 | Check authorization with correct login and correct password               |  |
| 2 | Check authorization with incorrect login and correct password             |  |
| 3 | Check authorization with correct login and incorrect password             |  |
| 4 | Check authorization with incorrect login and incorrect password           |  |
| 5 | Check Sign in button                                                      |  |
| 6 | Check Popup close button                                                  |  |


Web elements for UI check-list
| № |               Check name                                                  | Xpath |
-- | -------------|--------
| 1 | Login                                                                     | //*[@id="username"] |
| 2 | Password                                                                  | //*[@id="password"] |
| 3 | Sign in button                                                            | //button[@data-name ="signIn-button"] |
| 4 | Incorrect credentials                                                     | //div[@data-name="authorizationError-popup"] |
| 5 | Popup close button                                                        | //button[@data-name="authorizationError-popup-close-button"] |

