package org.example;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void insertLogin(String query){
        $(By.xpath("//input[@data-name='username-input']")).setValue(query);
    }

    public void insertPassword(String query){
        $(By.xpath("//input[@data-name='password-input']")).setValue(query);
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
