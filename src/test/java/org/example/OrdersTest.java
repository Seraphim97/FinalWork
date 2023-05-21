package org.example;

import helpers.SetupFunctions;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class OrdersTest {
    static String baseUrl;
    static String username;
    static String password;

    @BeforeAll
    public static void setUpAll(){
        baseUrl = new SetupFunctions().getBaseUrl();

    }

    @BeforeEach
    public void setUp(){


        open (baseUrl);


    }

    @AfterEach
    public void tearDown(){

        closeWebDriver();

    }

    @Test
    public void loginAndOrderCreationTest(){

        LoginPage loginPage = new LoginPage();
        username = new SetupFunctions().getUsername();
        password = new SetupFunctions().getPassword();

        loginPage.insertLogin(username);

        loginPage.insertPassword(password);

        loginPage.usernameInput();

        loginPage.passwordInput();

        loginPage.clickSignInButton();

        OrderPage orderPage = loginPage.login("username", "password");

        orderPage.order();
    }

    @Test
    public void loginAndCheckNonExistentOrder(){
        LoginPage loginPage = new LoginPage();
        username = new SetupFunctions().getUsername();
        password = new SetupFunctions().getPassword();

        loginPage.insertLogin(username);

        loginPage.insertPassword(password);

        loginPage.usernameInput();

        loginPage.passwordInput();

        loginPage.clickSignInButton();

        OrderPage orderPage = loginPage.login("username","password");

        orderPage.orderStatusButton();

        orderPage.searchOrderButtonAndAddZeroValue();

        orderPage.orderSubmitButton();




    }

}
