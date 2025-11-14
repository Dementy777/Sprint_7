package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import ru.yandex.practicum.model.OrderPojo;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static final String ORDER="/api/v1/orders";
    public static final String CANCEL_ORDER="/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderPojo order){
        return given()
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(OrderPojo order){
        return given()
                .body(order)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList(OrderPojo order) {
        return given()
                .body(order)
                .when()
                .get(ORDER)
                .then();
    }

}

