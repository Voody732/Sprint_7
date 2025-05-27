import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Test;
import ru.yandex.praktikum.steps.OrderSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

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
        orderSteps
                .getOrderList()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

}
