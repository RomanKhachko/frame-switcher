package com.rk.fsp.exceptions;

/**
 * Created by Roman_Khachko on 5/12/2016.
 */
public class ProperAnnotationAbsenceException extends RuntimeException {
    @Override
    public String getMessage() {
        return "method sent as a parameter should be annotated with @RequireSwitchingToFrame";
    }
}
