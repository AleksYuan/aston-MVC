package com.yanaev.astonMVC.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wheel {
    private Long id;
    private String type;
    private Integer radius;
    private Long carId;
}
