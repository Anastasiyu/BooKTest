package com.example.booktest.service;

import com.example.booktest.exception.IncorrectLastNameExeption;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

@Service
public class ValidatorService {




    public String validateFirstName(String firstName)  {
        if (!StringUtils.isAlpha(firstName)) {
            try {
                throw new IncorrectFirstNameExeption();
            } catch (IncorrectFirstNameExeption e) {
                throw new RuntimeException(e);
            }
        }
        return StringUtils.capitalize(firstName.toLowerCase());
    }


    public String validateLastName(String lastName) {
        String[] lastName = lastName.split("-");
        for (int i = 0; i < lastName.length; i++) {
            if (!StringUtils.isAlpha(lastName[i])) {
                throw new IncorrectLastNameExeption();
            }
            lastName[i] = StringUtils.capitalize((lastName[i].toLowerCase()));
        }
        return StringUtils.join("-", lastName);
    }
}

