package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import helpers.SetupFunctions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;

import java.util.Properties;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class WebAppTest {

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
    public void incorrectLogin() {

        Configuration.holdBrowserOpen = true;

        LoginPage loginPage = new LoginPage();

        loginPage.insertLogin(loginPage.generateRandomLogin());

        loginPage.insertPassword(loginPage.generateRandomPassword());

        loginPage.passwordInput();

        loginPage.clickSignInButton();

        loginPage.authorizationErrorPopUp();



    }



    @Test

    public void correctLogin(){
        Configuration.holdBrowserOpen = true;

        LoginPage loginPage = new LoginPage();
        username = new SetupFunctions().getUsername();
        password = new SetupFunctions().getPassword();

        loginPage.insertLogin(username);

        loginPage.insertPassword(password);

        loginPage.usernameInput();

        loginPage.passwordInput();

        loginPage.clickSignInButton();

        loginPage.commentInput();
    }

    @Test
    public void incorrectLoginAndCheckPopupPageObject(){

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin(loginPage.generateRandomLogin());
        loginPage.checkSignInDisabled();

    }
}
