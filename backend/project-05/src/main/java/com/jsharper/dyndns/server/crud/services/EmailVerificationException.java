package com.jsharper.dyndns.server.crud.services;

public class EmailVerificationException extends RuntimeException{
    public EmailVerificationException(String message) {
        super(message);
    }
}
