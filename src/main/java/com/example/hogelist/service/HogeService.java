package com.example.hogelist.service;

import java.util.Locale;

import com.example.hogelist.common.Utils;
import com.example.hogelist.form.HogeData;
import com.example.hogelist.form.HogeQuery;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HogeService {

    private final MessageSource message;

    public boolean isValid(HogeData hogeData, BindingResult result, boolean isCreate, Locale locale) {
        boolean ans = true;
        // 名前チェック
        String name = hogeData.getName();
        if (!Utils.isBlank(name)) {
            if (Utils.isDoubleSpace(name)) {
                FieldError error = new FieldError(result.getObjectName(), "name",
                        message.getMessage("DoubleSpace", null, locale));
                result.addError(error);
                ans = false;
            }
        }
        // 登録日形式チェック
        String date = hogeData.getRegistrationDate();
        if (!date.equals("")) {
            if (!Utils.isValidDateFormat(date)) {
                FieldError error = new FieldError(result.getObjectName(),
                        "registrationDate",
                        message.getMessage("Format.registrationDate", null, locale));
                result.addError(error);
                ans = false;
                // 新規追加のみ今日以降かチェック
            } else {
                if (isCreate) {
                    if (!Utils.isTodayOrFurtureDate(date)) {
                        FieldError error = new FieldError(result.getObjectName(),
                                "registrationDate",
                                message.getMessage("Past.registrationDate", null, locale));
                        result.addError(error);
                        ans = false;
                    }
                }
            }
        }
        return ans;
    }

    public boolean isValid(HogeQuery hogeQuery, BindingResult result, Locale locale) {
        boolean ans = true;
        String from = hogeQuery.getRegistrationDateFrom();
        if (!from.equals("") && !Utils.isValidDateFormat(from)) {
            FieldError error = new FieldError(result.getObjectName(),
                    "registrationDateFrom",
                     message.getMessage("Format.registrationDateFrom", null, locale));
            result.addError(error);
            ans = false;
        }
        String to = hogeQuery.getRegistrationDateTo();
        if (!to.equals("") && !Utils.isValidDateFormat(to)) {
            FieldError error = new FieldError(result.getObjectName(),
                    "registrationDateTo",
                    message.getMessage("Format.registrationDateTo", null, locale));
            result.addError(error);
            ans = false;
        }
        return ans;
    }

}