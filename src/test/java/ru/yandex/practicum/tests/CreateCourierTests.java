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

public class CreateCourierTests extends BaseTest {
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
    @DisplayName("Проверка на создание курьера")
    @Description("Позитивный тест на создание курьера с заполненными полями")
    public void shouldCreateCourierTest() {
        courierSteps
                .createCourier(courier)
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Проверка на невозможность создания одинаковых курьеров")
    @Description("Негативный тест на невозможность создание одинаковых курьеров(тест падает из-за несоответствия требований тексту ошибки)")
    public void  impossibleCreateIdenticalCouriersTest() {
        courierSteps
                .createCourier(courier)
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .createCourier(courier)
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется."));
    }

    @Test
    @DisplayName("Проверка на создание курьера без логина")
    @Description("Негативный тест на невозможность создания курьера без обязательного поля логин")
    public void shouldNotCreateCourierWithoutLogin() {
        courier.setLogin("");
        courierSteps
                .createCourier(courier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка на создание курьера без пароля")
    @Description("Негативный тест на невозможность создания курьера без обязательного поля пароль")
    public void shouldNotCreateCourierWithoutPassword() {
        courier.setPassword("");
        courierSteps
                .createCourier(courier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка на создание курьера без логина и пароля")
    @Description("Негативный тест на невозможность создания курьера без двух обязательных полей")
    public void shouldNotCreateCourierWithEmptyFields() {
        courier.setPassword("");
        courier.setLogin("");
        courierSteps
                .createCourier(courier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown () {
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
            System.err.println("Курьер не был создан. Статус: " + loginResponse.extract().statusCode());
        }
    }
}

