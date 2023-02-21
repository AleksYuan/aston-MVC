package com.yanaev.astonMVC.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phoneNumber;
    private List<House> houses = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
}
