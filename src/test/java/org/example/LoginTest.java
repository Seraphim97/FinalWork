package org.example;

import dto.Credentials;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LoginTest {
    @BeforeEach
    public void setup() {

        SetupFunctions setupFunctions = new SetupFunctions();

        System.out.println("test start");
        RestAssured.baseURI = setupFunctions.getRestAssuredUri();
        RestAssured.port = setupFunctions.getRestAssuredPort();

    }


    @Test
    public void positiveLoginTest() {

        SetupFunctions setupFunctions = new SetupFunctions();
        setupFunctions.getUsername();
        setupFunctions.getPassword();

        Response response = given()
                .header("Content-type", "application/json")
                .body(setupFunctions)
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
       LoginPage loginPage = new LoginPage();
       loginPage.generateRandomLogin();
       loginPage.generateRandomPassword();

        Response response = given()
                .header("Content-type", "application/json")
                .body(loginPage)
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
        LoginPage loginPage = new LoginPage();
        loginPage.generateRandomLogin();
        loginPage.generateRandomPassword();


        Response response = given()
                .header("Content-type", "application/json")
                .body(loginPage)
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