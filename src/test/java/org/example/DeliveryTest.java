package org.example;

import com.google.gson.Gson;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
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
    public void createOrderWithoutToken() {

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

}



