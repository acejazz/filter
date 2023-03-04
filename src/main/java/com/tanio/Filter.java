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
        long fieldValue = getFieldValue(condition.fieldName, entity);
        return fieldValue == condition.value;
    }

    private static long getFieldValue(String fieldName, Entity entity) {
        return extractField(fieldName, entity);
    }

    private static long extractField(String fieldName, Entity entity) {
        try {
            Field field = entity.getClass().getField(fieldName);
            return ((Number) field.get(entity)).longValue();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}