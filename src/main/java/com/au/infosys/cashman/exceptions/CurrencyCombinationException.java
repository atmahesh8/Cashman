package com.au.infosys.cashman.exceptions;

public class CurrencyCombinationException extends Exception {

    public CurrencyCombinationException(String message) {
        super(message);
    }

    public CurrencyCombinationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
