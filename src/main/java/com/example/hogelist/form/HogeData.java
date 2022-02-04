package com.example.hogelist.form;

import java.sql.Date;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.example.hogelist.common.Utils;
import com.example.hogelist.entity.HogeTable;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class HogeData {
    private Integer id;

    @NotEmpty
    private String name;

    private Integer age;

    @NotEmpty
    private String gender;

    @NotNull
    private Integer eat;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE)//"yyyy-MM-dd"形式でString→LocalDate型に変換
    @PastOrPresent
    private LocalDate birthday;

    private String registrationDate;

    private String vegetableLike;
    private String hobby;

    public HogeTable toEntity() {
        HogeTable hogeTable = new HogeTable();
        hogeTable.setId(id);

        hogeTable.setName(name);

        hogeTable.setGender(gender);

        hogeTable.setBirthday(Date.valueOf(birthday));

        hogeTable.setVegetableLike(vegetableLike);

        hogeTable.setEat(eat);

        hogeTable.setHobby(hobby);

        hogeTable.setAge(Utils.birthday(birthday));
        
        hogeTable.setRegistrationDate(Utils.strDate(registrationDate));
        return hogeTable;
    }
}