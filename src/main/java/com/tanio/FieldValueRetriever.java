package com.tanio;

import java.lang.reflect.Method;

class FieldValueRetriever {

    private static final String SEPARATOR = ".";

    Object retrieveFieldValue(String fieldName, Object object) {
        if (isNestedFieldName(fieldName)) {
            String topLevelFieldName = extractTopLevelFieldName(fieldName);
            String getterMethodName = transformToGetterMethodName(topLevelFieldName);
            Object topLevelFieldValue = invokeMethod(getterMethodName, object);
            String remainingLevelFieldNames = extractRemainingLevelFieldNames(fieldName);
            return retrieveFieldValue(remainingLevelFieldNames, topLevelFieldValue);
        }

        if (isFieldNameReferringToBoolean(fieldName)) {
            Method method = getMethod(fieldName, object);
            if (isReturnTypeBoolean(method)) {
                // Does not use the transformation to getter name
                return invokeMethod(fieldName, object);
            }
        }

        String methodName = transformToGetterMethodName(fieldName);
        return invokeMethod(methodName, object);
    }

    boolean isNestedFieldName(String fieldName) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);
        return separatorIndex != -1;
    }

    private static boolean isReturnTypeBoolean(Method method) {
        return method.getReturnType().equals(boolean.class)
                || method.getReturnType().equals(Boolean.class);
    }

    private static boolean isFieldNameReferringToBoolean(String fieldName) {
        return fieldName.startsWith("is")
                && Character.isUpperCase(fieldName.charAt(2));
    }

    private static String transformToGetterMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static String extractTopLevelFieldName(String fieldName) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);
        return fieldName.substring(0, separatorIndex);
    }

    private static Object invokeMethod(String methodName, Object object) {
        Method method = getMethod(methodName, object);
        return invokeMethod(method, object);
    }

    private static Method getMethod(String methodName, Object object) {
        try {
            return object.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object invokeMethod(Method method, Object object) {
        try {
            return method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractRemainingLevelFieldNames(String fieldName) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);
        return fieldName.substring(separatorIndex + 1);
    }
}