package com.example.booktest.service;

import com.example.booktest.exception.IncorrectFirstNameExeption;
import com.example.booktest.exception.IncorrectLastNameExeption;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

@Service
public class ValidatorService {



    @SneakyThrows
    public String validateFirstName(String firstName) {
        if (!StringUtils.isAlpha(firstName)) {
            throw new IncorrectFirstNameExeption();
        }
        return StringUtils.capitalize(firstName.toLowerCase());
    }


    public String validateLastName(String lastName) {
        String[] lastNames = lastName.split("");
        for (int i = 0; i < lastNames.length; i++) {
            if (!StringUtils.isAlpha(lastNames[i])) {
                throw new IncorrectLastNameExeption();
            }
            lastNames[i] = StringUtils.capitalize((lastNames[i].toLowerCase()));
        }
        return StringUtils.join("", lastName);
    }
}

