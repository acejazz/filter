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

        Long fieldNumberValue = ((Number) fieldValue).longValue();
        Long conditionNumberValue = ((Number) condition.value).longValue();

        return fieldNumberValue.equals(conditionNumberValue);
    }

    private static Object getFieldValue(String fieldName, Entity entity) {
        return extractField(fieldName, entity);
    }

    private static Object extractField(String fieldName, Entity entity) {
        try {
            Field field = entity.getClass().getField(fieldName);
            return field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}