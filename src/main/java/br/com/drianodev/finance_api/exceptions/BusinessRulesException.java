package br.com.drianodev.finance_api.exceptions;

public class BusinessRulesException extends RuntimeException {
    public BusinessRulesException(String message) {
        super(message);
    }
}
