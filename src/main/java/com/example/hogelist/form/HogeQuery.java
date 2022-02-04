package com.example.hogelist.form;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HogeQuery {
    private String name;
    private Integer age;
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayTo;

    private String registrationDateFrom;
    private String registrationDateTo;

    private Integer eat;
    private String vegetableLike;
    private String hobby;
    public HogeQuery() {
        name = "";
        age = null;
        gender = "";
        birthdayFrom = null;
        birthdayTo = null;
        registrationDateFrom = "";
        registrationDateTo = "";
        eat = null;
        vegetableLike = "";
        hobby = "";
    }
}