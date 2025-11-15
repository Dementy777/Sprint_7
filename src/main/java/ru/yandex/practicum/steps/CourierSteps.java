package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.model.CourierPojo;

import static io.restassured.RestAssured.given;

public class CourierSteps  {

    public static final String COURIER="/api/v1/courier";
    public static final String LOGIN="/api/v1/courier/login";
    public static final String COURIER_DELETE = COURIER + "/{id}";



    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierPojo courier) {
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierPojo courier) {
        return given()
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(CourierPojo courier) {
        return given()
                .pathParam("id", courier.getId())
                .when()
                .delete(COURIER_DELETE)
                .then();
    }
}

