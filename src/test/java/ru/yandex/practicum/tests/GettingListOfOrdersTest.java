package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.OrderPojo;
import ru.yandex.practicum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GettingListOfOrdersTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private OrderPojo order;

    @Before
    public void setUp() {
        order = new OrderPojo();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка наличия списка заказов в ответе")
    public void gettingListOrdersTest() {
        orderSteps
                .getOrderList(order)
                .statusCode(200)
                .body("orders", notNullValue());

    }
}

