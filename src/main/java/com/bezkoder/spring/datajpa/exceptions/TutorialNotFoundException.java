package com.bezkoder.spring.datajpa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TutorialNotFoundException extends RuntimeException {

    public TutorialNotFoundException(String exception) {
        super(exception);
    }
}
