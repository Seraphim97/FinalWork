package db;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.Gson;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.LoginPage;
import org.example.Status;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    static String baseUrl;
    static String username;
    static String password;
    static Connection connection;
    static DBmanager dBmanager;
    static String token;

    @BeforeAll
    public static void setUp() throws SQLException {
        dBmanager = new DBmanager();
        connection = dBmanager.connect();

        Assumptions.assumeTrue(connection != null, "No connection to db. Infrastructure failure");

        SetupFunctions setupFunctions = new SetupFunctions();
        System.out.println("token: " + setupFunctions.getToken());
        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();
    }

    @AfterAll
    public static void tearDown() {

        dBmanager.close(connection);

    }

    @AfterEach
    public void tearDownEach() {
        closeWebDriver();
    }


    @Test
    public void dummy() throws SQLException {

        open("http://51.250.6.164:3000/signin");
        SelenideElement usernameInput = $(By.id("username")).setValue("serafim");
        SelenideElement passwordInput = $(By.id("password")).setValue("hellouser123");
        $(By.xpath("//*[@data-name='signIn-button']")).click();

        $(By.id("name")).setValue("order-name");
        $(By.id("phone")).setValue("phone-name");
        $(By.xpath("//*[@data-name = 'createOrder-button']")).click();

        SelenideElement e = $(By.xpath("//*[@data-name = 'orderSuccessfullyCreated-popup-close-button']/following-sibling::span"))
                .shouldBe(Condition.visible);

        String text = e.getAttribute("innerHTML");

        String successText = $(By.xpath("//*[@data-name = 'orderSuccessfullyCreated-popup-close-button']/following-sibling::span"))
                .shouldBe(Condition.visible)
                .getAttribute("innerHTML");

        String s = successText.replaceAll("[^0-9]", "");

        int orderId = Integer.parseInt(s);

        executeSearchAndCompare(orderId);
    }

    public void executeSearchAndCompare(int orderId) {
        String sqltoFail = String.format("select * from orders where id >= %d ;", orderId - 2);

        String sql = String.format("select * from orders where id = %d ;", orderId);

        System.out.println();

        try {
            System.out.println("Executing sql ...");
            System.out.println("SQL is : " + sql);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            int size = 0;
            String statusFromDb = null;
            if (resultSet != null) {

                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
                    statusFromDb = resultSet.getString(3);
                    size++;
                }
                Assertions.assertEquals(1, size);
                Assertions.assertEquals(Status.OPEN.toString(), statusFromDb);
            } else {
                Assertions.fail("Result set is null");
            }
        } catch (SQLException e) {
            System.out.println("Error while executing sql");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
        }
    }

// 1. If i use orderCreationPrecondition method I get this error : "class io.restassured.path.xml.XmlPath cannot be cast to class java.lang.Integer", i tryied to fix it like this : XmlPath xmlPath = response.xmlPath();
//int value = xmlPath.getInt("path.to.integer.value"), but it doesn't work...
    // 2. If i don't use orderCreationPrecondition method I get errors with assert...
    @Test
    public void api() {
        String name = generateRandomName();
        String phone = generateRandomPhone();
        String comment = generateRandomComment();
        //int orderId = orderCreationPrecondition();

        LoginPage loginPage = new LoginPage();
        username = new SetupFunctions().getUsername();
        password = new SetupFunctions().getPassword();

        open(baseURI);
        loginPage.insertLogin(username);

        loginPage.insertPassword(password);

        loginPage.clickSignInButton();

        $(By.xpath("//*[@id='name']")).setValue(name);
       //sleep(500);
        $(By.xpath("//*[@id='phone']")).setValue(phone);
        //sleep(500);
        $(By.xpath("//*[@id='comment']")).setValue(comment);
        //sleep(500);
        $(By.xpath("//*[@data-name = 'createOrder-button']")).click();
        //loginPage.commentInput();
//        $(By.xpath("//*[@data-name='searchOrder-input']")).setValue(String.valueOf(orderId));
//        $(By.xpath("//*[@data-name='searchOrder-submitButton']")).click();


        try {

            String nameText = $(By.xpath("//*[@id='name']"))
                    .shouldBe(Condition.visible)
                    .getAttribute("innerHTML");
            String phoneText = $(By.xpath("//*[@id='phone']"))
                    .shouldBe(Condition.visible)
                    .getAttribute("innerHTML");
            String commentText = $(By.xpath("//*[@id='comment']"))
                    .shouldBe(Condition.visible)
                    .getAttribute("innerHTML");


            assertAll(
                    () -> Assertions.assertEquals(name, nameText, "Something went wrong with Name field"),
                    () -> Assertions.assertEquals(phone, phoneText, "Something went wrong with Phone field"),
                    () -> Assertions.assertEquals(comment, commentText, "Something went wrong with Comment field")
            );
        } catch (NoSuchElementException e) {
            System.out.println("Order page not found");


        }

    }


    public int orderCreationPrecondition() {

        OrderRealDto orderRealDto = new OrderRealDto("testname1", "12345678", "no");

        Gson gson = new Gson();

        int id = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .path("id");

        return id;

    }



    public String generateRandomName() {
        return RandomStringUtils.random(8, true, false);
    }

    public String generateRandomPhone() {
        return RandomStringUtils.random(8, false, true);
    }

    public String generateRandomComment() {
        return RandomStringUtils.random(8, true, true);
    }




}

