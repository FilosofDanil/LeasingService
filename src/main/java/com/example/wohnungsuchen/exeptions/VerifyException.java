package com.example.wohnungsuchen.exeptions;

public class VerifyException extends RuntimeException{
    public VerifyException(String message) {
        super(message);
    }
}
