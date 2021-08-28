package com.example.takeloanapp.controller.exception;

public class LoanNotFoundException extends  Exception {
    public LoanNotFoundException(String message){
        super(message);
    }
}
