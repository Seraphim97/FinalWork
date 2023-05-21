package org.example;

import com.codeborne.selenide.Selenide;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class OrderPage {
    String name = generateRandomName();
    String phone = generateRandomPhone();
    String comment = generateRandomComment();

    public String generateRandomName() {
        return RandomStringUtils.random(8, true, false);
    }

    public String generateRandomPhone() {
        return RandomStringUtils.random(8, false, true);
    }

    public String generateRandomComment() {
        return RandomStringUtils.random(8, true, true);
    }

    public OrderPage order(){
        $(By.xpath("//*[@id='name']")).setValue(name);

        $(By.xpath("//*[@id='phone']")).setValue(phone);

        $(By.xpath("//*[@id='comment']")).setValue(comment);

        $(By.xpath("//*[@data-name = 'createOrder-button']")).click();

        return Selenide.page(OrderPage.class);
    }

    public void orderStatusButton(){
        $(By.xpath("//*[@data-name='openStatusPopup-button']")).click();
    }

    public void searchOrderButtonAndAddZeroValue(){
        $(By.xpath("//*[@data-name='searchOrder-input']")).setValue(String.valueOf(0));

    }
    public void orderSubmitButton(){
        $(By.xpath("//*[@data-name='searchOrder-submitButton']")).click();
    }
}
