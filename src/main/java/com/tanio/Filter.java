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