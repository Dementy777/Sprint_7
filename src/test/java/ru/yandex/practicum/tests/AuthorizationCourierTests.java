package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
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
    }

    @Test
    @DisplayName("позитивный тест на успешную авторизацию курьера")
    @Description("Курьер успешно авторизуется")
    public void shouldLoginCourierTest() {
        courierSteps
                .createCourier(courier);
        courierSteps
                .loginCourier(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера без логина")
    @Description("Курьер авторизуется без логина")
    public void errorReturnAuthorizationWithoutLoginTest() {
        courier.setLogin("");
        courierSteps
                .createCourier(courier);
        courierSteps
                .loginCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера без пароля")
    @Description("Курьер  авторизуется без пароля")
    public void errorReturnAuthorizationWithoutPasswordTest() {
        courier.setPassword("");
        courierSteps
                .createCourier(courier);
        courierSteps
                .loginCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера c несуществующим логином")
    @Description("Курьер  авторизуется c несуществующим логином")
    public void errorReturnAuthorizationWithNonExistentLoginTest() {
        courierSteps
                .createCourier(courier);
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        courierSteps
                .loginCourier(courier)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Негативный тест на невозможность авторизации курьера c некорректным паролем")
    @Description("Курьер  авторизуется c некорректным паролем")
    public void errorReturnAuthorizationWithIncorrectPasswordTest() {
        courierSteps
                .createCourier(courier);
        courier.setPassword(RandomStringUtils.randomAlphanumeric(10));
        courierSteps
                .loginCourier(courier)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        ValidatableResponse loginResponse = courierSteps.loginCourier(courier);
        if (loginResponse.extract().statusCode() == 200) {
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


