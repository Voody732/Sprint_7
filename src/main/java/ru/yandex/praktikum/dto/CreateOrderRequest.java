package ru.yandex.praktikum.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private final List<String> scooterColor;
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
}
