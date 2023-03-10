package org.example;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class Api {

    @BeforeEach
    public void setup() {
        System.out.println("test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @Test
    public void simplePositiveTest() {

        given().
                log().
                all().
                when().
                //get("http://51.250.6.164:8080/test-orders/5").
                get("/test-orders/5").
                then().
                log().
                all().
                statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 9, 10})
    public void simpleParamPositiveTest(int id) {

        given().
                log().
                all().
                when().
                //get("http://51.250.6.164:8080/test-orders/5").
                get("/test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK);
    }



    @ParameterizedTest
    @ValueSource(ints = {0, 11, -1,})
    public void simpleParamNegativeTest(int id) {

        given().
                log().
                all().
                when().
                //get("http://51.250.6.164:8080/test-orders/5").
                get("/test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    String order = "{\"customerName\":\"name\",\"customerPhone\":\"123456\",\"comment\":\"comment\"}";
    @Test
    public void createOrderAndCheckStatusCode() {

        given()
                .header("Content-type", "application/json")
                .body(order)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }




    @Test
    public void createOrderAndCheckStatusCodeNegative() {

        given()
                .body(order)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }



    @Test
    public void simpleBodyGetTest() {

        String statusId = given().
                log().
                all().
                when().
                get("/test-orders/5").
                then().
                log().
                all().
                statusCode(200).
                and().
                extract().
                path("status");
        Assertions.assertTrue( statusId.contains("OPEN"));
    }






}

