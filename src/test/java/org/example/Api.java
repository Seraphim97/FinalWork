package org.example;

import com.google.gson.Gson;
import dto.OrderTestDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                statusCode(HttpStatus.SC_OK);
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


    @Test
    public void createOrderAndCheckStatusCode() {
         OrderTestDto orderDto = new OrderTestDto("testname", "1234567", "no");

        OrderTestDto orderDtoRandom = new OrderTestDto();
        orderDtoRandom.setCustomerName( genereteRandomName());
        orderDtoRandom.setCustomerPhone( genereteRandomPhone());
        orderDtoRandom.setComment( genereteRandomComment());

        Gson gson = new Gson();

       Response response = given()
                .header("Content-type", "application/json")
                .body(orderDto)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .extract()
                .response();

       OrderTestDto orderDtoReceived = gson.fromJson( response.asString(), OrderTestDto.class);

       assertEquals( orderDto.getCustomerName(), orderDtoReceived.getCustomerName());
       assertEquals( orderDto.getCustomerPhone(), orderDtoReceived.getCustomerPhone());
       assertEquals( orderDto.getComment(), orderDtoReceived.getComment());
       Assertions.assertNotNull( orderDtoReceived.getId());
       Assertions.assertNull(orderDtoReceived.getStatus());

        assertAll(
                "Grouped Assertions of User",
                () -> assertEquals("noo", orderDtoReceived.getComment(), "1 st Assert"),
                () -> assertEquals("testnamee", orderDtoReceived.getCustomerName(), "2nd Assert")
        );

    }




    @Test
    public void createOrderAndCheckStatusCodeNegative() {
        OrderTestDto orderDto = new OrderTestDto("testname", "1234567","no");
        

        given()
                .body(orderDto)
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
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("status");
        Assertions.assertTrue( statusId.contains("OPEN"));
    }

    public String genereteRandomName() {
        return RandomStringUtils.random(10, true, false);


    }

    public String genereteRandomPhone() {
        return RandomStringUtils.random(10, false,true);
    }

    public String genereteRandomComment () {
        return RandomStringUtils.random(5,true,true);
    }






}

