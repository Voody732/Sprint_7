import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier", name = "#api-Courier-CreateCourier")
@Tag("create-courier")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API создания курьера")
@DisplayName("1. Создание курьера")
public class CreatingCourierTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    //private String firstName;


    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        login = RandomStringUtils.randomAlphanumeric(10);
        password = RandomStringUtils.randomAlphanumeric(10);
        // firstName = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Тест проверяет API создания нового курьера. ОР - ok:true ")
    public void ShouldReturnOkTrueAfterCreateCourierTest() {
        courierSteps
                .createCourier(login, password)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        ;
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Тест проверяет возможность создать двух одинаковых курьеров. ОР - 409 Conflict")
    public void ShouldReturnConflictForDuplicatedCourierTest() {
        courierSteps
                .createCourier(login, password)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .createCourier(login, password)
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
        ;

    }

    @Test
    @DisplayName("Создание нового курьера без входных данных")
    @Description("Тест проверяет API создания нового курьера без входных данных. ОР - 400 Bad Request, новый курьер НЕ создан")
    public void ShouldReturnBadRequestWithoutAllParams() {
        courierSteps
                .createCourier(this.login = "", this.password = "")
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Тест проверяет API создания нового курьера без входных данных. ОР - 400 Bad Request, новый курьер НЕ создан")
    public void ShouldReturnBadRequestWithEmptyLoginTest() {
        courierSteps
                .createCourier(this.login = "", password)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        ;
    }


    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Тест проверяет API создания нового курьера без входных данных. ОР - 400 Bad Request, новый курьер НЕ создан")
    public void ShouldReturnBadRequestWithEmptyPasswordTest() {
        courierSteps
                .createCourier(login, this.password = "")
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        ;
    }


    @After
    @Step("Очистка данных после теста")
    public void tearDown() {
        Integer id = courierSteps.LoggingCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }
}
