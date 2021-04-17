package com.burakkaygusuz.exceptions;

public class UnSupportedBrowserException extends IllegalStateException {

    public UnSupportedBrowserException(String browserName) {
        super(String.format("Browser %s is not valid or not defined :", browserName));
    }
}
