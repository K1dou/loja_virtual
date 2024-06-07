package com.K1dou.Loja.virtual.exceptions;

import java.util.List;

public class ExceptionResponseBean {

    private List<String>errors;

    public ExceptionResponseBean(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
