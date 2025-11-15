package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ru.yandex.practicum.model.OrderPojo;
import ru.yandex.practicum.steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests extends BaseTest {

    private final String[] scooterColors;
    private final OrderSteps orderSteps = new OrderSteps();
    private OrderPojo order;
    private Integer track;

    @Before
    public void setUp() {
        order = new OrderPojo();
        order
                .setFirstName("Обиван")
                .setLastName("Кеноби")
                .setAddress("Хорейн")
                .setMetroStation("Нагорная")
                .setPhone("23456987522")
                .setRenTime(25)
                .setDeliveryDate("2025-11-11")
                .setComment("Ты должен был бороться со злом, а не примкнуть к нему");
    }

    public CreateOrderTests(String colorDescription) {
        if (colorDescription.equals("(нет цвета)")) {
            this.scooterColors = new String[0];
        } else {
            this.scooterColors = colorDescription.split(",\\s*");
        }
    }

    @Parameterized.Parameters(name = "Создание заказа самоката (цвет: {0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{"BLACK, GREY"},
                new Object[]{"BLACK"},
                new Object[]{"GREY"},
                new Object[]{"(нет цвета)"}
        );
    }

    @Test
    //у этого теста нет @DisplayName для отображения цвета теста в Allure
    @Description("Возможность указания цвета самоката при создании заказа")
    public void createOrderWithDifferentScooterColorTest() {
        order.setColor(scooterColors);
        ValidatableResponse response = orderSteps
                .createOrder(order)
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
        track = response.extract().body().path("track");
    }

    @After
    public void tearDown() {
        if (track != null) {
            order.setTrack(track);
            orderSteps.cancelOrder(order);
            System.out.println("Заказ удалён. Track: " + track);
        } else {
            System.err.println("TRACK не найден. Удаление заказа пропущено.");
        }
    }
}

