package org.example;

import com.google.gson.Gson;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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

        System.out.println( "token: " + setupFunctions.getToken());

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
                .extract()
                .response();


    }

    public int orderCreationPrecondition() {

        OrderRealDto orderRealDto = new OrderRealDto("testname1", "12345678", "no");
        Gson gson = new Gson();

        int id = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body( gson.toJson( orderRealDto ) )
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
    public void getOrderById(){
        int id = 2802;
        String response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();


        Assertions.assertEquals("",response);
    }

    @Test
    public void getOrders(){

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

        //orderRealDto.length

        for ( int i = 0; i < orderRealDtoArray.length; i++) {

            System.out.println(orderRealDtoArray[i].getId());

            deleteOrderById(orderRealDtoArray[i].getId());
        }
        System.out.println();
    }


    @Test
    public void deleteOrderByIdTest(){
        deleteOrderById(2802);
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
                .statusCode(200);
    }


}



