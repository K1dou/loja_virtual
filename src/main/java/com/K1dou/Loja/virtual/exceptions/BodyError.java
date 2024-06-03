package com.K1dou.Loja.virtual.exceptions;

import org.springframework.http.HttpStatus;

public class BodyError {

    private String error;
    private HttpStatus code;


    public BodyError() {
    }

    public BodyError(String error, HttpStatus code) {
        this.error = error;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
