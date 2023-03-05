package com.tanio;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
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
        if (!fieldName.contains(".")) {
            try {
                String methodName = getterMethodName(fieldName);
                Method method = entity.getClass().getMethod(methodName);
                return method.invoke(entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        int dotIndex = fieldName.indexOf(".");
        String topLevelFieldName = fieldName.substring(0, dotIndex);
        String remainingLevelFieldNames = fieldName.substring(dotIndex + 1);
        String methodName = getterMethodName(topLevelFieldName);
        try {
            Method method = entity.getClass().getMethod(methodName);
            Object nestedInstance = method.invoke(entity);
            return getFieldValue(remainingLevelFieldNames, nestedInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getterMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}