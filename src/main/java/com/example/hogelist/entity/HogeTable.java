package com.example.hogelist.entity;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "hogetable")
@Data
public class HogeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private Date birthday;
    
    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "eat")
    private Integer eat;

    @Column(name = "vegetable_like")
    private String vegetableLike;
    
    @Column(name = "hobby")
    private String hobby;
}