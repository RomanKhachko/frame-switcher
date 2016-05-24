package com.rk.fsp.exceptions;

/**
 * Created by Roman_Khachko on 5/17/2016.
 */
public class DriverableNotImplementedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Driverable should be implemented in order to obtain WebDriver instance";
    }
}
