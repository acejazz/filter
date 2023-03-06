package com.tanio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Filter {
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();

    <T> List<T> perform(List<T> target, Condition condition) {
        return target.stream()
                .filter(it -> matchesCondition(condition, it))
                .collect(Collectors.toList());
    }

    <T> List<T> performOr(List<T> target, List<Condition> conditions) {
        List<List<T>> conditionResults = new ArrayList<>();
        for (Condition condition : conditions) {
            conditionResults.add(perform(target, condition));
        }
        return union(conditionResults);
    }

    private static <T> List<T> union(List<List<T>> lists) {
        Set<T> result = new HashSet<>();

        for (List<T> list : lists) {
            result.addAll(list);
        }

        return new ArrayList<>(result);
    }

    private <T> boolean matchesCondition(Condition condition, T object) {
        Object fieldValue = fieldValueRetriever.retrieveFieldValue(condition.fieldName, object);

        if (fieldValue == null) {
            return false;
        }

        return fieldConditionEvaluator.evaluateCondition(
                condition.operator,
                condition.value,
                fieldValue);
    }
}