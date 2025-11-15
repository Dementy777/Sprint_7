package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.CourierPojo;
import ru.yandex.practicum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.*;

public class AuthorizationCourierTests extends BaseTest{
    private CourierSteps courierSteps = new CourierSteps();
    private CourierPojo courier;

    @Before
    public void setUp() {
        courier = new CourierPojo();
        courier
                .setLogin(RandomStringUtils.randomAlphanumeric(10))
                .setPassword(RandomStringUtils.randomAlphanumeric(11));
        courierSteps
                .createCourier(courier);

    }

    @Test
    @DisplayName("позитивный тест на успешную авторизацию курьера")
    @Description("Курьер успешно авторизуется")
    public void shouldLoginCourierTest() {
        courierSteps
                .loginCourier(courier)
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера без логина")
    @Description("Курьер авторизуется без логина")
    public void errorReturnAuthorizationWithoutLoginTest() {
        courier.setLogin("");
        courierSteps
                .loginCourier(courier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера без пароля")
    @Description("Курьер  авторизуется без пароля")
    public void errorReturnAuthorizationWithoutPasswordTest() {
        courier.setPassword("");
        courierSteps
                .loginCourier(courier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера c несуществующим логином")
    @Description("Курьер  авторизуется c несуществующим логином")
    public void errorReturnAuthorizationWithNonExistentLoginTest() {
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        courierSteps
                .loginCourier(courier)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера c некорректным паролем")
    @Description("Курьер  авторизуется c некорректным паролем")
    public void errorReturnAuthorizationWithIncorrectPasswordTest() {
        courier.setPassword(RandomStringUtils.randomAlphanumeric(10));
        courierSteps
                .loginCourier(courier)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        ValidatableResponse loginResponse = courierSteps.loginCourier(courier);
        if (loginResponse.extract().statusCode() == HttpStatus.SC_OK) {
            Integer id = loginResponse.extract().body().path("id");
            if (id != null) {
                courier.setId(id);
                courierSteps.deleteCourier(courier);
            } else {
                System.err.println("ID не найден в ответе авторизации. Удаление курьера пропущено.");
            }
        } else {
            System.err.println("Авторизация не удалась. Статус: " + loginResponse.extract().statusCode());
        }
    }
}


