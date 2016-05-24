package com.rk.fsp.exceptions;

/**
 * Created by Roman_Khachko on 5/12/2016.
 */
public class InvalidLocatorTypeException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Invalid locatorType parameter. Not exists";
    }
}
