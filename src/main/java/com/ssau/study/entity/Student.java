package com.ssau.study.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Student {
    private long id;
    private String name;
    private Date birthdate;
    private int number;
}
