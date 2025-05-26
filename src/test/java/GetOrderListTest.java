import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.steps.OrderSteps;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.praktikum.StaticUrls.BASEURL;
import static ru.yandex.praktikum.StaticUrls.ORDER_LIST_HANDLER;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-v1-orders", name = "#api/v1/orders")
@Tag("login-courier")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API получения списка заказов")
@DisplayName("4. Получение списка заказов")
public class GetOrderListTest {
    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Тест проверяет API получения списка заказов. ОР - 200 и тела заказов ")
    public void getOrderList() {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .baseUri(BASEURL)
                .get(ORDER_LIST_HANDLER)
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

}
