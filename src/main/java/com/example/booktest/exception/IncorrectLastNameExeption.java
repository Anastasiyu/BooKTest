package com.example.booktest.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class IncorrectLastNameExeption extends  RuntimeException{
}
