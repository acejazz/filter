package com.tanio;

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
        if (fieldName.equals("integer_field")) {
            return entity.integerField;
        }
        if (fieldName.equals("short_field")) {
            return entity.shortField;
        }
        return entity.longField;
    }
}