package com.tanio;

import java.lang.reflect.Field;
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
            return conditionValue.equals(fieldValue);
        }

        Long conditionNumberValue = ((Number) conditionValue).longValue();
        Long fieldNumberValue = ((Number) fieldValue).longValue();
        return conditionNumberValue.equals(fieldNumberValue);
    }

    private static Object getFieldValue(String fieldName, Entity entity) {
        try {
            Field field = entity.getClass().getField(fieldName);
            return field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}