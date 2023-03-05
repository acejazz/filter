package com.tanio;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

class Filter {
    FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();

    List<Entity> perform(List<Entity> target, Condition condition) {
        return target.stream()
                .filter(it -> matchesCondition(condition, it))
                .collect(Collectors.toList());
    }

    private boolean matchesCondition(Condition condition, Entity entity) {
        Object fieldValue = getFieldValue(condition.fieldName, entity);
        return fieldConditionEvaluator.evaluateCondition(
                condition.operator,
                condition.value,
                fieldValue);
    }

    private static Object getFieldValue(String fieldName, Object entity) {
        final String separator = ".";
        int dotIndex = fieldName.indexOf(separator);

        if (dotIndex == -1) {
            String methodName = getterMethodName(fieldName);
            return invokeMethod(methodName, entity);
        }

        String topLevelFieldName = extractTopLevelFieldName(fieldName, dotIndex);
        String methodName = getterMethodName(topLevelFieldName);
        Object topLevelFieldValue = invokeMethod(methodName, entity);
        String remainingLevelFieldNames = extractRemainingLevelFieldNames(fieldName, dotIndex);
        return getFieldValue(remainingLevelFieldNames, topLevelFieldValue);
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