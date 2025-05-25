package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.dto.CreateCourierRequest;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.StaticUrls.*;

public class CourierSteps {

    @Step("Отправка запроса на создание курьера")
    public ValidatableResponse createCourier(String login, String password, String firstName) {
        CreateCourierRequest request = new CreateCourierRequest();
        request.setLogin(login);
        request.setPassword(password);
        request.setFirstName(firstName);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(request)
                .when()
                .post(CREATE_COURIER_HANDLER)
                .then();
    }

    @Step("Отправка запроса на логин курьера")
    public ValidatableResponse LoggingCourier(String login, String password) {
        CreateCourierRequest request = new CreateCourierRequest();
        request.setLogin(login);
        request.setPassword(password);

        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(request)
                .when()
                .post(LOGIN_COURIER_HANDLER)
                .then();
    }

    @Step("Отправка запроса на удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
        RestAssured.defaultParser = Parser.JSON;
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .pathParam("id", id)
                .when()
                .delete(DELETE_COURIER_HANDLER)
                .then();

    }
}
