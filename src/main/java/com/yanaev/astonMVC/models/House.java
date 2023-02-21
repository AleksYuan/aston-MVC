package com.yanaev.astonMVC.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class House {
    private Long id;
    private Integer area;
    private Boolean garage;
    private List<User> users = new ArrayList<>();
}
