package db;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.Gson;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Status;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class IntegrationTest {

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
    public static void tearDown(){

        dBmanager.close(connection);

    }

    @AfterEach
    public void tearDownEach(){closeWebDriver();}




    @Test
    public void dummy() throws SQLException{

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

        String s = successText.replaceAll("[^0-9]","");

        int orderId = Integer.parseInt( s );

        executeSearchAndCompare( orderId);
    }

    public void executeSearchAndCompare(int orderId) {
        String sqltoFail = String.format("select * from orders where id >= %d ;", orderId - 2);

        String sql = String.format("select * from orders where id = %d ;", orderId);

        System.out.println();

        try {
            System.out.println("Executing sql ...");
            System.out.println("SQL is : " +sql);

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
                Assertions.assertEquals(1,size);
                Assertions.assertEquals(Status.OPEN.toString(), statusFromDb);
            }else {
                Assertions.fail("Result set is null");
            }
        }catch (SQLException e) {
            System.out.println("Error while executing sql");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
        }
    }


    @Test
    public void api(){
        OrderRealDto orderRealDto = new OrderRealDto("testname","1234567","no");

        Gson gson = new Gson();

       Response orderId = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(orderRealDto)
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();


        open("http://51.250.6.164:3000/signin");
        SelenideElement usernameInput = $(By.id("username")).setValue("serafim");
        SelenideElement passwordInput = $(By.id("password")).setValue("hellouser123");
        $(By.xpath("//*[@data-name='signIn-button']")).click();
        $(By.xpath("//*[@data-name = 'openStatusPopup-button']")).click();
        $(By.xpath("//*[@data-name = 'searchOrder-input']")).setValue(String.valueOf(orderId));
        $(By.xpath("//*[@data-name = 'searchOrder-submitButton']")).click();



//        List<SelenideElement> elementList = $$(By.xpath("//*[starts-with(@data-name,'status-item')]"))
//                .shouldBe(CollectionCondition
//                        .allMatch("All elements should be visible",
//                                element->element.isDisplayed()));
//        Assertions.assertEquals(4,elementList.size());


    }
}
