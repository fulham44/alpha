package com.example.insurance.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsuranceNotFoundException extends RuntimeException {
    public InsuranceNotFoundException(String message) {
        super(message);
    }
}
