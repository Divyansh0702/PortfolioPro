package com.divyansh.PortfolioPro.exception;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String error){
        super(error);
    }
}
