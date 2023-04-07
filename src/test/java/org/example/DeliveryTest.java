package org.example;

import com.google.gson.Gson;
import dto.CourierCreation;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class DeliveryTest {
    public static String token;

    //SetupFunctions setupFunctions = new SetupFunctions();
    //String baseUrl = setupFunctions.getBaseUrl();
    //String username = setupFunctions.getUsername();
    //String pwd = setupFunctions.getPassword();


    @BeforeAll
    public static void setup() {
        System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();

        token = setupFunctions.getToken();

        RestAssured.baseURI = setupFunctions.getBaseUrl();

        System.out.println("token: " + setupFunctions.getToken());

    }

    @Test
    public void createOrderTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "no");

        // 1
        Gson gson = new Gson();


        // 2
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(orderRealDto)
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


    }

    @Test
    public void createOrderWithoutComment() {

        OrderRealDto orderRealDto = new OrderRealDto("customer", "7777777", "");

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(orderRealDto)
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


    }

    @Test
    public void unsuccessfulOrderCreationWithoutToken() {

        OrderRealDto orderRealDto = new OrderRealDto("Rain", "333333", "yes");

        given()
                .header("Content-type", "application/json")
                //.header("Authorization", "Bearer " + token)
                .body(orderRealDto)
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .response();


    }

    public int orderCreationPrecondition() {

        OrderRealDto orderRealDto = new OrderRealDto("testname1", "12345678", "no");

        Gson gson = new Gson();

        int id = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .path("id");

        return id;

    }

    @Test
    public void getOrderById() {

        int id = orderCreationPrecondition();

        int getId = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");


        Assertions.assertEquals(getId, id);
    }

    @Test
    public void getOrders() {

        int id = orderCreationPrecondition();
        

        OrderRealDto[] orderRealDtoArray = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderRealDto[].class);
        


        for (int i = 0; i < orderRealDtoArray.length; i++) {

            System.out.println(orderRealDtoArray[i].getId());

            deleteOrderById(orderRealDtoArray[i].getId());

        }

        OrderRealDto[] orderRealDtoArrayAfterDeleteon = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderRealDto[].class);

            //ex.3
            System.out.println("Array length = " + orderRealDtoArray.length);

            Assertions.assertEquals(0, orderRealDtoArrayAfterDeleteon.length);





    }



    @Test
    public void deleteOrderByIdTest() {

        int orderId = orderCreationPrecondition();

        deleteOrderById(orderId);

    }

    public void deleteOrderById(long id) {

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .delete("/orders/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    public Response executeGetMethodByStudent(String path) {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get(path)
                .then()
                .log()
                .all()
                .extract()
                .response();

        return response;

    }


    public Response executePutMethodByStudent(String path, int orderId) {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .put(path)
                .then()
                .log()
                .all()
                .extract()
                .response();

        return response;


    }

    @Test
    public void courierOrderAvailabilityForbidenForStudent() {

        Response response = executeGetMethodByStudent("/orders/available");

        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);


        System.out.println();
    }

    @Test
    public void courierOrderAssignForbidenForStudent() {

        int orderId = orderCreationPrecondition();

        Response response = executePutMethodByStudent("/orders/" + orderId + "/assign", orderId);

        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);

    }


    @Test
    public void createCourier() {

        String courierLogin = generateRandomLogin();

        CourierCreation courierBody = new CourierCreation(courierLogin, "password123", "Serafim");

        Gson gson = new Gson();


        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(courierBody))
                .log()
                .all()
                .post("/users/courier" )
                .then()
                .log()
                .all()
                .extract()
                .response();

    }

    public String generateRandomLogin() {
        return RandomStringUtils.random(8,true,false);
    }


}




