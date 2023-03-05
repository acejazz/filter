package com.tanio;

import java.lang.reflect.Method;

class FieldValueRetriever {
    Object retrieveFieldValue(String fieldName, Object object) {
        final String separator = ".";
        int dotIndex = fieldName.indexOf(separator);

        if (dotIndex == -1) {
            String methodName = getterMethodName(fieldName);
            return invokeMethod(methodName, object);
        }

        String topLevelFieldName = extractTopLevelFieldName(fieldName, dotIndex);
        String methodName = getterMethodName(topLevelFieldName);
        Object topLevelFieldValue = invokeMethod(methodName, object);
        String remainingLevelFieldNames = extractRemainingLevelFieldNames(fieldName, dotIndex);
        return retrieveFieldValue(remainingLevelFieldNames, topLevelFieldValue);
    }

    private static String extractRemainingLevelFieldNames(String fieldName, int dotIndex) {
        return fieldName.substring(dotIndex + 1);
    }

    private static String extractTopLevelFieldName(String fieldName, int dotIndex) {
        return fieldName.substring(0, dotIndex);
    }

    private static Object invokeMethod(String methodName, Object entity) {
        Object methodInvocationResult;
        try {
            Method method = entity.getClass().getMethod(methodName);
            methodInvocationResult = method.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return methodInvocationResult;
    }

    private static String getterMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}