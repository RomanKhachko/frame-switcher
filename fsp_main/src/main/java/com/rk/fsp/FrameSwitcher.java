package com.rk.fsp;

import com.rk.fsp.annotations.RequireSwitchingToFrame;
import com.rk.fsp.enums.LocatorType;
import com.rk.fsp.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Roman Khachko on 01.09.2014.
 */
public class FrameSwitcher {
    private WebDriver driver;

    private Object callerObject;

    public FrameSwitcher(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * invoking a proper method with switching to a frame
     *
     * @param callerObject just send 'this' from your code, or 'null' if you invoke it from static method
     * @param parameters parameters of caller method
     * @return returned value of caller method
     */
    public Object invokeWithSwitchingToFrame(Object callerObject, Object... parameters) {
        this.callerObject = callerObject;
        // returns stackTraceElement of caller method:
        StackTraceElement callerMethodInfo = Thread.currentThread().getStackTrace()[2];

        Class callerClass = getCallerClass(callerMethodInfo);
        Class[] parameterClasses = getParameterClasses(parameters);
        Method callerMethod = getMethod(callerMethodInfo, callerClass, parameterClasses);

        Class annotationClass = RequireSwitchingToFrame.class;
        if (callerMethod.isAnnotationPresent(annotationClass)) {
            switchToFrameAccordingToAnnotationParams(callerMethod, annotationClass);
        }

        Object returnedValue = invokeCallingMethod(callerMethod, parameters);

        driver.switchTo().defaultContent();

        return returnedValue;
    }

    /**
     * Arguments priority:
     * - nameOrId
     * - web element
     * - index
     *
     * @param callerMethod
     * @param annotationClass
     */
    private void switchToFrameAccordingToAnnotationParams(Method callerMethod, Class annotationClass) {
        RequireSwitchingToFrame annotation = (RequireSwitchingToFrame) callerMethod.getAnnotation(annotationClass);

        int frameIndex = annotation.index();

        LocatorType locatorType = annotation.locatorType();
        String locatorValue = annotation.locatorValue();
        String nameOrId = annotation.frameNameOrId();

        if (nameOrId.length() > 0) {
            String xpathLocator = String.format("//*[@name='%1$s' or @id='%1$s']", nameOrId);
            SeleniumUtils.findElementWithTimeout(driver, By.xpath(xpathLocator), 20);
            driver.switchTo().frame(nameOrId);
        } else if (locatorType != LocatorType.NONE && locatorValue.length() > 0) {
            switchToFrameByWebElement(locatorType, locatorValue);
        } else {
            driver.switchTo().frame(frameIndex);
        }
    }

    private void switchToFrameByWebElement(LocatorType locatorType, String locatorValue) {
        By by;
        switch (locatorType) {
            case ID:
                by = By.id(locatorValue);
                break;
            case NAME:
                by = By.name(locatorValue);
                break;
            case CLASS_NAME:
                by = By.className(locatorValue);
                break;
            case TAG_NAME:
                by = By.tagName(locatorValue);
                break;
            case CSS:
                by = By.cssSelector(locatorValue);
                break;
            case XPATH:
                by = By.xpath(locatorValue);
                break;
            default:
                throw new RuntimeException("Invalid locatorType parameter. No exists");
        }

        WebElement frameElement = SeleniumUtils.findElementWithTimeout(driver, by, 60);
        driver.switchTo().frame(frameElement);
    }

    private Object invokeCallingMethod(Method callerMethod, Object[] parameters) {
        Method callingMethod = null;
        try {
            callingMethod = callerMethod.getDeclaringClass().getDeclaredMethod(callerMethod.getName() + "Impl", callerMethod.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return invokeMethod(callingMethod, parameters);
    }

    private Object invokeMethod(Method callingMethod, Object[] parameters) {
        Object returnedValue;
        callingMethod.setAccessible(true);
        try {
            returnedValue = callingMethod.invoke(callerObject, parameters);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        return returnedValue;
    }

    private Method getMethod(StackTraceElement callerMethodInfo, Class callerClass, Class[] parameterClasses) {
        List<Method> fetchedMethods = new ArrayList<Method>();

        for (Method method : Arrays.asList(callerClass.getMethods())) {
            if (method.getName().equals(callerMethodInfo.getMethodName()) && method.getParameterCount() == parameterClasses.length) {
                fetchedMethods.add(method);
            }
        }

        Method callerMethod = null;

        if (fetchedMethods.size() == 1) {
            callerMethod = fetchedMethods.get(0);
        } else if (fetchedMethods.size() > 1) {
            callerMethod = getCallerMethodAccordingToParameterTypes(fetchedMethods, callerClass, parameterClasses);
        } else {
            throw new RuntimeException("There's no such method");
        }

        return callerMethod;
    }

    private Method getCallerMethodAccordingToParameterTypes(List<Method> fetchedMethods, Class callerClass, Class[] parameterClasses) {
        Method callerMethod = null;
        for (Method method : fetchedMethods) {
            for (int i = 0; i < parameterClasses.length; i++) {
                Class methodParameterType = method.getParameterTypes()[i];
                if (parameterClasses[i].equals(methodParameterType) || Arrays.asList(parameterClasses[i].getInterfaces()).contains(methodParameterType)
                        || getClassHierarchy(callerClass).contains(methodParameterType)) {
                    if (i == parameterClasses.length - 1) {
                        callerMethod = method;
                    }

                } else {
                    break;
                }
            }

            if (callerMethod != null) {
                break;
            }
        }

        if (callerMethod == null) {
            throw new RuntimeException("There's no method with compatible types");
        }

        return callerMethod;
    }

    private List<Class> getClassHierarchy(final Class callerClass) {
        List classHierarchyList = new ArrayList();
        Class superClass = callerClass;

        do {
            superClass = superClass.getSuperclass();
            classHierarchyList.add(superClass);
        } while (!superClass.getSimpleName().equals("Object"));

        return classHierarchyList;
    }

    private Class[] getParameterClasses(Object[] parameters) {
        Class[] parameterClasses = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterClasses[i] = parameters[i].getClass();
        }
        return parameterClasses;
    }

    private Class getCallerClass(StackTraceElement callerMethodInfo) {
        Class callerClass;
        try {
            callerClass = Class.forName(callerMethodInfo.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return callerClass;
    }

}
