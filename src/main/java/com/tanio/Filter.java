package com.tanio;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

class Filter {
    List<Entity> perform(List<Entity> target, Condition condition) {
        return target.stream()
                .filter(it -> matchesCondition(condition, it))
                .collect(Collectors.toList());
    }

    private static boolean matchesCondition(Condition condition, Entity entity) {
        Object fieldValue = getFieldValue(condition.fieldName, entity);
        Object conditionValue = condition.value;

        if (conditionValue.getClass().equals(String.class)) {
            if (condition.operator.equals(Condition.Operator.EQUAL)) {
                return conditionValue.equals(fieldValue);
            }
            return !conditionValue.equals(fieldValue);
        }

        if (conditionValue.getClass().equals(Boolean.class)) {
            if (condition.operator.equals(Condition.Operator.EQUAL)) {
                return conditionValue.equals(fieldValue);
            }
            return !conditionValue.equals(fieldValue);
        }

        if (conditionValue.getClass().equals(Character.class)) {
            if (condition.operator.equals(Condition.Operator.EQUAL)) {
                return conditionValue.equals(fieldValue);
            }
            return !conditionValue.equals(fieldValue);
        }

        Double conditionNumberValue = ((Number) conditionValue).doubleValue();
        Double fieldNumberValue = ((Number) fieldValue).doubleValue();
        if (condition.operator.equals(Condition.Operator.EQUAL)) {
            return conditionNumberValue.equals(fieldNumberValue);
        }
        return !conditionNumberValue.equals(fieldNumberValue);
    }

    private static Object getFieldValue(String fieldName, Entity entity) {
        try {
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = entity.getClass().getMethod(methodName);
            return method.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}