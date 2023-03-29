package com.tanio;

import java.lang.reflect.Method;

class FieldValueRetriever {

    private static final String SEPARATOR = ".";

    Object retrieveFieldValue(String fieldName, Object object) {
        if (isNestedFieldName(fieldName)) {
            return retrieveNestedFieldValue(fieldName, object);
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

    private Object retrieveNestedFieldValue(String fieldName, Object object) {
        // object1.object2.object3 => object1
        String topLevelFieldName = extractTopLevelFieldName(fieldName);

        // object1 => getObject1
        String getterMethodName = transformToGetterMethodName(topLevelFieldName);

        // Get the value for the first level object: {object1}
        Object topLevelFieldValue = invokeMethod(getterMethodName, object);

        // object1.object2.object3 => object2.object3
        String remainingLevelFieldNames = extractRemainingLevelFieldNames(fieldName);

        // determines the value for field object2.object3 on {object1}
        return retrieveFieldValue(remainingLevelFieldNames, topLevelFieldValue);
    }

    private boolean isNestedFieldName(String fieldName) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);
        return separatorIndex != -1;
    }

    private boolean isReturnTypeBoolean(Method method) {
        return method.getReturnType().equals(boolean.class)
                || method.getReturnType().equals(Boolean.class);
    }

    private boolean isFieldNameReferringToBoolean(String fieldName) {
        return fieldName.startsWith("is")
                && Character.isUpperCase(fieldName.charAt(2));
    }

    private String transformToGetterMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private String extractTopLevelFieldName(String fieldName) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);
        return fieldName.substring(0, separatorIndex);
    }

    private Object invokeMethod(String methodName, Object object) {
        Method method = getMethod(methodName, object);
        return invokeMethod(method, object);
    }

    private Method getMethod(String methodName, Object object) {
        try {
            return object.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            String exceptionMessage =
                    String.format(
                            "Method [%s] does not exist in class [%s]",
                            methodName,
                            object.getClass().getName());
            throw new FilterException(exceptionMessage);
        }
    }

    private Object invokeMethod(Method method, Object object) {
        try {
            return method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String extractRemainingLevelFieldNames(String fieldName) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);
        return fieldName.substring(separatorIndex + 1);
    }
}