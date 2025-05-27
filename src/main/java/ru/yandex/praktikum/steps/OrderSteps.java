package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.dto.CancelOrderRequest;
import ru.yandex.praktikum.dto.CreateOrderRequest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.StaticUrls.*;

public class OrderSteps {
    @Step("Отправка запроса на создание заказа")
    public ValidatableResponse createOrder(List<String> scooterColor, String firstName, String lastName,
                                           String address, int metroStation, String phone, int rentTime,
                                           String deliveryDate, String comment) {
        CreateOrderRequest request = new CreateOrderRequest(scooterColor);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setAddress(address);
        request.setMetroStation(metroStation);
        request.setPhone(phone);
        request.setRentTime(rentTime);
        request.setDeliveryDate(deliveryDate);
        request.setComment(comment);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(request)
                .when()
                .post(CREATE_ORDER_HANDLER)
                .then();
    }

    public ValidatableResponse cancelOrder(CancelOrderRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(request)
                .when()
                .put(DELETE_ORDER_HANDLER)
                .then();
    }

    public ValidatableResponse getOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .when()
                .get(ORDER_LIST_HANDLER)
                .then();
    }
}
