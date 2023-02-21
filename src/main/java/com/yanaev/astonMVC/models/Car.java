package com.yanaev.astonMVC.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Car {
    private Long id;
    private String name;
    private String comment;
    private Integer createdYear;
    private Long houseId;
    private List<User> users = new ArrayList<>();
    private List<Wheel> wheels = new ArrayList<>();
}
