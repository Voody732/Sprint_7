import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.dto.CancelOrderRequest;
import ru.yandex.praktikum.steps.OrderSteps;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder", name = "#api-Orders-CreateOrder")
@Tag("create-order")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API создания заказа")
@DisplayName("3. Создание заказа")

public class CreatingOrderTest {
    private final OrderSteps orderSteps = new OrderSteps();
    private final List<String> scooterColor;
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private Integer trackId;

    public CreatingOrderTest(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] colorParams() {
        return new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        firstName = RandomStringUtils.randomAlphabetic(7);
        lastName = RandomStringUtils.randomAlphabetic(7);
        address = RandomStringUtils.randomAlphanumeric(20);
        metroStation = Integer.parseInt(RandomStringUtils.randomNumeric(1, 3));
        phone = "+" + RandomStringUtils.randomNumeric(1, 1) + " " + RandomStringUtils.randomNumeric(3, 3)
                + " " + RandomStringUtils.randomNumeric(3, 3) + " " + RandomStringUtils.randomNumeric(2, 2)
                + " " + RandomStringUtils.randomNumeric(2, 2);
        rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        int randomDays = ThreadLocalRandom.current().nextInt(1, 31);
        deliveryDate = LocalDate.now().plusDays(randomDays).toString();
        comment = RandomStringUtils.randomAlphabetic(20);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Тест проверяет API создания заказа. Ожидаемый результат - заказ создан, возвращается его track-номер")
    public void ShouldReturnTrackAfterCreateOrderTest() {
        trackId = orderSteps
                .createOrder(scooterColor, firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment)
                .statusCode(SC_CREATED)
                .body("track", is(notNullValue()))
                .extract().path("track");
    }

    @After
    @Step("Удаление заказа")
    public void tearDown() {
        if (trackId != null) {
            CancelOrderRequest request = new CancelOrderRequest(trackId);
            orderSteps.cancelOrder(request);


        }
    }


}
