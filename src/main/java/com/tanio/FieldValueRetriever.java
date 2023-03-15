package com.tanio;

import java.lang.reflect.Method;

class FieldValueRetriever {

    private static final String SEPARATOR = ".";

    Object retrieveFieldValue(String fieldName, Object object) {
        int separatorIndex = fieldName.indexOf(SEPARATOR);

        if (separatorIndex == -1) {
            boolean isFieldNameReferringToBoolean = fieldName.startsWith("is")
                    && Character.isUpperCase(fieldName.charAt(2));
            if (isFieldNameReferringToBoolean) {
                try {
                    Method method = object.getClass().getMethod(fieldName);
                    boolean isReturnTypeBoolean = method.getReturnType().equals(boolean.class)
                            || method.getReturnType().equals(Boolean.class);
                    if (isReturnTypeBoolean) {
                        return invokeMethod(fieldName, object);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            String methodName = transformToGetterMethodName(fieldName);
            return invokeMethod(methodName, object);
        }

        String topLevelFieldName = extractTopLevelFieldName(fieldName, separatorIndex);
        String methodName = transformToGetterMethodName(topLevelFieldName);
        Object topLevelFieldValue = invokeMethod(methodName, object);
        String remainingLevelFieldNames = extractRemainingLevelFieldNames(fieldName, separatorIndex);
        return retrieveFieldValue(remainingLevelFieldNames, topLevelFieldValue);
    }

    private static String transformToGetterMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static String extractTopLevelFieldName(String fieldName, int dotIndex) {
        return fieldName.substring(0, dotIndex);
    }

    private static Object invokeMethod(String methodName, Object object) {
        Object methodInvocationResult;
        try {
            Method method = object.getClass().getMethod(methodName);
            methodInvocationResult = method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return methodInvocationResult;
    }

    private static String extractRemainingLevelFieldNames(String fieldName, int dotIndex) {
        return fieldName.substring(dotIndex + 1);
    }
}