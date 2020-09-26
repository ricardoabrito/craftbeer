package com.beerhouse.config.domain;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

public class FieldError {

    private final String fieldName;
    private final String message;

    public FieldError(ObjectError objectError) {
        this.message = objectError.getDefaultMessage();
        DefaultMessageSourceResolvable defaultMsg = (DefaultMessageSourceResolvable) objectError.getArguments()[0];
        this.fieldName = defaultMsg.getDefaultMessage();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

}
