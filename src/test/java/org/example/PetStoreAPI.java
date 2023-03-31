package org.example;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class PetStoreAPI {


    @BeforeEach
    public void setup() {
        System.out.println("test start");
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/";
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 9})
    public void petsPositiveTest(int id) {

        given().
                log().
                all().
                when().
                get("order/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10})
    public void petsNegativeTest(int id) {

        given().
                log().
                all().
                when().
                get("order/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void simpleBodyGetPetTest() {

        String petStatus = given().
                log().
                all().
                when().
                get("order/9").
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("status");
        Assertions.assertTrue(petStatus.contains("ordered"));
    }
    @Test
    public void simpleBodyGetPetSecondTest() {

        String petStatus = given().
                log().
                all().
                when().
                get("order/3").
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("status");
        Assertions.assertTrue(petStatus.contains("placed"));
    }

    @Test
    public void getInventoryTest(){
        given().
                log().
                all().
                when().
                get("/inventory").
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK);

    }


}

