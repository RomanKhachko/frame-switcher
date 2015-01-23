package com.fsp.annotations;

import com.fsp.enums.LocatorType;

import java.lang.annotation.*;

/**
 * Created by Roman Khachko on 01.09.2014.
 * Annotate method if it requires switching to frame
 * <p/>
 * Available values of locatorType:
 * - id
 * - name
 * - className
 * - tagName
 * - css
 * - xpath
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface RequireSwitchingToFrame {
    /**
     * index of iFrame
     */
    int index() default 0;

    /**
     * type of the searching (e.g. css, xpath, id, etc.)
     */
    LocatorType locatorType() default LocatorType.NONE;

    /**
     * value of locator
     */
    String locatorValue() default "";

    /**
     * name of id values of iFrame
     */
    String frameNameOrId() default "";

}
