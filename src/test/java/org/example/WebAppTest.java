package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public class WebAppTest {

    @BeforeEach
    public void setUp(){

        open ( "http://51.250.6.164:3000/signin");

    }

    @AfterEach
    public void tearDown(){

        closeWebDriver();

    }

    @Test
    public void incorrectLogin() {

        Configuration.holdBrowserOpen = true;

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("serafim");
        loginPage.insertPassword("12345678");

        $(By.xpath("//input[@data-name='password-input']"));

        $(By.xpath("//*[@data-name='signIn-button']")).click();

        $(By.xpath("//*[@data-name='authorizationError-popup']")).shouldBe(Condition.exist);



    }



    @Test

    public void correctLogin(){
        Configuration.holdBrowserOpen = true;

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("serafims");
        loginPage.insertPassword("hellouser123");

        $(By.xpath("//input[@data-name='username-input']"));

        $(By.xpath("//input[@data-name='password-input']"));

        $(By.xpath("//*[@data-name='signIn-button']")).click();

        $(By.xpath("//*[@data-name='comment-input']")).shouldBe(Condition.visible);
    }

    @Test
    public void incorrectLoginAndCheckPopupPageObject(){

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("123");
        loginPage.checkSignInDisabled();

    }
}
