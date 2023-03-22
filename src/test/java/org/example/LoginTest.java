package org.example;

import dto.Credentials;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LoginTest {
    @BeforeEach
    public void setup() {
        System.out.println("test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;

    }


    @Test
    public void positiveLoginTest() {

        Credentials credentials = new Credentials();
        credentials.setUsername("serafims");
        credentials.setPassword("hellouser123");

        Response response = given()
                .header("Content-type", "application/json")
                .body(credentials)
                .log()
                .all()
                .post("/login/student")
                .then()
                .log()
                .all()
                .extract()
                .response();
        Assertions.assertNotNull(response.asString());

    }


    @Test
    public void negativeLoginTest() {
        Credentials credentials = new Credentials();
        credentials.setUsername("12345");
        credentials.setPassword("hellouser123");

        Response response = given()
                .header("Content-type", "application/json")
                .body(credentials)
                .log()
                .all()
                .post("/login/student")
                .then()
                .log()
                .all()
                .extract()
                .response();

        Assertions.assertNotNull(response.asString());
    }

    @Test
    public void negativePasswordTest() {
        Credentials credentials = new Credentials();
        credentials.setUsername("serafims");
        credentials.setPassword("12345");

        Response response = given()
                .header("Content-type", "application/json")
                .body(credentials)
                .log()
                .all()
                .post("/login/student")
                .then()
                .log()
                .all()
                .extract()
                .response();

        Assertions.assertNotNull(response.asString());
    }
}