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
        if (condition.fieldName.equals("integer_field")) {
            return entity.integerField == condition.value;
        }
        if (condition.fieldName.equals("short_field")) {
            return entity.shortField == condition.value;
        }
        else {
            return entity.longField == condition.value;
        }
    }
}