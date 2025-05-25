import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-Login", name = "#api-Courier-Login")
@Tag("login-courier")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API логина курьера")
@DisplayName("2. Логин курьера")
public class LoggingCourierTest {
    private final CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;
    private String addToLogin;
    private String addToPassword;

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        login = RandomStringUtils.randomAlphanumeric(10);
        password = RandomStringUtils.randomAlphanumeric(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
        addToLogin = RandomStringUtils.randomAlphanumeric(3);
        addToPassword = RandomStringUtils.randomAlphanumeric(3);
    }

    @Test
    @DisplayName("Логин курьера в систему")
    @Description("Тест проверяет API логина курьера. ОР - 200 OK, курьер залогинился в системе, возвращается его id")
    public void ShouldReturnIdAfterLoginTest() {
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(SC_CREATED)
                .body("ok", is(true));

        courierSteps
                .LoggingCourier(login, password)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Попытка логина курьера в систему без обязательных параметров")
    @Description("Тест проверяет API логина курьера. ОР - 400 Bad Request, курьер НЕ залогинился в системе")
    public void ShouldReturnBadRequestWithoutAllParams() {
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .LoggingCourier(this.login = "", this.password = "")
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка логина курьера в систему без логина")
    @Description("Тест проверяет API логина курьера. ОР - 400 Bad Request, курьер НЕ залогинился в системе")
    public void ShouldReturnBadRequestWithoutLogin() {
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .LoggingCourier(this.login = "", password)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка логина курьера в систему без пароля")
    @Description("Тест проверяет API логина курьера. ОР - 400 Bad Request, курьер НЕ залогинился в системе")
    public void ShouldReturnBadRequestWithoutPassword() {
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .LoggingCourier(login, this.password = "")
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка логина курьера с некорректным логином")
    @Description("Тест проверяет API логина курьера. ОР - 404 Not Found, курьер НЕ залогинился в системе")
    public void ShouldReturnNotFoundWithIncorrectLogin() {
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .LoggingCourier(this.login = login + addToLogin, password)
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка логина курьера с некорректным логином")
    @Description("Тест проверяет API логина курьера. ОР - 404 Not Found, курьер НЕ залогинился в системе")
    public void ShouldReturnNotFoundWithIncorrectPassword() {
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierSteps
                .LoggingCourier(login, this.password = password + addToPassword)
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
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
