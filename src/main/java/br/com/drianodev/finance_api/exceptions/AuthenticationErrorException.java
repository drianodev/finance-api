package br.com.drianodev.finance_api.exceptions;

public class AuthenticationErrorException extends RuntimeException {
    public AuthenticationErrorException(String message) {
        super(message);
    }
}