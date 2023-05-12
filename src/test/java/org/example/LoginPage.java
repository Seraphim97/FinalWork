package org.example;

import com.codeborne.selenide.Condition;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.example.WebAppTest.username;

public class LoginPage {

    public String generateRandomLogin(){
        return RandomStringUtils.random(8,true,false);
    }

    public void insertLogin(String login) {
        $(By.xpath("//input[@data-name='username-input']")).setValue(login);
    }

    public String generateRandomPassword(){
        return RandomStringUtils.random(8,false,true);
    }

    public void insertPassword(String password){

        $(By.xpath("//input[@data-name='password-input']")).setValue(password);
    }

    public void checkSignInDisabled(){
        $(By.xpath("//*[@data-name='signIn-button']")).shouldBe(Condition.disabled);
    }

    public void clickSignInButton(){
        $(By.xpath("//*[@data-name='signIn-button']")).click();
    }

    public void authorizationErrorPopUp(){
        $(By.xpath("//*[@data-name='authorizationError-popup']")).shouldBe(Condition.exist);
    }

    public void passwordInput(){
        $(By.xpath("//input[@data-name='password-input']"));
    }

    public void usernameInput(){
        $(By.xpath("//input[@data-name='username-input']"));
    }

    public void commentInput(){
        $(By.xpath("//*[@data-name='comment-input']")).shouldBe(Condition.visible);
    }

}
